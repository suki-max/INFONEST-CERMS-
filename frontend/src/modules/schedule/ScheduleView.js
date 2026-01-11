// Ye file basically main container hai schedule module ka
import React from 'react';
import TeacherList from './TeacherList';

export default function ScheduleView() {
  return (
    <div className="container py-5 text-center">
      <h1 className="display-4 mb-4">Class Schedules</h1>
      <TeacherList />
    </div>
  );
}