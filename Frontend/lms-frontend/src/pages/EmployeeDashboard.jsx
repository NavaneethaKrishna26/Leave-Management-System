import { useState, useEffect } from 'react';
import Navbar from '../components/Navbar';
import { getProfile, getMyLeaves, applyLeave } from '../services/api';

function EmployeeDashboard() {
  const [profile, setProfile] = useState(null);
  const [leaves, setLeaves] = useState([]);
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');

  // Leave form state
  const [fromDate, setFromDate] = useState('');
  const [toDate, setToDate] = useState('');
  const [reason, setReason] = useState('');

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    try {
      const profileData = await getProfile();
      setProfile(profileData);
      const leavesData = await getMyLeaves();
      setLeaves(leavesData || []);
    } catch (err) {
      console.error('Failed to load data:', err);
    }
  };

  const handleApplyLeave = async (e) => {
    e.preventDefault();
    setMessage('');
    setError('');

    try {
      await applyLeave({
        fromDate,
        toDate,
        reason,
      });
      setMessage('Leave applied successfully!');
      setFromDate('');
      setToDate('');
      setReason('');
      // Refresh leaves list
      const leavesData = await getMyLeaves();
      setLeaves(leavesData || []);
    } catch (err) {
      setError(err.response?.data?.error || 'Failed to apply leave.');
    }
  };

  const getStatusBadge = (status) => {
    const s = status?.toUpperCase();
    if (s === 'APPROVED') return <span className="badge badge-approved">Approved</span>;
    if (s === 'REJECTED') return <span className="badge badge-rejected">Rejected</span>;
    return <span className="badge badge-pending">Pending</span>;
  };

  return (
    <div>
      <Navbar />
      <div className="dashboard">
        <h1>Employee Dashboard</h1>

        {/* Profile */}
        {profile && (
          <div className="card">
            <h3 className="section-title">My Profile</h3>
            <div className="profile-card">
              <div className="info-item">
                <label>Name</label>
                <span>{profile.name}</span>
              </div>
              <div className="info-item">
                <label>Email</label>
                <span>{profile.email}</span>
              </div>
              <div className="info-item">
                <label>Role</label>
                <span>{profile.role?.replace('ROLE_', '')}</span>
              </div>
            </div>
          </div>
        )}

        {/* Apply Leave */}
        <div className="card">
          <h3 className="section-title">Apply for Leave</h3>
          {message && <p className="msg-success">{message}</p>}
          {error && <p className="msg-error">{error}</p>}
          <form onSubmit={handleApplyLeave}>
            <div className="leave-form">
              <div className="form-group">
                <label>From Date</label>
                <input
                  type="date"
                  value={fromDate}
                  onChange={(e) => setFromDate(e.target.value)}
                  required
                />
              </div>
              <div className="form-group">
                <label>To Date</label>
                <input
                  type="date"
                  value={toDate}
                  onChange={(e) => setToDate(e.target.value)}
                  required
                />
              </div>
              <div className="form-group">
                <label>Reason</label>
                <input
                  type="text"
                  value={reason}
                  onChange={(e) => setReason(e.target.value)}
                  placeholder="Reason for leave"
                  required
                />
              </div>
              <button type="submit" className="btn-primary">Apply</button>
            </div>
          </form>
        </div>

        {/* My Leaves */}
        <div className="card">
          <h3 className="section-title">My Leaves</h3>
          {leaves.length === 0 ? (
            <p>No leave records found.</p>
          ) : (
            <table>
              <thead>
                <tr>
                  <th>From</th>
                  <th>To</th>
                  <th>Reason</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>
                {leaves.map((leave, index) => (
                  <tr key={leave.id || index}>
                    <td>{leave.fromDate}</td>
                    <td>{leave.toDate}</td>
                    <td>{leave.reason}</td>
                    <td>{getStatusBadge(leave.status)}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </div>
      </div>
    </div>
  );
}

export default EmployeeDashboard;
