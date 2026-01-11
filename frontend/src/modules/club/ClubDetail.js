import React, { useState, useEffect } from 'react';
import { useParams, Link, useNavigate } from 'react-router-dom';
import API from '../../services/Api';

export default function ClubDetail({ user }) {
    const { id } = useParams();
    const navigate = useNavigate();
    const [data, setData] = useState(null);
    const [loading, setLoading] = useState(true);

    const isOfficial = user?.role?.toLowerCase() === 'club_official' && user?.clubId === id;
    const isAdmin = user?.role?.toLowerCase() === 'admin';

    useEffect(() => {
        API.get(`/clubs/${id}`)
            .then(res => { setData(res.data); setLoading(false); })
            .catch(() => setLoading(false));
    }, [id]);

    const toggleEventVisibility = async (eventId, currentHiddenStatus) => {
        try {
            // Backend endpoint to update is_hidden field
            await API.put(`/events/${eventId}/toggle-visibility`, { isHidden: !currentHiddenStatus });
            
            // UI refresh karne ke liye state update
            setData(prev => ({
                ...prev,
                events: prev.events.map(e => 
                    e.eventId === eventId ? { ...e, hidden: !currentHiddenStatus } : e
                )
            }));
        } catch (err) {
            alert("Visibility update karne mein error aaya!");
        }
    };

    if (loading) return <div className="text-center mt-5 py-5"><div className="spinner-border text-primary"></div></div>;
    if (!data) return <div className="container mt-5 alert alert-danger">Club details not found.</div>;

    return (
        <div className="container mt-4 mb-5">
            {/* Back Button */}
            <button onClick={() => navigate(-1)} className="btn btn-sm btn-outline-secondary mb-3 rounded-pill px-3">
                ← Go Back
            </button>

            <div className="bg-light p-5 rounded-4 shadow-sm mb-4">
                <h1 className="fw-bold display-5">{data.club.name}</h1>
                <p className="text-muted small">ID: {data.club.clubId}</p>
                
                {/* MANAGEMENT SECTION (No global hide buttons here) */}
                {(isOfficial || isAdmin) && (
                    <div className="mt-4 p-4 bg-white rounded-4 border-start border-primary border-5 shadow-sm">
                        <h5 className="fw-bold mb-3">Management Panel</h5>
                        <div className="d-flex flex-wrap gap-2">
                            {isOfficial && (
                                <>
                                    <Link to="/add-event" className="btn btn-sm btn-success rounded-pill px-3">+ Add Event</Link>
                                    <button className="btn btn-sm btn-outline-warning rounded-pill px-3">Update Event</button>
                                    <button className="btn btn-sm btn-outline-danger rounded-pill px-3">Remove Event</button>
                                </>
                            )}
                            {isAdmin && <button className="btn btn-sm btn-dark rounded-pill px-3">Add/Remove Official</button>}
                        </div>
                    </div>
                )}
            </div>

            <div className="row g-4">
                {/* LEFT: CLUB OFFICIALS LIST */}
                <div className="col-md-4">
                    <div className="card border-0 shadow-sm p-4 rounded-4 bg-white">
                        <h5 className="fw-bold mb-3">Club Officials</h5>
                        {data.officials && data.officials.length > 0 ? data.officials.map(off => (
                            <div key={off.userId} className="p-3 mb-2 bg-light rounded-3">
                                <p className="mb-0 fw-bold small">{off.firstName} {off.lastName}</p>
                                <small className="text-primary fw-bold text-uppercase" style={{fontSize: '10px'}}>{off.role}</small>
                            </div>
                        )) : <p className="text-muted small">No officials assigned.</p>}
                    </div>
                </div>

                {/* RIGHT: EVENTS LIST WITH INDIVIDUAL TOGGLE */}
                <div className="col-md-8">
                    <div className="card border-0 shadow-sm p-4 rounded-4 bg-white">
                        <h3 className="fw-bold mb-4 h4">Upcoming Events</h3>
                        {data.events?.map(event => (
                            // Students ko hidden events nahi dikhenge, Admin aur Official ko hamesha dikhenge
                            (!event.hidden || isAdmin || isOfficial) && (
                                <div key={event.eventId} className="card p-3 mb-3 border-0 bg-light rounded-3 shadow-sm">
                                    <div className="d-flex justify-content-between align-items-center">
                                        <div>
                                            <h6 className="fw-bold mb-1">
                                                {event.name} 
                                                {event.hidden && <span className="badge bg-danger ms-2">Hidden</span>}
                                            </h6>
                                            <small className="text-muted">📍 {event.venueId || 'TBA'} | 📅 {event.date}</small>
                                        </div>
                                        <div className="d-flex align-items-center gap-3">
                                            {/* ADMIN ONLY: Individual Hide/Unhide Toggle */}
                                            {isAdmin && (
                                                <div className="form-check form-switch border-end pe-3">
                                                    <input 
                                                        className="form-check-input" 
                                                        type="checkbox" 
                                                        id={`hide-${event.eventId}`}
                                                        checked={!event.hidden} 
                                                        onChange={() => toggleEventVisibility(event.eventId, event.hidden)} 
                                                    />
                                                    <label className="form-check-label small fw-bold" htmlFor={`hide-${event.eventId}`}>
                                                        {event.hidden ? "Hidden" : "Live"}
                                                    </label>
                                                </div>
                                            )}
                                            <Link to={`/events/${event.eventId}`} className="btn btn-sm btn-primary rounded-pill px-3">View</Link>
                                        </div>
                                    </div>
                                </div>
                            )
                        ))}
                        {data.events?.length === 0 && <p className="text-muted">No events found.</p>}
                    </div>
                </div>
            </div>
        </div>
    );
}