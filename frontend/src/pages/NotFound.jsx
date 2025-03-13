import { Link } from 'react-router-dom';

const NotFound = () => {
  return (
    <div className="text-center mt-5">
      <img src="/src/assets/images/pets.png" alt="Pet Clinic" className="img-fluid mb-3" />
      <h2>Something happened...</h2>
      <p>We couldn't find the page you're looking for</p>
      <Link to="/" className="btn btn-primary mt-3">Return to Home</Link>
    </div>
  );
};

export default NotFound;