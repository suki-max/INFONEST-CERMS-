import React from 'react';
export default function DownloadPdf({ pdfUrl, name }) {
  return <a href={pdfUrl} download className="btn btn-sm btn-danger">View Timetable (PDF)</a>;
}