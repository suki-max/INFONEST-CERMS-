import React from 'react';
import { Link } from 'react-router-dom';

export default function Home({ user }) {
  return (
    <div className="container mt-5">

    {/* Hero Section - Clean Version */}
    <div className="text-center mb-5 py-5 bg-dark text-white rounded-4 shadow">
    <h1 className="fw-bold display-4">Welcome to InfoNest</h1>
    <p className="lead opacity-75">A Comprehensive Platform for Campus Management</p>

      {!user && (
        <div className="mt-4">
          <p className="small text-muted text-uppercase tracking-widest">
            Login through the navigation menu to access your portal
          </p>
        </div>
      )}
    </div>

      {/* 3 Main Modules Cards */}
      <div className="row g-4">
        <div className="col-md-4">
          <div className="card h-100 shadow-sm border-0 text-center p-3 rounded-4">
            <div className="card-body">
              <h4 className="fw-bold">Club Management</h4>
              <p className="text-muted small">Join clubs, register for hackathons and manage recruitments.</p>
              <Link to="/clubs" className="btn btn-outline-primary rounded-pill">Browse Clubs</Link>
            </div>
          </div>
        </div>

        <div className="col-md-4">
          <div className="card h-100 shadow-sm border-0 text-center p-3 rounded-4">
            <div className="card-body">
              <h4 className="fw-bold">Schedule Module</h4>
              <p className="text-muted small">Access faculty timetables and class schedules.</p>
              <Link to="/schedule" className="btn btn-outline-primary rounded-pill">View Schedule</Link>
            </div>
          </div>
        </div>

        <div className="col-md-4">
          <div className="card h-100 shadow-sm border-0 text-center p-3 rounded-4">
            <div className="card-body">
              <h4 className="fw-bold">Venue Booking</h4>
              <p className="text-muted small">Check real-time availability and book venues for events.</p>
              <Link to="/venue" className="btn btn-outline-primary rounded-pill">Check Availability</Link>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}