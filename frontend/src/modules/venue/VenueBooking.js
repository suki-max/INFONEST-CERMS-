import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import API from '../../services/Api';

export default function VenueBooking({ user }) {
    const navigate = useNavigate();
    const [venues, setVenues] = useState([]); 
    const [selectedVenue, setSelectedVenue] = useState(null); 
    const [date, setDate] = useState("");
    const [startTime, setStartTime] = useState("");
    const [endTime, setEndTime] = useState("");
    const [capacity, setCapacity] = useState("");
    const [purpose, setPurpose] = useState(""); 
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        API.get('/venues')
            .then(res => {
                setVenues(res.data);
                setLoading(false);
            })
            .catch(err => {
                console.error("Venues fetch error:", err);
                setLoading(false);
            });
    }, []);

    const handleCheckAndBook = async (e) => {
        e.preventDefault();

        // 1. Capacity Check
        if (selectedVenue && parseInt(capacity) > selectedVenue.capacity) {
            alert(`Capacity Error: Is venue mein sirf ${selectedVenue.capacity} log aa sakte hain.`);
            return;
        }

        // 2. Time Logic Check
        if (startTime >= endTime) {
            alert("Time Error: End Time, Start Time ke baad honi chahiye.");
            return;
        }

        try {
            const checkData = {
                venueId: parseInt(selectedVenue.venueId),
                date: date,
                startTime: startTime,
                endTime: endTime
            };

            // Database Availability Check
            const res = await API.post('/bookings/check-availability', checkData);

            if (res.data.available) {
                if (window.confirm(`Venue available hai! Kya aap "${purpose}" ke liye booking confirm karna chahte hain?`)) {
                    const bookingData = {
                        venueId: parseInt(selectedVenue.venueId),
                        userId: user.userId,
                        bookingDate: date,
                        startTime: startTime,
                        endTime: endTime,
                        purpose: purpose,
                        capacity: parseInt(capacity)
                    };
                    
                    await API.post('/bookings/create', bookingData);
                    alert("Booking Successful!");
                    navigate('/'); 
                }
            } else {
                alert(`Conflict: Sorry! ${selectedVenue.address} is slot par booked hai.`);
            }
        } catch (err) {
            console.error("Booking validation error:", err);
            alert("Check failed: Backend se connect nahi ho pa raha.");
        }
    };

    if (loading) return <div className="text-center mt-5 py-5"><div className="spinner-border text-primary"></div></div>;

    return (
        <div className="container mt-4 mb-5">
            <button onClick={() => navigate(-1)} className="btn btn-sm btn-outline-secondary mb-3 rounded-pill px-3">
                ← Go Back
            </button>

            <div className="card shadow-sm border-0 rounded-4 p-5 bg-white">
                <h2 className="fw-bold mb-4 text-primary">Reserve Venue</h2>
                
                <form onSubmit={handleCheckAndBook}>
                    <div className="mb-3">
                        <label className="form-label small fw-bold text-muted">EVENT / PURPOSE NAME</label>
                        <input 
                            type="text" 
                            className="form-control rounded-3 py-2" 
                            placeholder="e.g. Annual Fest, Guest Lecture..." 
                            value={purpose}
                            onChange={(e) => setPurpose(e.target.value)}
                            required 
                        />
                    </div>

                    <div className="mb-3">
                        <label className="form-label small fw-bold text-muted">SELECT VENUE</label>
                        <select 
                            className="form-select rounded-3 py-2" 
                            onChange={(e) => {
                                const v = venues.find(item => item.venueId === parseInt(e.target.value));
                                setSelectedVenue(v);
                            }}
                            required
                        >
                            <option value="">Choose Venue...</option>
                            {venues.map(v => (
                                <option key={v.venueId} value={v.venueId}>
                                    {v.address} (Max: {v.capacity})
                                </option>
                            ))}
                        </select>
                    </div>

                    <div className="mb-3">
                        <label className="form-label small fw-bold text-muted">EXPECTED CAPACITY</label>
                        <input 
                            type="number" 
                            className="form-control rounded-3 py-2" 
                            value={capacity}
                            onChange={(e) => setCapacity(e.target.value)}
                            required 
                        />
                    </div>

                    <div className="mb-3">
                        <label className="form-label small fw-bold text-muted">DATE</label>
                        <input 
                            type="date" 
                            className="form-control rounded-3 py-2" 
                            min={new Date().toISOString().split('T')[0]}
                            value={date}
                            onChange={(e) => setDate(e.target.value)}
                            required 
                        />
                    </div>

                    <div className="row">
                        <div className="col-md-6 mb-4">
                            <label className="form-label small fw-bold text-muted">START TIME</label>
                            <input 
                                type="time" 
                                className="form-control rounded-3 py-2" 
                                value={startTime}
                                onChange={(e) => setStartTime(e.target.value)}
                                required
                            />
                        </div>
                        <div className="col-md-6 mb-4">
                            <label className="form-label small fw-bold text-muted">END TIME</label>
                            <input 
                                type="time" 
                                className="form-control rounded-3 py-2" 
                                value={endTime}
                                onChange={(e) => setEndTime(e.target.value)}
                                required
                            />
                        </div>
                    </div>

                    <button type="submit" className="btn btn-primary w-100 rounded-pill py-2 fw-bold shadow">
                        Verify Availability & Confirm
                    </button>
                </form>
            </div>
        </div>
    );
}