import { useState, useEffect } from 'react';
import axios from 'axios';

const VetList = () => {
  const [vets, setVets] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(0);

  useEffect(() => {
    fetchVets(currentPage);
  }, [currentPage]);

  const fetchVets = async (page) => {
    setLoading(true);
    try {
      // API 엔드포인트는 백엔드에 맞게 조정해야 합니다
      const response = await axios.get(`http://localhost:8080/api/vets`, {
        params: { page: page - 1, size: 10 }
      });
      setVets(response.data.content);
      setTotalPages(response.data.totalPages);
      setLoading(false);
    } catch (err) {
      setError('Failed to fetch veterinarians');
      setLoading(false);
      console.error('Error fetching veterinarians:', err);
    }
  };

  const handlePageChange = (page) => {
    setCurrentPage(page);
  };

  if (loading) return <div>Loading...</div>;
  if (error) return <div className="alert alert-danger">{error}</div>;

  const renderPagination = () => {
    if (totalPages <= 1) return null;

    const pageNumbers = Array.from({ length: totalPages }, (_, i) => i + 1);

    return (
      <div>
        <span>Pages:</span>
        <span>[</span>
        {pageNumbers.map((page) => (
          <span key={page}>
            {currentPage !== page ? (
              <a
                href="#"
                onClick={(e) => {
                  e.preventDefault();
                  handlePageChange(page);
                }}
                className="mx-1"
              >
                {page}
              </a>
            ) : (
              <span className="mx-1">{page}</span>
            )}
          </span>
        ))}
        <span>]&nbsp;</span>

        <span>
          {currentPage > 1 ? (
            <a
              href="#"
              onClick={(e) => {
                e.preventDefault();
                handlePageChange(1);
              }}
              title="First"
              className="fa fa-fast-backward mx-1"
            ></a>
          ) : (
            <span title="First" className="fa fa-fast-backward mx-1"></span>
          )}
        </span>

        <span>
          {currentPage > 1 ? (
            <a
              href="#"
              onClick={(e) => {
                e.preventDefault();
                handlePageChange(currentPage - 1);
              }}
              title="Previous"
              className="fa fa-step-backward mx-1"
            ></a>
          ) : (
            <span title="Previous" className="fa fa-step-backward mx-1"></span>
          )}
        </span>

        <span>
          {currentPage < totalPages ? (
            <a
              href="#"
              onClick={(e) => {
                e.preventDefault();
                handlePageChange(currentPage + 1);
              }}
              title="Next"
              className="fa fa-step-forward mx-1"
            ></a>
          ) : (
            <span title="Next" className="fa fa-step-forward mx-1"></span>
          )}
        </span>

        <span>
          {currentPage < totalPages ? (
            <a
              href="#"
              onClick={(e) => {
                e.preventDefault();
                handlePageChange(totalPages);
              }}
              title="Last"
              className="fa fa-fast-forward mx-1"
            ></a>
          ) : (
            <span title="Last" className="fa fa-fast-forward mx-1"></span>
          )}
        </span>
      </div>
    );
  };

  return (
    <div>
      <h2>Veterinarians</h2>
      <table id="vets" className="table table-striped">
        <thead>
          <tr>
            <th>Name</th>
            <th>Specialties</th>
          </tr>
        </thead>
        <tbody>
          {vets.map((vet) => (
            <tr key={vet.id}>
              <td>{vet.firstName} {vet.lastName}</td>
              <td>
                {vet.specialties && vet.specialties.length > 0 
                  ? vet.specialties.join(', ') 
                  : 'none'}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      {renderPagination()}
    </div>
  );
};

export default VetList;