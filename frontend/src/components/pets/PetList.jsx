import { Link } from 'react-router-dom';

const PetList = ({ pets, ownerId }) => {
  if (!pets || pets.length === 0) {
    return <div className="alert alert-info">No pets found.</div>;
  }

  const formatDate = (dateString) => {
    if (!dateString) return '';
    const date = new Date(dateString);
    return date.toISOString().split('T')[0];
  };

  return (
    <div>
      <h2>Pets</h2>
      <table className="table table-striped">
        <thead>
          <tr>
            <th>Name</th>
            <th>Birth Date</th>
            <th>Type</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {pets.map((pet) => (
            <tr key={pet.id}>
              <td>{pet.name}</td>
              <td>{formatDate(pet.birthDate)}</td>
              <td>{pet.type}</td>
              <td>
                <Link 
                  to={`/owners/${ownerId}/pets/${pet.id}/edit`} 
                  className="btn btn-sm btn-primary me-2"
                >
                  Edit
                </Link>
                <Link 
                  to={`/owners/${ownerId}/pets/${pet.id}/visits/new`} 
                  className="btn btn-sm btn-info"
                >
                  Add Visit
                </Link>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <div className="mt-3">
        <Link to={`/owners/${ownerId}/pets/new`} className="btn btn-primary">
          Add New Pet
        </Link>
      </div>
    </div>
  );
};

export default PetList;