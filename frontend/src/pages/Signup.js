import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import axios from 'axios';

export default function Signup() {
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    role: 'STUDENT', // Default role matching formatRole logic
    clubId: ''       // New field for Club Officials
  });
  const navigate = useNavigate();

  const handleSignup = async (e) => {
    e.preventDefault();
    try {
      // Backend registration endpoint
      await axios.post('http://localhost:8080/api/register', formData);
      alert("Registration Successful! Please login.");
      navigate('/login');
    } catch (err) {
      alert("Registration failed. Check if email is already in use.");
    }
  };

  return (
    <div className="container mt-5">
      <div className="row justify-content-center">
        <div className="col-md-5">
          <div className="card shadow-sm p-4 border-0 rounded-4">
            <h3 className="text-center mb-4 fw-bold text-success">Create InfoNest Account</h3>
            <form onSubmit={handleSignup}>
              <div className="row">
                <div className="col-md-6 mb-3">
                  <label className="form-label small fw-bold">First Name</label>
                  <input type="text" className="form-control shadow-sm" required 
                    onChange={(e) => setFormData({...formData, firstName: e.target.value})} />
                </div>
                <div className="col-md-6 mb-3">
                  <label className="form-label small fw-bold">Last Name</label>
                  <input type="text" className="form-control shadow-sm" required 
                    onChange={(e) => setFormData({...formData, lastName: e.target.value})} />
                </div>
              </div>

              <div className="mb-3">
                <label className="form-label small fw-bold">Email Address</label>
                <input type="email" className="form-control shadow-sm" required 
                  onChange={(e) => setFormData({...formData, email: e.target.value})} />
              </div>

              <div className="mb-3">
                <label className="form-label small fw-bold">I am a:</label>
                <select className="form-select shadow-sm" 
                  value={formData.role}
                  onChange={(e) => setFormData({...formData, role: e.target.value, clubId: ''})}>
                    <option value="STUDENT">Student</option>
                    <option value="FACULTY">Faculty</option>
                    <option value="CLUB_OFFICIAL">Club Official</option>
                    <option value="OFFICE">Office User</option>
                    <option value="ADMIN">Admin</option>
                </select>
              </div>

              {/* Conditional Rendering: Club ID field only for Club Officials */}
              {formData.role === 'CLUB_OFFICIAL' && (
                <div className="mb-3 animate__animated animate__fadeIn">
                  <label className="form-label small fw-bold text-primary">Enter Club ID</label>
                  <input type="text" className="form-control border-primary shadow-sm" 
                    placeholder="e.g. CS2501"
                    required={formData.role === 'CLUB_OFFICIAL'}
                    onChange={(e) => setFormData({...formData, clubId: e.target.value})} />
                  <div className="form-text">Yeh ID aapke club portal ke liye zaroori hai.</div>
                </div>
              )}

              <div className="mb-4">
                <label className="form-label small fw-bold">Password</label>
                <input type="password" className="form-control shadow-sm" required 
                  onChange={(e) => setFormData({...formData, password: e.target.value})} />
              </div>

              <button type="submit" className="btn btn-success w-100 py-2 fw-bold shadow-sm">Sign Up</button>
            </form>
            <p className="mt-3 text-center small text-muted">
              Already have an account? <Link to="/login" className="text-decoration-none fw-bold">Login here</Link>
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}