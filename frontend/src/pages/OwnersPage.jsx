import { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import OwnerSearch from '../components/owners/OwnerSearch';
import OwnerList from '../components/owners/OwnerList';
import { ownerService } from '../services/ownerService';

const OwnersPage = () => {
  const [owners, setOwners] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const [searchTerm, setSearchTerm] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const location = useLocation();

  useEffect(() => {
    // 첫 로드시 또는 경로가 /owners/find인 경우 검색 폼만 표시
    const isSearchForm = location.pathname === '/owners/find';
    if (!isSearchForm) {
      fetchOwners(searchTerm, currentPage);
    }
  }, [location.pathname, currentPage]);

  const fetchOwners = async (lastName = '', page = 1) => {
    setLoading(true);
    try {
      const response = await ownerService.getOwners(lastName, page - 1);
      setOwners(response.data.content);
      setCurrentPage(response.data.currentPage);
      setTotalPages(response.data.totalPages);
      setError(null);
    } catch (err) {
      setError('Failed to fetch owners');
      console.error('Error fetching owners:', err);
    }
    setLoading(false);
  };

  const handleSearch = async (lastName) => {
    setSearchTerm(lastName);
    try {
      if (!lastName.trim()) {
        // 빈 검색어인 경우 전체 목록 표시
        fetchOwners('', 1);
        return;
      }

      // 검색어로 검색
      const response = await ownerService.searchOwnersByLastName(lastName);
      const results = response.data;

      if (results.length === 0) {
        setError('No owners found with that last name');
        setOwners([]);
      } else if (results.length === 1) {
        // 단일 결과인 경우 해당 소유자 상세 페이지로 이동
        window.location.href = `/owners/${results[0].id}`;
      } else {
        // 여러 결과인 경우 목록 표시
        setOwners(results);
        setCurrentPage(1);
        setTotalPages(Math.ceil(results.length / 10));
        setError(null);
      }
    } catch (err) {
      setError('Error searching for owners');
      console.error('Search error:', err);
    }
  };

  const handlePageChange = (page) => {
    setCurrentPage(page);
    fetchOwners(searchTerm, page);
  };

  const isSearchForm = location.pathname === '/owners/find';
  const showList = !isSearchForm || (owners.length > 0 && !loading);

  return (
    <div>
      {isSearchForm && <OwnerSearch onSearch={handleSearch} />}
      
      {error && <div className="alert alert-danger">{error}</div>}
      
      {loading && <div className="text-center mt-4">Loading...</div>}
      
      {showList && owners.length > 0 && (
        <OwnerList 
          owners={owners} 
          currentPage={currentPage}
          totalPages={totalPages}
          onPageChange={handlePageChange}
        />
      )}
    </div>
  );
};

export default OwnersPage;