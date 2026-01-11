import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import API from '../../services/Api';

export default function ClubList({ user }) {
    const [clubs, setClubs] = useState([]);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();
    const isAdmin = user?.role?.toLowerCase() === 'admin';

    useEffect(() => {
        API.get('/clubs')
            .then(res => { setClubs(res.data); setLoading(false); })
            .catch(() => setLoading(false));
    }, []);

    if (loading) return <div className="text-center mt-5 py-5"><div className="spinner-border text-primary"></div></div>;

    return (
        <div className="container mt-5">
            <button onClick={() => navigate(-1)} className="btn btn-outline-secondary btn-sm mb-4 rounded-pill px-3">
                ← Go Back
            </button>

            <div className="d-flex justify-content-between align-items-center mb-4">
                <h2 className="fw-bold">College Clubs</h2>
                {isAdmin && <button className="btn btn-success rounded-pill px-4">+ Add New Club</button>}
            </div>

            <div className="row">
                {clubs.map(club => (
                    <div className="col-md-4 mb-4" key={club.clubId}>
                        <div className="card shadow-sm h-100 p-3 rounded-4 border-0">
                            <h4 className="fw-bold text-dark">{club.name}</h4>
                            <p className="text-muted small">Club ID: {club.clubId}</p>
                            <div className="mt-auto d-grid gap-2">
                                <Link to={`/club/${club.clubId}`} className="btn btn-primary btn-sm rounded-pill">View Details</Link>
                                {isAdmin && (
                                    <button className="btn btn-outline-danger btn-sm rounded-pill mt-1">Remove Club</button>
                                )}
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}