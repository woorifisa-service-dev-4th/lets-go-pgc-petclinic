import { Link } from 'react-router-dom';

const OwnerDetails = ({ owner }) => {
  if (!owner) {
    return <div className="alert alert-info">Loading...</div>;
  }

  const formatDate = (dateString) => {
    if (!dateString) return '';
    const date = new Date(dateString);
    return date.toISOString().split('T')[0];
  };

  return (
    <div>
      <h2>Owner Information</h2>
      <table className="table table-striped">
        <tbody>
          <tr>
            <th>Name</th>
            <td>
              <b>{owner.firstName} {owner.lastName}</b>
            </td>
          </tr>
          <tr>
            <th>Address</th>
            <td>{owner.address}</td>
          </tr>
          <tr>
            <th>City</th>
            <td>{owner.city}</td>
          </tr>
          <tr>
            <th>Telephone</th>
            <td>{owner.telephone}</td>
          </tr>
        </tbody>
      </table>

      <Link to={`/owners/${owner.id}/edit`} className="btn btn-primary">
        Edit Owner
      </Link>
      <Link to={`/owners/${owner.id}/pets/new`} className="btn btn-primary ms-1">
        Add New Pet
      </Link>

      <h2 className="mt-5">Pets and Visits</h2>
      {owner.pets && owner.pets.length > 0 ? (
        <table className="table table-striped">
          <tbody>
            {owner.pets.map((pet) => (
              <tr key={pet.id}>
                <td valign="top" style={{ width: "30%" }}>
                  <dl className="dl-horizontal">
                    <dt>Name</dt>
                    <dd>{pet.name}</dd>
                    <dt>Birth Date</dt>
                    <dd>{formatDate(pet.birthDate)}</dd>
                    <dt>Type</dt>
                    <dd>{pet.type}</dd>
                  </dl>
                </td>
                <td valign="top" style={{ width: "70%" }}>
                  <table className="table-condensed">
                    <thead>
                      <tr>
                        <th>Visit Date</th>
                        <th>Description</th>
                      </tr>
                    </thead>
                    <tbody>
                      {pet.visits && pet.visits.length > 0 ? (
                        pet.visits.map((visit, idx) => (
                          <tr key={idx}>
                            <td>{formatDate(visit.date)}</td>
                            <td>{visit.description}</td>
                          </tr>
                        ))
                      ) : (
                        <tr>
                          <td colSpan="2">No visits recorded</td>
                        </tr>
                      )}
                      <tr>
                        <td>
                          <Link to={`/owners/${owner.id}/pets/${pet.id}/edit`}>
                            Edit Pet
                          </Link>
                        </td>
                        <td>
                          <Link to={`/owners/${owner.id}/pets/${pet.id}/visits/new`}>
                            Add Visit
                          </Link>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <div className="alert alert-info">No pets found</div>
      )}
    </div>
  );
};

export default OwnerDetails;