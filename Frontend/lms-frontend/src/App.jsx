import { Routes, Route, Navigate } from 'react-router-dom';
import Home from './pages/Home';
import Login from './pages/Login';
import EmployeeDashboard from './pages/EmployeeDashboard';
import ManagerDashboard from './pages/ManagerDashboard';
import ProtectedRoute from './components/ProtectedRoute';
import './App.css';

function App() {
  return (
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/login" element={<Login />} />
      <Route
        path="/employee"
        element={
          <ProtectedRoute allowedRole="ROLE_EMPLOYEE">
            <EmployeeDashboard />
          </ProtectedRoute>
        }
      />
      <Route
        path="/manager"
        element={
          <ProtectedRoute allowedRole="ROLE_MANAGER">
            <ManagerDashboard />
          </ProtectedRoute>
        }
      />
      <Route path="*" element={<Navigate to="/" />} />
    </Routes>
  );
}

export default App;
