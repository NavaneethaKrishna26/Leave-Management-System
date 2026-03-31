import { useState, useEffect } from 'react';
import Navbar from '../components/Navbar';
import { getProfile, getMyLeaves, applyLeave } from '../services/api';

function EmployeeDashboard() {
  const [profile, setProfile] = useState(null);
  const [leaves, setLeaves] = useState([]);
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');

  // Leave form state
  const [leaveType, setLeaveType] = useState('SICK');
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
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
        leaveType,
        startDate,
        endDate,
        reason,
      });
      setMessage('Leave applied successfully!');
      setStartDate('');
      setEndDate('');
      setReason('');
      // Refresh leaves list
      const leavesData = await getMyLeaves();
      setLeaves(leavesData || []);
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to apply leave.');
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
                <label>Department</label>
                <span>{profile.department}</span>
              </div>
              <div className="info-item">
                <label>Role</label>
                <span>{profile.role}</span>
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
                <label>Leave Type</label>
                <select value={leaveType} onChange={(e) => setLeaveType(e.target.value)}>
                  <option value="SICK">Sick Leave</option>
                  <option value="CASUAL">Casual Leave</option>
                  <option value="EARNED">Earned Leave</option>
                </select>
              </div>
              <div className="form-group">
                <label>Start Date</label>
                <input
                  type="date"
                  value={startDate}
                  onChange={(e) => setStartDate(e.target.value)}
                  required
                />
              </div>
              <div className="form-group">
                <label>End Date</label>
                <input
                  type="date"
                  value={endDate}
                  onChange={(e) => setEndDate(e.target.value)}
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
                  <th>Type</th>
                  <th>Start</th>
                  <th>End</th>
                  <th>Reason</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>
                {leaves.map((leave, index) => (
                  <tr key={leave.id || index}>
                    <td>{leave.leaveType}</td>
                    <td>{leave.startDate}</td>
                    <td>{leave.endDate}</td>
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
