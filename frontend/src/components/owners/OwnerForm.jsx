import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const OwnerForm = ({ owner, isEdit = false, onSave }) => {
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    address: '',
    city: '',
    telephone: ''
  });
  const [errors, setErrors] = useState({});
  const navigate = useNavigate();

  useEffect(() => {
    if (owner) {
      setFormData({
        firstName: owner.firstName || '',
        lastName: owner.lastName || '',
        address: owner.address || '',
        city: owner.city || '',
        telephone: owner.telephone || ''
      });
    }
  }, [owner]);

  const validate = () => {
    const newErrors = {};
    if (!formData.firstName.trim()) newErrors.firstName = 'First name is required';
    if (!formData.lastName.trim()) newErrors.lastName = 'Last name is required';
    if (!formData.address.trim()) newErrors.address = 'Address is required';
    if (!formData.city.trim()) newErrors.city = 'City is required';
    if (!formData.telephone.trim()) newErrors.telephone = 'Telephone is required';
    else if (!/^\d{10}$/.test(formData.telephone)) 
      newErrors.telephone = 'Telephone must be 10 digits';
    
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

  const renderFormGroup = (label, name, type = 'text') => {
    const hasError = !!errors[name];
    return (
      <div className="form-group has-feedback mb-3">
        <div className={`control-group ${hasError ? 'has-error' : ''}`}>
          <label className="col-sm-2 control-label">{label}</label>
          <div className="col-sm-10">
            <input
              type={type}
              className={`form-control ${hasError ? 'is-invalid' : ''}`}
              name={name}
              value={formData[name]}
              onChange={handleChange}
            />
            {hasError && <div className="invalid-feedback">{errors[name]}</div>}
          </div>
        </div>
      </div>
    );
  };

  return (
    <div>
      <h2>Owner</h2>
      <form className="form-horizontal" id="add-owner-form" onSubmit={handleSubmit}>
        {renderFormGroup('First Name', 'firstName')}
        {renderFormGroup('Last Name', 'lastName')}
        {renderFormGroup('Address', 'address')}
        {renderFormGroup('City', 'city')}
        {renderFormGroup('Telephone', 'telephone')}
        
        <div className="form-group mt-4">
          <div className="col-sm-offset-2 col-sm-10">
            <button
              className="btn btn-primary"
              type="submit"
            >
              {isEdit ? 'Update Owner' : 'Add Owner'}
            </button>
          </div>
        </div>
      </form>
    </div>
  );
};

export default OwnerForm;