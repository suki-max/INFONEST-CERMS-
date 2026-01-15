import axios from 'axios';

// Backend ka base URL (Check karein ki 8080 par hi backend chal raha hai)
const API = axios.create({
  baseURL: 'http://localhost:8080/api',
});

// Request Interceptor: Har request ke saath token bhejne ke liye
API.interceptors.request.use(
  (req) => {
    const userData = localStorage.getItem('user');
    
    if (userData) {
      try {
        // LocalStorage se data nikaal kar parse karein
        const user = JSON.parse(userData);
        
        // Agar user object hai to .token le, warna poora string hi token hai
        const token = user.token ? user.token : user;

        if (token) {
          req.headers.Authorization = `Bearer ${token}`;
        }
      } catch (error) {
        // Agar JSON.parse fail ho jaye (matlab storage mein sirf plain string hai)
        req.headers.Authorization = `Bearer ${userData}`;
      }
    }
    return req;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Response Interceptor: Agar token expire ho jaye (401 error) toh handle karne ke liye
API.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 401) {
      console.error("Token expire ho gaya hai ya invalid hai.");
      // Aap chahein toh yahan user ko logout karwa sakte hain:
      // localStorage.removeItem('user');
      // window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

// API Endpoints
export const loginUser = (data) => API.post('/login', data);
export const registerUser = (data) => API.post('/register', data);
export const fetchClubs = () => API.get('/clubs');
export const fetchTeachers = () => API.get('/teachers');
export const fetchVenues = () => API.get('/venues');
export const fetchSchedule = () => API.get('/schedule');
export const fetchUserBookings = (userId) => API.get(`/bookings/user/${userId}`);

export default API;