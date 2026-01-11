import axios from 'axios';

const API = axios.create({
  baseURL: 'http://localhost:8080/api',
});

API.interceptors.request.use((req) => {
  const user = JSON.parse(localStorage.getItem('user'));
  if (user && user.token) {
    req.headers.Authorization = `Bearer ${user.token}`;
  }
  return req;
});
export const loginUser = (data) => API.post('/login', data);
export const registerUser = (data) => API.post('/register', data);
export const fetchClubs = () => API.get('/clubs');
export const fetchTeachers = () => API.get('/teachers');
export const fetchVenues = () => API.get('/venues');
export const fetchSchedule = () => API.get('/schedule');
export const fetchUserBookings = (userId) => API.get(`/bookings/user/${userId}`);

export default API;