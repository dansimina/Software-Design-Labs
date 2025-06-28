import { useEffect, useState } from "react";
import { AppointmentDTO } from "../types/AppointmentDTO";
import api from "../api";
import { DoctorDTO } from "../types/DoctorDTO";
import { MedicalServiceDTO } from "../types/MedicalServiceDTO";

function ReceptionistViewAppointments() {
  const [appointments, setAppointments] = useState<Array<AppointmentDTO>>([]);
  const [filteredAppointments, setFilteredAppointments] = useState<
    Array<AppointmentDTO>
  >([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [id, setId] = useState<number | null>(null);
  const [patientName, setPatientName] = useState("");
  const [doctor, setDoctor] = useState<DoctorDTO | null>(null);
  const [medicalService, setMedicalService] =
    useState<MedicalServiceDTO | null>(null);
  const [date, setDate] = useState("");
  const [time, setTime] = useState("");
  const [status, setStatus] = useState("");
  const [error, setError] = useState("");

  const fetchAppointments = async () => {
    try {
      const response = await api.get<Array<AppointmentDTO>>(
        "/receptionist/appointments"
      );
      setAppointments(response.data);
      setFilteredAppointments(response.data);
    } catch (error) {
      console.error("Error fetching appointments:", error);
    }
  };

  useEffect(() => {
    fetchAppointments();
  }, []);

  useEffect(() => {
    const filtered = appointments.filter((appointment) =>
      appointment.patientName.toLowerCase().includes(searchQuery.toLowerCase())
    );
    setFilteredAppointments(filtered);
  }, [searchQuery, appointments]);

  const handleClick = (appointment: AppointmentDTO) => {
    setId(appointment.id);
    setPatientName(appointment.patientName);
    setDoctor(appointment.doctor);
    setMedicalService(appointment.medicalService);
    setDate(new Date(appointment.date).toISOString().split("T")[0]);
    setTime(appointment.time);
    setStatus(appointment.status);
  };

  const handleEdit = async () => {
    if (!id || !status || !doctor || !medicalService) {
      setError("Please select an appointment and a status.");
      return;
    }

    try {
      const dateObj = new Date(date);
      const formattedDate = dateObj.toISOString().split("T")[0];

      const updatedAppointment: AppointmentDTO = {
        id,
        patientName,
        doctor,
        medicalService,
        date: formattedDate,
        time,
        status,
      };

      await api.post("/receptionist/appointments", updatedAppointment);
      setError("");
      fetchAppointments();
    } catch (error) {
      console.error("Error updating appointment:", error);
      setError("Failed to update appointment. Please try again.");
    }
  };

  function handleClear(): void {
    setStatus("");
    setId(null);
    setPatientName("");
    setDoctor(null);
    setMedicalService(null);
    setDate("");
    setTime("");
    setStatus("");
    setError("");
  }

  return (
    <div className="container mt-4" style={{ fontFamily: "Arial, sans-serif" }}>
      <h1 className="text-center text-success mb-4">Appointments</h1>
      <div className="mb-4">
        <h4>Edit Appointment Status</h4>
        <div className="form-group">
          <select
            id="status"
            className="form-control"
            value={status}
            onChange={(e) => setStatus(e.target.value)}
          >
            <option value="">Select Status</option>
            <option value="NEW">NEW</option>
            <option value="ONGOING">ONGOING</option>
            <option value="COMPLETED">COMPLETED</option>
          </select>
        </div>
        <div className="d-flex justify-content-between mt-2">
          <button
            className="btn btn-primary"
            style={{ width: "48%" }}
            onClick={handleEdit}
          >
            Edit
          </button>
          <button
            className="btn btn-secondary"
            style={{ width: "48%" }}
            onClick={handleClear}
          >
            Clear
          </button>
        </div>
        {error && <p className="text-danger mt-2">{error}</p>}
      </div>

      <div className="mb-4">
        <input
          type="text"
          className="form-control"
          placeholder="Search by patient name"
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.target.value)}
        />
      </div>

      <div className="table-responsive mb-4">
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
            {filteredAppointments.map((appointment) => (
              <tr
                key={appointment.id}
                onClick={() => handleClick(appointment)}
                className={id === appointment.id ? "table-primary" : ""}
                style={{ cursor: "pointer" }}
              >
                <td>{appointment.id}</td>
                <td>{appointment.patientName}</td>
                <td>{appointment.doctor.name}</td>
                <td>{appointment.medicalService.name}</td>
                <td>
                  {new Date(appointment.date).toISOString().split("T")[0]}
                </td>
                <td>{appointment.time}</td>
                <td>{appointment.status}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default ReceptionistViewAppointments;
