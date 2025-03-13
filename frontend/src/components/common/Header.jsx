import { NavLink } from 'react-router-dom';

const Header = () => {
  return (
    <nav className="navbar navbar-expand-lg navbar-dark" role="navigation">
      <div className="container-fluid">
        <a className="navbar-brand" href="/">
          <span></span>
        </a>
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#main-navbar"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="main-navbar">
          <ul className="nav navbar-nav me-auto">
            <li className="nav-item">
              <NavLink to="/" className={({ isActive }) => 
                isActive ? "nav-link active" : "nav-link"
              }>
                <span className="fa fa-home"></span>
                <span>Home</span>
              </NavLink>
            </li>
            <li className="nav-item">
              <NavLink to="/owners/find" className={({ isActive }) => 
                isActive ? "nav-link active" : "nav-link"
              }>
                <span className="fa fa-search"></span>
                <span>Find owners</span>
              </NavLink>
            </li>
            <li className="nav-item">
              <NavLink to="/vets.html" className={({ isActive }) => 
                isActive ? "nav-link active" : "nav-link"
              }>
                <span className="fa fa-th-list"></span>
                <span>Veterinarians</span>
              </NavLink>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default Header;