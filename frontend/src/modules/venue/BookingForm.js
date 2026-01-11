import React, { useState } from 'react';
import { postBooking } from '../../services/Api';

export default function BookingForm() {
  const [formData, setFormData] = useState({ venueId: '', purpose: '', duration: '' });

  const handleSubmit = (e) => {
    e.preventDefault();
    postBooking(formData).then(() => alert("Booking Request Sent!"));
  };

  return (
    <form className="p-3 border rounded shadow-sm" onSubmit={handleSubmit}>
      <h4>Book a Venue</h4>
      <input className="form-control mb-2" placeholder="Venue Name/ID" onChange={e => setFormData({...formData, venueId: e.target.value})} />
      <input className="form-control mb-2" placeholder="Purpose" onChange={e => setFormData({...formData, purpose: e.target.value})} />
      <input className="form-control mb-2" placeholder="Duration (e.g. 2 hrs)" onChange={e => setFormData({...formData, duration: e.target.value})} />
      <button className="btn btn-dark w-100">Submit Booking</button>
    </form>
  );
}