import React, { useState, useEffect } from 'react';
import API from '../../services/Api';

export default function TeacherList() {
  const [teachers, setTeachers] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // API Call to fetch teachers
    API.get('/teachers')
      .then(res => {
        console.log("Teacher Data:", res.data); // Debugging ke liye
        setTeachers(res.data);
      })
      .catch(err => console.error("Error fetching teachers:", err))
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <div className="text-center mt-5">Schedules load ho rahe hain...</div>;

  return (
    <div className="container mt-4">
      <h2 className="mb-4 fw-bold">Faculty Schedules</h2>
      <div className="table-responsive shadow-sm rounded">
        <table className="table table-hover bg-white">
          <thead className="table-dark">
            <tr>
              <th>Name</th>
              <th>Email</th>
              <th>Cabin</th>
              <th>Timetable</th>
            </tr>
          </thead>
          <tbody>
            {teachers.length > 0 ? (
              teachers.map((t) => (
                <tr key={t.userId}>
                  {/* TeacherDTO fields check karein */}
                  <td>{t.firstName} {t.lastName}</td>
                  <td>{t.email}</td>
                  <td>{t.sittingCabin}</td>
                  <td>
                    <a 
                      href={`http://localhost:8080${t.schedulePdfPath}`} 
                      target="_blank" 
                      rel="noreferrer"
                      className="btn btn-sm btn-danger"
                    >
                      View PDF
                    </a>
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="4" className="text-center py-4">Koi schedule nahi mila.</td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}