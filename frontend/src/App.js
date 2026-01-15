import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import Navbar from './components/Navbar';
import Footer from './components/Footer';
import Home from './pages/Home';
import Login from './pages/Login';
import Signup from './pages/Signup';

import ClubList from './modules/club/ClubList';
import ClubDetail from './modules/club/ClubDetail';
import EventDetail from './modules/club/EventDetail';
import AddEditEvent from './modules/club/AddEditEvent'; 
import VenueBooking from './modules/venue/VenueBooking';
import TeacherList from './modules/schedule/TeacherList';
import Dashboard from './modules/dashboard/Dashboard'; 

function App() {
  const [user, setUser] = useState(null);

  useEffect(() => {
    const savedUser = localStorage.getItem('user');
    if (savedUser) setUser(JSON.parse(savedUser));
  }, []);

  const ProtectedRoute = ({ children, allowedRoles }) => {
    if (!user) return <Navigate to="/login" />;
    const userRole = user.role.toUpperCase();
    if (!allowedRoles.includes(userRole)) {
      alert("You do not have permission to access this module.");
      return <Navigate to="/" />;
    }
    return children;
  };

  return (
    <Router>
      <Navbar user={user} setUser={setUser} />
      <main style={{ minHeight: '85vh' }}>
        <Routes>
          {/* HOME DYNAMIC ROUTE */}
          <Route path="/" element={user ? <Dashboard user={user}/> : <Home />} />
          <Route path="/login" element={!user ? <Login setUser={setUser} /> : <Navigate to="/" />} />
          <Route path="/signup" element={!user ? <Signup /> : <Navigate to="/" />} />

          {/* SHARED MODULES */}
          <Route path="/clubs" element={<ClubList user={user} />} />
          <Route path="/club/:id" element={<ClubDetail user={user} />} />
          <Route path="/events/:eventId" element={<EventDetail user={user} />} />
          <Route path="/schedule" element={<TeacherList />} />

          {/* VENUE BOOKING - FIX: Path added for both /venue and /booking */}
          <Route path="/venue" element={
            <ProtectedRoute allowedRoles={['FACULTY', 'OFFICE', 'ADMIN', 'CLUB_OFFICIAL']}>
              <VenueBooking user={user} />
            </ProtectedRoute>
          } />

          <Route path="/booking" element={
            <ProtectedRoute allowedRoles={['FACULTY', 'OFFICE', 'ADMIN', 'CLUB_OFFICIAL']}>
              <VenueBooking user={user} />
            </ProtectedRoute>
          } />

          {/* EVENT MANAGEMENT */}
          <Route path="/add-event" element={
            <ProtectedRoute allowedRoles={['CLUB_OFFICIAL', 'ADMIN']}>
              <AddEditEvent user={user} />
            </ProtectedRoute>
          } />

          <Route path="*" element={<Navigate to="/" />} />
        </Routes>
      </main>
      <Footer />
    </Router>
  );
}

export default App;