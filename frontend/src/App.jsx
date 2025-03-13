import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Layout from './components/common/Layout';
import Home from './pages/Home';
import OwnersPage from './pages/OwnersPage';
import OwnerPage from './pages/OwnerPage';
import NotFound from './pages/NotFound';
import 'bootstrap/dist/css/bootstrap.min.css';
import './index.css';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<Home />} />
          <Route path="owners">
            <Route index element={<OwnersPage />} />
            <Route path="find" element={<OwnersPage />} />
            <Route path="new" element={<OwnerPage />} />
            <Route path=":ownerId" element={<OwnerPage />} />
            <Route path=":ownerId/edit" element={<OwnerPage isEdit={true} />} />
            <Route path=":ownerId/pets/new" element={<OwnerPage petForm={true} />} />
            <Route path=":ownerId/pets/:petId/edit" element={<OwnerPage petForm={true} isEdit={true} />} />
          </Route>
          {/* <Route path="vets.html" element={<VetsPage />} /> */}
          <Route path="*" element={<NotFound />} />
        </Route>
      </Routes>
    </Router>
  );
}

export default App;