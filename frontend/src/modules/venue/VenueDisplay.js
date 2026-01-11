import React, { useState, useEffect } from 'react';
import { fetchVenues } from '../../services/Api';

export default function VenueDisplay() {
  const [venues, setVenues] = useState([]);

  useEffect(() => {
    fetchVenues().then(res => setVenues(res.data));
  }, []);

  return (
    <div className="container mt-4">
      <div className="row">
        {venues.map(v => (
          <div className="col-md-4 mb-3" key={v.venueId}>
            <div className="card p-3 border-info">
              <h5>{v.name}</h5>
              <p className="small mb-1">📍 {v.address}</p>
              <p className="mb-0">Capacity: {v.capacity}</p>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}