// src/modules/recruitment/RecruitmentHub.js
import React, { useState } from 'react';
import API from '../../services/Api';

export default function RecruitmentHub({ user }) {
  const [file, setFile] = useState(null);

  const uploadResume = async () => {
    const formData = new FormData();
    formData.append("resume", file);
    formData.append("student_id", user.user_id);

    try {
      // SDS Resume Table Integration [cite: 483, 487]
      await API.post('/recruitment/upload', formData);
      alert("Resume uploaded for Shortlisting!");
    } catch (err) { console.error(err); }
  };

  return (
    <div className="container mt-5">
      <h2>Club Recruitment Hub</h2>
      {user.role === 'STUDENT' ? (
        <div className="card p-4">
          <h5>Upload Resume (PDF/DOCX) [cite: 132]</h5>
          <input type="file" onChange={(e) => setFile(e.target.files[0])} className="form-control my-3" />
          <button onClick={uploadResume} className="btn btn-success">Submit for Shortlisting</button>
        </div>
      ) : (
        <div className="card p-4">
          <h5>Ranked Applicants (Club Official View) [cite: 138]</h5>
          {/* Mapping of ranked list from backend sorting algorithm [cite: 65, 137] */}
          <p className="text-muted">Algorithm-based ranking will appear here.</p>
        </div>
      )}
    </div>
  );
}