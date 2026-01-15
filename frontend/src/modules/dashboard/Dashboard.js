import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import API from '../../services/Api';

export default function Dashboard({ user }) {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (user) {
      // Backend calls for basic stats if needed
      API.get(`/dashboard/${user.userId}/${user.role.toLowerCase()}`)
        .then(res => { setData(res.data); setLoading(false); })
        .catch(() => setLoading(false));
    }
  }, [user]);

  if (loading) return <div className="text-center mt-5 py-5"><div className="spinner-border text-primary"></div></div>;

  const role = user.role.toLowerCase();

  return (
    <div className="container mt-5">
      {/* Hero Section */}
      <div className="text-center mb-5 py-5 bg-dark text-white rounded-4 shadow">
        <h1 className="fw-bold display-4">InfoNest Portal</h1>
        <p className="lead opacity-75">Welcome back, {user.firstName}!</p>
        <div className="mt-3">
            <span className="badge bg-warning text-dark px-3 py-2 rounded-pill fw-bold">
                {role.toUpperCase()} MODE
            </span>
        </div>
      </div>

      <div className="row g-4 mb-5 text-center">
        {/* Module 1: Clubs */}
        <div className="col-md-4">
          <div className="card h-100 shadow-sm border-0 p-4 rounded-4">
            <h4 className="fw-bold">Club Management</h4>
            <p className="text-muted small">Join clubs and browse upcoming events.</p>
            <div className="d-grid gap-2 mt-4">
              <Link to="/clubs" className="btn btn-outline-primary rounded-pill">Browse Clubs</Link>
              {/* "Manage All Clubs" button removed for clean UI */}
            </div>
          </div>
        </div>

        {/* Module 2: Schedule */}
        <div className="col-md-4">
          <div className="card h-100 shadow-sm border-0 p-4 rounded-4">
            <h4 className="fw-bold">Schedule Module</h4>
            <p className="text-muted small">View faculty and class timings.</p>
            <div className="d-grid gap-2 mt-4">
              <Link to="/schedule" className="btn btn-outline-primary rounded-pill">View Schedule</Link>
              {(role === 'office' || role === 'office_user') && (
                <button className="btn btn-dark rounded-pill">Upload New Schedule</button>
              )}
            </div>
          </div>
        </div>

        {/* Module 3: Venue - Student cannot see */}
          <div className="col-md-4">
            <div className="card h-100 shadow-sm border-0 p-4 rounded-4">
              <h4 className="fw-bold">Venue Booking</h4>
              <p className="text-muted small">Reserve halls and check availability.</p>
              <div className="d-grid gap-2 mt-4">
                {/* Student ko message dikhayenge, baaki roles ko Link denge */}
                {role === 'student' ? (
                  <button 
                    className="btn btn-outline-primary rounded-pill" 
                    onClick={() => alert("Access Restricted: Students are not allowed to book venues or check internal availability.")}
                  >
                    Check Availability
                  </button>
                ) : (
                  <Link to="/venue" className="btn btn-outline-primary rounded-pill">
                    Check Availability
                  </Link>
                )}
              </div>
            </div>
          </div>
      </div>
     
    </div>
  );
}