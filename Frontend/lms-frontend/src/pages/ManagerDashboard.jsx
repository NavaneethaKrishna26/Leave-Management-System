import { useState, useEffect } from 'react';
import Navbar from '../components/Navbar';
import { getProfile, getTeam, getTeamLeaves, approveLeave, rejectLeave } from '../services/api';

function ManagerDashboard() {
  const [profile, setProfile] = useState(null);
  const [team, setTeam] = useState([]);
  const [teamLeaves, setTeamLeaves] = useState([]);
  const [message, setMessage] = useState('');

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    try {
      const profileData = await getProfile();
      setProfile(profileData);
      const teamData = await getTeam();
      setTeam(teamData || []);
      const leavesData = await getTeamLeaves();
      setTeamLeaves(leavesData || []);
    } catch (err) {
      console.error('Failed to load data:', err);
    }
  };

  const handleApprove = async (leaveId) => {
    try {
      await approveLeave(leaveId);
      setMessage('Leave approved!');
      const leavesData = await getTeamLeaves();
      setTeamLeaves(leavesData || []);
    } catch (err) {
      console.error('Approve failed:', err);
    }
  };

  const handleReject = async (leaveId) => {
    try {
      await rejectLeave(leaveId);
      setMessage('Leave rejected.');
      const leavesData = await getTeamLeaves();
      setTeamLeaves(leavesData || []);
    } catch (err) {
      console.error('Reject failed:', err);
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
        <h1>Manager Dashboard</h1>

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

        {/* Team */}
        <div className="card">
          <h3 className="section-title">My Team</h3>
          {team.length === 0 ? (
            <p>No team members found.</p>
          ) : (
            <div className="team-list">
              {team.map((member, index) => (
                <div className="team-member" key={member.id || index}>
                  <div className="name">{member.name}</div>
                  <div className="email">{member.email}</div>
                </div>
              ))}
            </div>
          )}
        </div>

        {/* Team Leaves */}
        <div className="card">
          <h3 className="section-title">Team Leave Requests</h3>
          {message && <p className="msg-success">{message}</p>}
          {teamLeaves.length === 0 ? (
            <p>No leave requests found.</p>
          ) : (
            <table>
              <thead>
                <tr>
                  <th>Employee</th>
                  <th>From</th>
                  <th>To</th>
                  <th>Reason</th>
                  <th>Status</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {teamLeaves.map((leave, index) => (
                  <tr key={leave.id || index}>
                    <td>{leave.employeeName}</td>
                    <td>{leave.fromDate}</td>
                    <td>{leave.toDate}</td>
                    <td>{leave.reason}</td>
                    <td>{getStatusBadge(leave.status)}</td>
                    <td>
                      {leave.status?.toUpperCase() === 'PENDING' && (
                        <div className="action-btns">
                          <button
                            className="btn-success"
                            onClick={() => handleApprove(leave.id)}
                          >
                            Approve
                          </button>
                          <button
                            className="btn-danger"
                            onClick={() => handleReject(leave.id)}
                          >
                            Reject
                          </button>
                        </div>
                      )}
                    </td>
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

export default ManagerDashboard;
