import { Link } from 'react-router-dom';

const OwnerList = ({ owners, currentPage, totalPages, onPageChange }) => {
  if (!owners || owners.length === 0) {
    return <div className="alert alert-info">No owners found.</div>;
  }

  const renderPagination = () => {
    if (totalPages <= 1) return null;

    const pageNumbers = Array.from({ length: totalPages }, (_, i) => i + 1);

    return (
      <div className="mt-3">
        <span>Pages:</span>
        <span>[</span>
        {pageNumbers.map((page) => (
          <span key={page}>
            {currentPage !== page ? (
              <a
                href="#"
                onClick={(e) => {
                  e.preventDefault();
                  onPageChange(page);
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
                onPageChange(1);
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
                onPageChange(currentPage - 1);
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
                onPageChange(currentPage + 1);
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
                onPageChange(totalPages);
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
      <h2>Owners</h2>
      <table className="table table-striped" id="owners">
        <thead>
          <tr>
            <th style={{ width: '150px' }}>Name</th>
            <th style={{ width: '200px' }}>Address</th>
            <th>City</th>
            <th style={{ width: '120px' }}>Telephone</th>
            <th>Pets</th>
          </tr>
        </thead>
        <tbody>
          {owners.map((owner) => (
            <tr key={owner.id}>
              <td>
                <Link to={`/owners/${owner.id}`}>
                  {owner.firstName} {owner.lastName}
                </Link>
              </td>
              <td>{owner.address}</td>
              <td>{owner.city}</td>
              <td>{owner.telephone}</td>
              <td>{owner.pets ? owner.pets.join(', ') : ''}</td>
            </tr>
          ))}
        </tbody>
      </table>
      {renderPagination()}
    </div>
  );
};

export default OwnerList;