import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import API from '../../services/Api';

export default function AddEditEvent({ user }) {
  const { id } = useParams(); 
  const navigate = useNavigate();
  const [event, setEvent] = useState({
    name: '', description: '', date: '', venueId: '', registrationFormLink: '', clubId: user?.clubId
  });

  useEffect(() => {
    if (id) {
      API.get(`/events/${id}`).then(res => setEvent(res.data)).catch(err => console.log(err));
    }
  }, [id]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (id) {
        await API.put(`/events/${id}`, event);
        alert("Event updated successfully!");
      } else {
        await API.post('/events', event);
        alert("New Event added successfully!");
      }
      navigate('/dashboard');
    } catch (err) {
      alert("Error saving event. Please try again.");
    }
  };

  return (
    <div className="container mt-5 py-4" style={{maxWidth: '700px'}}>
      <div className="card shadow-lg border-0 rounded-4 p-4">
        <h2 className="fw-bold mb-4 text-center">{id ? '📝 Edit Event' : '➕ Add New Event'}</h2>
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label className="form-label fw-bold">Event Name</label>
            <input type="text" className="form-control rounded-3" value={event.name} 
              onChange={(e) => setEvent({...event, name: e.target.value})} required placeholder="Enter event title" />
          </div>
          <div className="mb-3">
            <label className="form-label fw-bold">Description</label>
            <textarea className="form-control rounded-3" rows="3" value={event.description}
              onChange={(e) => setEvent({...event, description: e.target.value})} placeholder="What's the event about?"></textarea>
          </div>
          <div className="row">
            <div className="col-md-6 mb-3">
              <label className="form-label fw-bold">Date</label>
              <input type="date" className="form-control rounded-3" value={event.date}
                onChange={(e) => setEvent({...event, date: e.target.value})} required />
            </div>
            <div className="col-md-6 mb-3">
              <label className="form-label fw-bold">Venue ID/Location</label>
              <input type="text" className="form-control rounded-3" placeholder="e.g. Auditorium" value={event.venueId}
                onChange={(e) => setEvent({...event, venueId: e.target.value})} required />
            </div>
          </div>
          <div className="mb-4">
            <label className="form-label fw-bold">Registration Link (Google Form)</label>
            <input type="url" className="form-control rounded-3" placeholder="https://forms.gle/..." value={event.registrationFormLink}
              onChange={(e) => setEvent({...event, registrationFormLink: e.target.value})} />
          </div>
          <div className="d-flex gap-3">
            <button type="submit" className="btn btn-primary w-100 py-2 fw-bold rounded-pill">Save Event</button>
            <button type="button" onClick={() => navigate('/dashboard')} className="btn btn-outline-secondary w-100 rounded-pill">Cancel</button>
          </div>
        </form>
      </div>
    </div>
  );
}