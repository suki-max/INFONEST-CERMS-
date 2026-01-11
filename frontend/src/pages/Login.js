import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import axios from 'axios';

export default function Login({ setUser }) {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const res = await axios.post('http://localhost:8080/api/login', { email, password });
      
      const appUser = {
        ...res.data,
        role: res.data.role.toLowerCase() 
      };

      localStorage.setItem('user', JSON.stringify(appUser));
      setUser(appUser);
      
      // Seedha Home par bhejein, App.js ab Dashboard render karega
      navigate('/'); 
    } catch (err) {
      alert("Login failed! Check credentials.");
    }
  };

  return (
    <div className="container mt-5 py-5 d-flex justify-content-center">
      <div className="card shadow-lg border-0 rounded-4 p-4" style={{maxWidth: '400px', width: '100%'}}>
        <h3 className="text-center fw-bold mb-4">Sign In</h3>
        <form onSubmit={handleLogin}>
          <div className="mb-3">
            <label className="form-label fw-bold">Email</label>
            <input type="email" className="form-control rounded-pill" required 
              onChange={(e) => setEmail(e.target.value)} />
          </div>
          <div className="mb-4">
            <label className="form-label fw-bold">Password</label>
            <input type="password" className="form-control rounded-pill" required 
              onChange={(e) => setPassword(e.target.value)} />
          </div>
          <button type="submit" className="btn btn-primary w-100 rounded-pill py-2 fw-bold">Login</button>
        </form>
        <p className="mt-3 text-center small">New user? <Link to="/signup">Create account</Link></p>
      </div>
    </div>
  );
}