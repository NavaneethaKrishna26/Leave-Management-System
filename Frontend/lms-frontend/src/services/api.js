// api.js
import axios from "axios";

const API_BASE_URL = "http://localhost:8080";

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");

  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem("token");
      localStorage.removeItem("role");
      window.location.href = "/login";
    }

    if (error.response?.status === 403) {
      alert("Access Denied");
    }

    return Promise.reject(error);
  }
);

export const authLogin = async (params) => {
  try {
    const res = await api.post("/auth/login", params);

    // store token & role
    localStorage.setItem("token", res.data.token);
    localStorage.setItem("role", res.data.role);

    return res.data;
  } catch (error) {
    console.error("Login failed:", error.response?.data || error.message);
    throw error;
  }
};

export const getProfile = async () => {
  try {
    const res = await api.get("/api/employees/profile");
    return res.data.data;
  } catch (error) {
    console.error(error);
    throw error;
  }
};

export const getTeam = async () => {
  try {
    const res = await api.get("/api/employees/team");
    return res.data.data;
  } catch (error) {
    console.error(error);
    throw error;
  }
};

// ─────────────────────────────────────────────
// LEAVE APIs
// ─────────────────────────────────────────────
export const applyLeave = async (leaveData) => {
  try {
    const res = await api.post("/api/leaves/apply", leaveData);
    return res.data.data;
  } catch (error) {
    console.error(error);
    throw error;
  }
};

export const getMyLeaves = async () => {
  try {
    const res = await api.get("/api/leaves/my");
    return res.data.data;
  } catch (error) {
    console.error(error);
    throw error;
  }
};

export const getTeamLeaves = async () => {
  try {
    const res = await api.get("/api/leaves/team");
    return res.data.data;
  } catch (error) {
    console.error(error);
    throw error;
  }
};

export const approveLeave = async (leaveId) => {
  try {
    const res = await api.put(`/api/leaves/${leaveId}/approve`);
    return res.data.data;
  } catch (error) {
    console.error(error);
    throw error;
  }
};

export const rejectLeave = async (leaveId) => {
  try {
    const res = await api.put(`/api/leaves/${leaveId}/reject`);
    return res.data.data;
  } catch (error) {
    console.error(error);
    throw error;
  }
};

export const logout = () => {
  localStorage.removeItem("token");
  localStorage.removeItem("role");
  window.location.href = "/login";
};

export default api;