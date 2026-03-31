import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { authLogin } from '../services/api';

function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    try {
      const res = await authLogin({ email, password });
      if (res.role === 'ROLE_MANAGER') {
        navigate('/manager');
      } else {
        navigate('/employee');
      }
    } catch (err) {
      setError(
        err.response?.data?.error ||
        err.response?.data?.message ||
        'Login failed. Please try again.'
      );
    }
  };

  return (
    <div className="login-page">
      <div className="login-box">
        <h2>Leave Manager</h2>

        {error && <p className="error-msg">{error}</p>}

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Email</label>
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="Enter your email"
              required
            />
          </div>

          <div className="form-group">
            <label>Password</label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="Enter your password"
              minLength={6}
              required
            />
          </div>

          <button type="submit" className="btn-primary">Login</button>
        </form>
      </div>
    </div>
  );
}

export default Login;
