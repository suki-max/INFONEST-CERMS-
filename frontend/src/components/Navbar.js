import React from 'react';
import { Link, useNavigate } from 'react-router-dom';

export default function Navbar({ user, setUser }) {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem('user'); // Local storage clear karein
    setUser(null); // Global state clear karein
    navigate('/login'); // Login page par bhej dein
  };

  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark px-4 shadow">
      <Link className="navbar-brand fw-bold" to="/">InfoNest</Link>
      
      {/* Navbar Toggler for Mobile View */}
      <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
        <span className="navbar-toggler-icon"></span>
      </button>

      <div className="collapse navbar-collapse" id="navbarNav">
        {/* Left Side: Home, About, Contact */}
        <ul className="navbar-nav me-auto">
          <li className="nav-item">
            <Link className="nav-link" to="/">Home</Link>
          </li>
          <li className="nav-item">
            <Link className="nav-link" to="/about">About</Link>
          </li>
          <li className="nav-item">
            <Link className="nav-link" to="/contact">Contact</Link>
          </li>
        </ul>

        {/* Right Side: Login/Logout Logic */}
        <ul className="navbar-nav align-items-center">
          {user ? (
            // Jab user Login ho
            <li className="nav-item d-flex align-items-center">
              <span className="text-light me-3 small">
                Welcome, <strong>{user.firstName}</strong> 
                <span className="badge bg-primary ms-2" style={{ fontSize: '0.75rem' }}>
                  {user.role.replace('_', ' ').toUpperCase()}
                </span>
              </span>
              <button onClick={handleLogout} className="btn btn-outline-danger btn-sm rounded-pill px-3">
                Logout
              </button>
            </li>
          ) : (
            // Jab user Login NA ho
            <>
              <li className="nav-item ms-lg-2 mt-2 mt-lg-0">
                <Link className="btn btn-sm btn-outline-primary px-3 rounded-pill w-100" to="/login">Sign In</Link>
              </li>
              <li className="nav-item ms-lg-2 mt-2 mt-lg-0">
                <Link className="btn btn-sm btn-primary px-3 rounded-pill w-100" to="/signup">Get Started</Link>
              </li>
            </>
          )}
        </ul>
      </div>
    </nav>
  );
}