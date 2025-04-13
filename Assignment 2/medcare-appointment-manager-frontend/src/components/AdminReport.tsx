import { useEffect, useState } from "react";
import { DoctorReportDTO } from "../types/DoctorReportDTO";
import { AppointmentDTO } from "../types/AppointmentDTO";
import api from "../api";
import { DateIntervalDTO } from "../types/DateIntervalDTO";
import { MedicalServiceReportDTO } from "../types/MedicalServiceReportDTO";

function AdminReport() {
  const [appointments, setAppointments] = useState<Array<AppointmentDTO>>([]);
  const [doctorReport, setDoctorReport] = useState<Array<DoctorReportDTO>>([]);
  const [serviceReport, setServiceReport] = useState<
    Array<MedicalServiceReportDTO>
  >([]);
  const [dateInterval, setDateInterval] = useState<DateIntervalDTO>({
    start: new Date(
      new Date().setMonth(new Date().getMonth() - 1)
    ).toISOString(),
    end: new Date().toISOString(),
  });
  const [error, setError] = useState<string>("");

  const fetchAppointments = async () => {
    try {
      const response = await api.post<Array<AppointmentDTO>>(
        "/admin/appointments",
        dateInterval
      );
      setAppointments(response.data);
    } catch (error: any) {
      console.error(
        "Error fetching appointments:",
        error?.response?.data || error.message || error
      );
      setError("Failed to fetch appointments.");
    }
  };

  const fetchDoctorReport = async () => {
    try {
      const response = await api.post<Array<DoctorReportDTO>>(
        "/admin/doctors/report",
        dateInterval
      );
      setDoctorReport(response.data);
    } catch (error: any) {
      console.error(
        "Error fetching doctor report:",
        error?.response?.data || error.message || error
      );
      setError("Failed to fetch doctor report.");
    }
  };

  const fetchServiceReport = async () => {
    try {
      const response = await api.post<Array<MedicalServiceReportDTO>>(
        "/admin/services/report",
        dateInterval
      );
      setServiceReport(response.data);
    } catch (error: any) {
      console.error(
        "Error fetching service report:",
        error?.response?.data || error.message || error
      );
      setError("Failed to fetch service report.");
    }
  };

  useEffect(() => {
    fetchAppointments();
    fetchDoctorReport();
    fetchServiceReport();
  }, [dateInterval]);

  return (
    <div className="container mt-4" style={{ fontFamily: "Arial, sans-serif" }}>
      <h1 className="text-center text-success mb-4">Admin Report</h1>

      <div className="row mb-4">
        <div className="col-md-6">
          <label htmlFor="startDate" className="form-label">
            Start Date
          </label>
          <input
            type="date"
            className="form-control"
            id="startDate"
            value={new Date(dateInterval.start).toISOString().split("T")[0]}
            onChange={(e) =>
              setDateInterval((prev) => ({
                ...prev,
                start: new Date(e.target.value).toISOString(),
              }))
            }
          />
        </div>
        <div className="col-md-6">
          <label htmlFor="endDate" className="form-label">
            End Date
          </label>
          <input
            type="date"
            className="form-control"
            id="endDate"
            value={new Date(dateInterval.end).toISOString().split("T")[0]}
            onChange={(e) =>
              setDateInterval((prev) => ({
                ...prev,
                end: new Date(e.target.value).toISOString(),
              }))
            }
          />
        </div>
      </div>

      <div className="mb-4 text-center">
        <button
          className="btn btn-primary w-100"
          onClick={() => {
            console.log("Export button clicked");
          }}
        >
          Export Report
        </button>
      </div>

      {error && <div className="alert alert-danger">{error}</div>}

      <div className="table-responsive mb-4">
        <h2>Appointments</h2>
        <table className="table table-bordered">
          <thead>
            <tr>
              <th>ID</th>
              <th>Patient Name</th>
              <th>Doctor</th>
              <th>Medical Service</th>
              <th>Date</th>
              <th>Time</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {appointments.map((appointment) => (
              <tr key={appointment.id}>
                <td>{appointment.id}</td>
                <td>{appointment.patientName}</td>
                <td>{appointment.doctor.name}</td>
                <td>{appointment.medicalService.name}</td>
                <td>{new Date(appointment.date).toLocaleDateString()}</td>
                <td>{appointment.time}</td>
                <td>{appointment.status}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      <div className="table-responsive mb-4">
        <h2>Doctor Report</h2>
        <table className="table table-bordered">
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>No. of Appointments</th>
            </tr>
          </thead>
          <tbody>
            {doctorReport.map((report) => (
              <tr key={report.id}>
                <td>{report.id}</td>
                <td>{report.name}</td>
                <td>{report.noOfAppointments}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      <div className="table-responsive">
        <h2>Service Report</h2>
        <table className="table table-bordered">
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>No. of Appointments</th>
            </tr>
          </thead>
          <tbody>
            {serviceReport.map((report) => (
              <tr key={report.id}>
                <td>{report.id}</td>
                <td>{report.name}</td>
                <td>{report.noOfAppointments}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default AdminReport;
