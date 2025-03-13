import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import OwnerForm from '../components/owners/OwnerForm';
import OwnerDetails from '../components/owners/OwnerDetails';
import PetForm from '../components/pets/PetForm';
import { ownerService } from '../services/ownerService';
import { petService } from '../services/petService';

const OwnerPage = ({ isEdit = false, petForm = false }) => {
  const { ownerId, petId } = useParams();
  const [owner, setOwner] = useState(null);
  const [pet, setPet] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    // 소유자 ID가 있는 경우 데이터 로드
    if (ownerId) {
      fetchOwner(ownerId);
    }

    // 펫 ID가 있는 경우 펫 데이터 로드
    if (petId) {
      fetchPet(petId);
    }
  }, [ownerId, petId]);

  const fetchOwner = async (id) => {
    setLoading(true);
    try {
      const response = await ownerService.getOwnerById(id);
      setOwner(response.data);
      setError(null);
    } catch (err) {
      setError('Failed to fetch owner details');
      console.error('Error fetching owner:', err);
    }
    setLoading(false);
  };

  const fetchPet = async (id) => {
    try {
      const response = await petService.getPetById(id);
      setPet(response.data);
    } catch (err) {
      console.error('Error fetching pet:', err);
      setError('Failed to fetch pet details');
    }
  };

  const handleSaveOwner = async (formData) => {
    setLoading(true);
    try {
      if (isEdit && ownerId) {
        // 기존 소유자 업데이트
        await ownerService.updateOwner(ownerId, formData);
        // 업데이트 후 최신 데이터로 새로고침
        await fetchOwner(ownerId);
        navigate(`/owners/${ownerId}`);
      } else {
        // 새 소유자 생성
        const response = await ownerService.createOwner(formData);
        
        // Location 헤더에서 새 소유자 ID 추출 시도
        let newOwnerId;
        
        if (response.headers && response.headers.location) {
          const locationParts = response.headers.location.split('/');
          newOwnerId = locationParts[locationParts.length - 1];
        } else if (response.data && response.data.id) {
          // 백엔드가 응답에 ID를 직접 포함할 경우
          newOwnerId = response.data.id;
        } else {
          // ID를 얻을 수 없을 경우, 소유자 목록으로 이동
          navigate('/owners');
          return;
        }
        
        navigate(`/owners/${newOwnerId}`);
      }
      setLoading(false);
    } catch (err) {
      setLoading(false);
      setError('Failed to save owner: ' + (err.response?.data?.message || err.message));
      console.error('Error saving owner:', err);
    }
  };

  const handleSavePet = async (formData) => {
    setLoading(true);
    try {
      if (isEdit && petId) {
        // 기존 펫 업데이트
        await petService.updatePet(petId, formData);
      } else {
        // 새 펫 생성
        await petService.createPet(ownerId, { ...formData, ownerId });
      }
      
      // 펫 저장 후 소유자 데이터 새로고침해서 최신 상태 표시
      await fetchOwner(ownerId);
      
      // 소유자 상세 페이지로 이동
      navigate(`/owners/${ownerId}`);
      setLoading(false);
    } catch (err) {
      setLoading(false);
      setError('Failed to save pet: ' + (err.response?.data?.message || err.message));
      console.error('Error saving pet:', err);
    }
  };

  if (loading) return <div className="text-center">Loading...</div>;
  if (error) return <div className="alert alert-danger">{error}</div>;

  // 생성 화면 (ID가 없는 경우)
  if (!ownerId) {
    return <OwnerForm onSave={handleSaveOwner} />;
  }

  // 펫 폼 표시
  if (petForm) {
    return <PetForm 
      pet={pet} 
      owner={owner} 
      isEdit={isEdit} 
      onSave={handleSavePet} 
    />;
  }

  // 소유자 수정 화면
  if (isEdit) {
    return <OwnerForm owner={owner} isEdit={true} onSave={handleSaveOwner} />;
  }

  // 소유자 상세 화면 (기본)
  return <OwnerDetails owner={owner} />;
};

export default OwnerPage;