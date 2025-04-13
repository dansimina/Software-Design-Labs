import { useEffect, useState } from "react";
import { DoctorDTO } from "../types/DoctorDTO";
import Table from "./Table";
import { MedicalServiceDTO } from "../types/MedicalServiceDTO";
import api from "../api";
import { AppointmentDTO } from "../types/AppointmentDTO";

function ReceptionistCreateAppointment() {
  const [doctors, setDoctors] = useState<Array<DoctorDTO>>([]);
  const [services, setServices] = useState<Array<MedicalServiceDTO>>([]);
  const [selectedDoctor, setSelectedDoctor] = useState<DoctorDTO | null>(null);
  const [selectedService, setSelectedService] =
    useState<MedicalServiceDTO | null>(null);
  const [appointmentDate, setAppointmentDate] = useState<string>("");
  const [appointmentTime, setAppointmentTime] = useState<string>("");
  const [patientName, setPatientName] = useState<string>("");
  const [error, setError] = useState("");
  const [availableTimes, setAvailableTimes] = useState<string[]>([]);

  useEffect(() => {
    fetchDoctors();
    fetchServices();
  }, []);

  useEffect(() => {
    if (selectedDoctor) {
      generateTimeSlots();
    }
  }, [selectedDoctor, appointmentDate]);

  const fetchDoctors = async () => {
    try {
      const response = await api.get<Array<DoctorDTO>>("/receptionist/doctors");
      setDoctors(response.data);
    } catch (error) {
      console.error("Error fetching doctors:", error);
      setError("Failed to fetch doctors. Please try again.");
    }
  };

  const fetchServices = async () => {
    try {
      const response = await api.get<Array<MedicalServiceDTO>>(
        "/receptionist/services"
      );
      setServices(response.data);
    } catch (error) {
      console.error("Error fetching services:", error);
      setError("Failed to fetch services. Please try again.");
    }
  };

  const generateTimeSlots = () => {
    if (!selectedDoctor) return;

    try {
      const times: string[] = [];

      // Extract hours and minutes from the doctor's schedule
      const startParts = selectedDoctor.startOfProgram.split(":");
      const endParts = selectedDoctor.endOfProgram.split(":");

      if (startParts.length < 2 || endParts.length < 2) {
        console.error("Invalid time format in doctor schedule");
        return;
      }

      // Create Date objects for start and end times (use current date as base)
      const baseDate = new Date();
      baseDate.setHours(0, 0, 0, 0); // Reset to midnight

      const startTime = new Date(baseDate);
      startTime.setHours(parseInt(startParts[0]), parseInt(startParts[1]));

      const endTime = new Date(baseDate);
      endTime.setHours(parseInt(endParts[0]), parseInt(endParts[1]));

      // Generate time slots in 30-minute intervals
      let currentTime = new Date(startTime);

      while (currentTime < endTime) {
        const hours = currentTime.getHours().toString().padStart(2, "0");
        const minutes = currentTime.getMinutes().toString().padStart(2, "0");
        times.push(`${hours}:${minutes}`);

        // Add 30 minutes
        currentTime.setMinutes(currentTime.getMinutes() + 30);
      }

      setAvailableTimes(times);
      setAppointmentTime("");
    } catch (error) {
      console.error("Error generating time slots:", error);
      setError("Error generating available time slots");
    }
  };

  const handleDoctorSelect = (doctor: DoctorDTO) => {
    setSelectedDoctor(doctor);
  };

  const handleServiceSelect = (service: MedicalServiceDTO) => {
    setSelectedService(service);
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (
      !selectedDoctor ||
      !selectedService ||
      !appointmentDate ||
      !appointmentTime ||
      !patientName
    ) {
      setError("Please fill in all required fields");
      return;
    }

    try {
      const newAppointment: AppointmentDTO = {
        id: null,
        patientName,
        doctor: selectedDoctor,
        medicalService: selectedService,
        date: appointmentDate,
        time: appointmentTime,
        status: "NEW",
      };

      console.log("Creating appointment:", newAppointment);

      await api.post("/receptionist/appointments", newAppointment);

      setPatientName("");
      setAppointmentDate("");
      setAppointmentTime("");
      setSelectedDoctor(null);
      setSelectedService(null);
      setError("");

      alert("Appointment created successfully!");
    } catch (error) {
      console.error("Error creating appointment:", error);
      setError("Failed to create appointment. Please try again.");
    }
  };

  const today = new Date().toISOString().split("T")[0];

  return (
    <div className="container mt-4" style={{ fontFamily: "Arial, sans-serif" }}>
      <h1 className="text-center text-success mb-4">Create Appointment</h1>

      {error && <div className="alert alert-danger">{error}</div>}

      <h4>Appointment Details</h4>
      <form
        className="row g-3"
        style={{ maxWidth: "600px", margin: "0 auto" }}
        onSubmit={handleSubmit}
      >
        <div className="col-12">
          <label htmlFor="patientName" className="form-label">
            Patient Name
          </label>
          <input
            type="text"
            className="form-control"
            id="patientName"
            placeholder="Enter patient name"
            value={patientName}
            onChange={(e) => setPatientName(e.target.value)}
            required
          />
        </div>

        <div className="col-md-6">
          <label htmlFor="appointmentDate" className="form-label">
            Date
          </label>
          <input
            type="date"
            className="form-control"
            id="appointmentDate"
            min={today}
            value={appointmentDate || ""}
            onChange={(e) => setAppointmentDate(e.target.value)}
            required
          />
        </div>

        <div className="col-md-6">
          <label htmlFor="appointmentTime" className="form-label">
            Time
          </label>
          <select
            className="form-select"
            id="appointmentTime"
            value={appointmentTime}
            onChange={(e) => setAppointmentTime(e.target.value)}
            disabled={!selectedDoctor}
            required
          >
            <option value="">Select time</option>
            {availableTimes.map((time) => (
              <option key={time} value={time}>
                {time}
              </option>
            ))}
          </select>
          {!selectedDoctor && (
            <small className="text-muted">Please select a doctor first</small>
          )}
        </div>

        <div className="col-12 mt-4">
          <h5>Selected Doctor</h5>
          {selectedDoctor ? (
            <div className="p-3 border rounded bg-light">
              <p className="mb-0">
                <strong>{selectedDoctor.name}</strong> -{" "}
                {selectedDoctor.specialization}
              </p>
              <small>
                Hours: {selectedDoctor.startOfProgram} -{" "}
                {selectedDoctor.endOfProgram}
              </small>
            </div>
          ) : (
            <p className="text-muted">No doctor selected</p>
          )}
        </div>

        <div className="col-12">
          <h5>Selected Service</h5>
          {selectedService ? (
            <div className="p-3 border rounded bg-light">
              <p className="mb-0">
                <strong>{selectedService.name}</strong> - $
                {selectedService.price}
              </p>
              <small>Duration: {selectedService.duration} minutes</small>
            </div>
          ) : (
            <p className="text-muted">No service selected</p>
          )}
        </div>

        <div className="col-12 mt-3">
          <button type="submit" className="btn btn-primary w-100">
            Create Appointment
          </button>
        </div>
      </form>

      <div className="mt-5">
        <h4>Select a Doctor</h4>
        <Table
          data={doctors}
          columns={[
            { header: "ID", accessor: "id" },
            { header: "Name", accessor: "name" },
            { header: "Specialization", accessor: "specialization" },
            { header: "Start Time", accessor: "startOfProgram" },
            { header: "End Time", accessor: "endOfProgram" },
          ]}
          onRowClick={handleDoctorSelect}
        />
      </div>

      <div className="mt-5 mb-5">
        <h4>Select a Service</h4>
        <Table
          data={services}
          columns={[
            { header: "ID", accessor: "id" },
            { header: "Name", accessor: "name" },
            { header: "Price", accessor: "price" },
            { header: "Duration (minutes)", accessor: "duration" },
          ]}
          onRowClick={handleServiceSelect}
        />
      </div>
    </div>
  );
}

export default ReceptionistCreateAppointment;
