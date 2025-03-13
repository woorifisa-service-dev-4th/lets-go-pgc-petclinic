import { useState, useEffect } from 'react';
import { petService } from '../../services/petService';

const PetForm = ({ pet, petTypes, owner, isEdit = false, onSave }) => {
  const [formData, setFormData] = useState({
    name: '',
    birthDate: '',
    typeId: ''
  });
  const [errors, setErrors] = useState({});
  const [types, setTypes] = useState([]);

  // 오늘 날짜를 YYYY-MM-DD 형식으로 가져오기
  const today = new Date().toISOString().split('T')[0];

  useEffect(() => {
    // 펫 타입 로드
    if (!petTypes || petTypes.length === 0) {
      petService.getPetTypes()
        .then(response => {
          setTypes(response.data);
        })
        .catch(error => {
          console.error('Error fetching pet types', error);
        });
    } else {
      setTypes(petTypes);
    }

    // 기존 펫 데이터가 있는 경우 폼에 세팅
    if (pet) {
      const birthDate = pet.birthDate ? new Date(pet.birthDate).toISOString().split('T')[0] : '';
      
      setFormData({
        name: pet.name || '',
        birthDate: birthDate,
        typeId: pet.typeId || (pet.type ? getTypeIdByName(pet.type) : '')
      });
    }
  }, [pet, petTypes]);

  // 펫 타입 이름으로 ID 찾기 (타입이 문자열로 오는 경우 처리)
  const getTypeIdByName = (typeName) => {
    if (!types.length) return '';
    const foundType = types.find(type => type.name === typeName);
    return foundType ? foundType.id : '';
  };

  const validate = () => {
    const newErrors = {};
    if (!formData.name.trim()) newErrors.name = 'Pet name is required';
    if (!formData.birthDate) newErrors.birthDate = 'Birth date is required';
    else {
      // 생년월일이 오늘 이후인지 검사
      const selectedDate = new Date(formData.birthDate);
      const currentDate = new Date();
      
      // 시간을 제외하고 날짜만 비교
      selectedDate.setHours(0, 0, 0, 0);
      currentDate.setHours(0, 0, 0, 0);
      
      if (selectedDate > currentDate) {
        newErrors.birthDate = 'Birth date cannot be in the future';
      }
    }
    
    if (!formData.typeId) newErrors.typeId = 'Pet type is required';
    
    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (validate()) {
      onSave(formData);
    }
  };

  return (
    <div>
      <h2>{isEdit ? 'Update Pet' : 'New Pet'}</h2>
      <form className="form-horizontal" onSubmit={handleSubmit}>
        <div className="form-group">
          <label className="col-sm-2 control-label">Owner</label>
          <div className="col-sm-10">
            <span>{owner ? `${owner.firstName} ${owner.lastName}` : 'Loading...'}</span>
          </div>
        </div>

        <div className="form-group mt-3">
          <label className="col-sm-2 control-label">Name</label>
          <div className="col-sm-10">
            <input
              type="text"
              className={`form-control ${errors.name ? 'is-invalid' : ''}`}
              name="name"
              value={formData.name}
              onChange={handleChange}
            />
            {errors.name && <div className="invalid-feedback">{errors.name}</div>}
          </div>
        </div>

        <div className="form-group mt-3">
          <label className="col-sm-2 control-label">Birth Date</label>
          <div className="col-sm-10">
            <input
              type="date"
              className={`form-control ${errors.birthDate ? 'is-invalid' : ''}`}
              name="birthDate"
              value={formData.birthDate}
              onChange={handleChange}
              max={today} // 오늘 날짜 이후는 선택할 수 없도록 제한
            />
            {errors.birthDate && <div className="invalid-feedback">{errors.birthDate}</div>}
          </div>
        </div>

        <div className="form-group mt-3">
          <label className="col-sm-2 control-label">Type</label>
          <div className="col-sm-10">
            <select
              className={`form-control ${errors.typeId ? 'is-invalid' : ''}`}
              name="typeId"
              value={formData.typeId}
              onChange={handleChange}
            >
              <option value="">-- Select a type --</option>
              {types.map(type => (
                <option key={type.id} value={type.id}>{type.name}</option>
              ))}
            </select>
            {errors.typeId && <div className="invalid-feedback">{errors.typeId}</div>}
          </div>
        </div>

        <div className="form-group mt-4">
          <div className="col-sm-offset-2 col-sm-10">
            <button className="btn btn-primary" type="submit">
              {isEdit ? 'Update Pet' : 'Add Pet'}
            </button>
          </div>
        </div>
      </form>
    </div>
  );
};

export default PetForm;