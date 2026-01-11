import React, { useState, useEffect } from 'react';
import { useParams, Link, useNavigate } from 'react-router-dom';
import API from '../../services/Api';

export default function EventDetail({ user }) {
    const { eventId } = useParams();
    const navigate = useNavigate();
    const [event, setEvent] = useState(null);

    useEffect(() => {
        API.get(`/events/${eventId}`)
            .then(res => setEvent(res.data))
            .catch(err => console.error("Error fetching event:", err));
    }, [eventId]);

    if (!event) return <div className="text-center mt-5 py-5"><div className="spinner-border text-primary"></div><p className="mt-2">Loading Event Details...</p></div>;

    // Check karein ki kya ye Recruitment event hai
    const isRecruitment = event.name?.toLowerCase().includes('recruitment');

    return (
        <div className="container mt-4 mb-5">
            {/* Back Button */}
            <button onClick={() => navigate(-1)} className="btn btn-sm btn-outline-secondary mb-3 rounded-pill px-3">
                ← Go Back
            </button>

            <div className="card p-5 shadow-lg border-0 rounded-4 text-center bg-white">
                <h2 className="fw-bold text-primary mb-3 display-6">{event.name}</h2>
                <div className="mx-auto mb-4" style={{maxWidth: '700px'}}>
                    <p className="text-muted lead">{event.description}</p>
                </div>

                {/* DYNAMIC DATABASE FIELDS SECTION */}
                <div className="row justify-content-center mb-5 g-4">
                    {isRecruitment ? (
                        /* RECRUITMENT CASE: Only show Deadline */
                        <div className="col-md-6">
                            <div className="p-4 bg-white border border-danger border-2 rounded-4 shadow-sm">
                                <h6 className="text-uppercase small fw-bold text-danger mb-2">Application Deadline</h6>
                                <p className="mb-0 fw-bold fs-3 text-danger">{event.deadline || 'Closing Soon'}</p>
                            </div>
                        </div>
                    ) : (
                        /* NORMAL EVENT CASE: Show Date, Time, and Venue Address */
                        <>
                            <div className="col-md-3">
                                <div className="p-3 bg-light rounded-4">
                                    <h6 className="text-uppercase small fw-bold text-secondary">Date</h6>
                                    <p className="mb-0 fw-bold">{event.date}</p>
                                </div>
                            </div>
                            <div className="col-md-3">
                                <div className="p-3 bg-light rounded-4">
                                    <h6 className="text-uppercase small fw-bold text-secondary">Time</h6>
                                    <p className="mb-0 fw-bold">{event.time || 'Check Description'}</p>
                                </div>
                            </div>
                            <div className="col-md-4">
                                <div className="p-3 bg-light rounded-4">
                                    <h6 className="text-uppercase small fw-bold text-secondary">Venue Location</h6>
                                    {/* Displaying Address instead of ID */}
                                    <p className="mb-0 fw-bold">{event.venueAddress || 'To be announced'}</p>
                                </div>
                            </div>
                        </>
                    )}
                </div>

                {/* REGISTRATION ACTION */}
                {user ? (
                    <div className="mt-2">
                        <a 
                            href={event.registrationFormLink} 
                            target="_blank" 
                            rel="noreferrer" 
                            className="btn btn-success btn-lg rounded-pill px-5 py-3 shadow-sm fw-bold"
                        >
                            {isRecruitment ? 'Apply Now' : 'Register Now'}
                        </a>
                        <p className="mt-3 text-muted small">Link will open the database registration form.</p>
                    </div>
                ) : (
                    <div className="alert alert-warning mt-3 rounded-4 shadow-sm d-inline-block px-5">
                        Please <Link to="/login" className="fw-bold text-dark">Login</Link> to {isRecruitment ? 'apply' : 'register'}.
                    </div>
                )}
            </div>
        </div>
    );
}