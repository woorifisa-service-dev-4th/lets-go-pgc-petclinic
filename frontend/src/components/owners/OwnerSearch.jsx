import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const OwnerSearch = ({ onSearch }) => {
  const [lastName, setLastName] = useState('');
  const [errors, setErrors] = useState({});
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    onSearch(lastName);
  };

  const handleAddOwner = () => {
    navigate('/owners/new');
  };

  return (
    <div>
      <h2>Find Owners</h2>
      <form onSubmit={handleSubmit} className="form-horizontal" id="search-owner-form">
        <div className="form-group">
          <div className="control-group" id="lastNameGroup">
            <label className="col-sm-2 control-label">Last name </label>
            <div className="col-sm-10">
              <input
                className={`form-control ${errors.lastName ? 'is-invalid' : ''}`}
                value={lastName}
                onChange={(e) => setLastName(e.target.value)}
                size="30"
                maxLength="80"
              />
              {errors.lastName && (
                <div className="invalid-feedback">{errors.lastName}</div>
              )}
            </div>
          </div>
        </div>
        <div className="form-group mt-3">
          <div className="col-sm-offset-2 col-sm-10">
            <button type="submit" className="btn btn-primary">
              Find Owner
            </button>
          </div>
        </div>

        <button 
          type="button" 
          className="btn btn-primary mt-3" 
          onClick={handleAddOwner}
        >
          Add Owner
        </button>
      </form>
    </div>
  );
};

export default OwnerSearch;