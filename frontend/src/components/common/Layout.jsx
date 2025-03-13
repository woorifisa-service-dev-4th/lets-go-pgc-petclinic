import { Outlet, NavLink } from 'react-router-dom';
import Header from './Header';
import Footer from './Footer';

const Layout = () => {
  return (
    <>
      <Header />
      <div className="container-fluid">
        <div className="container xd-container">
          <Outlet />
          <Footer />
        </div>
      </div>
    </>
  );
};

export default Layout;