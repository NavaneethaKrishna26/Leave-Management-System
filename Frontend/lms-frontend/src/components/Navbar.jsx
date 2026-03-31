import { logout } from '../services/api';

function Navbar() {
  const role = localStorage.getItem('role') || '';

  return (
    <nav className="navbar">
      <h2>Leave Manager</h2>
      <div className="nav-right">
        <span>Role: {role}</span>
        <button className="btn-danger" onClick={logout}>Logout</button>
      </div>
    </nav>
  );
}

export default Navbar;
