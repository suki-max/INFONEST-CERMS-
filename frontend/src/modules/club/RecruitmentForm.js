import React, { useState } from 'react';
import API from '../../services/Api';

export default function RecruitmentForm({ clubId, user, closeModal }) {
  const [formData, setFormData] = useState({
    collegeId: '',
    branch: '',
    experience: '',
  });
  const [resume, setResume] = useState(null);

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    // File upload ke liye FormData use karna padta hai
    const data = new FormData();
    data.append('studentId', user.id);
    data.append('clubId', clubId);
    data.append('collegeId', formData.collegeId);
    data.append('branch', formData.branch);
    data.append('experience', formData.experience);
    if (resume) data.append('resume', resume);

    try {
      await API.post('/recruitment/apply', data, {
        headers: { 'Content-Type': 'multipart/form-data' }
      });
      alert("Application submitted successfully!");
      closeModal();
    } catch (err) {
      alert("Error submitting application. Backend check karein.");
    }
  };

  return (
    <div className="card p-3">
      <h5 className="mb-3">Apply for Recruitment</h5>
      <form onSubmit={handleSubmit}>
        <div className="mb-2">
          <label className="small">College ID / Roll No</label>
          <input type="text" className="form-control form-control-sm" required 
            onChange={(e) => setFormData({...formData, collegeId: e.target.value})} />
        </div>
        <div className="mb-2">
          <label className="small">Branch</label>
          <input type="text" className="form-control form-control-sm" required 
            onChange={(e) => setFormData({...formData, branch: e.target.value})} />
        </div>
        <div className="mb-2">
          <label className="small">Why do you want to join?</label>
          <textarea className="form-control form-control-sm" rows="3" 
            onChange={(e) => setFormData({...formData, experience: e.target.value})}></textarea>
        </div>
        <div className="mb-3">
          <label className="small">Upload Resume (PDF)</label>
          <input type="file" className="form-control form-control-sm" accept=".pdf"
            onChange={(e) => setResume(e.target.files[0])} />
        </div>
        <div className="d-flex gap-2">
          <button type="submit" className="btn btn-primary btn-sm w-100">Submit Application</button>
          <button type="button" className="btn btn-secondary btn-sm" onClick={closeModal}>Cancel</button>
        </div>
      </form>
    </div>
  );
}