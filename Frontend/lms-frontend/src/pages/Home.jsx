import { Link } from 'react-router-dom';

function Home() {
  return (
    <div>
      {/* Home Navbar */}
      <nav className="navbar">
        <h2>Leave Manager</h2>
        <div className="nav-right">
          <Link to="/login">
            <button className="btn-primary">Login</button>
          </Link>
        </div>
      </nav>

      {/* Hero Section */}
      <div className="home-hero">
        <h1>Leave Management System</h1>
        <p>Manage your team's leave requests with ease. Apply for leaves, track approvals, and stay organized.</p>
        <div className="home-features">
          <div className="feature-card">
            <h3>📝 Apply Leave</h3>
            <p>Submit leave requests quickly with just a few clicks.</p>
          </div>
          <div className="feature-card">
            <h3>✅ Track Status</h3>
            <p>View the status of your leave requests in real time.</p>
          </div>
          <div className="feature-card">
            <h3>👥 Team Overview</h3>
            <p>Managers can view and manage their team's leave requests.</p>
          </div>
        </div>
        <Link to="/login">
          <button className="btn-primary" style={{ marginTop: '24px', padding: '12px 32px', fontSize: '16px' }}>
            Get Started
          </button>
        </Link>
      </div>
    </div>
  );
}

export default Home;
