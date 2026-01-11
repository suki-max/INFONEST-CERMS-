import React from 'react';
export default function VenueStatus({ isAvailable }) {
  return <span className={`badge ${isAvailable ? 'bg-success' : 'bg-danger'} mb-2`}>
    {isAvailable ? "Available" : "Occupied"}
  </span>;
}