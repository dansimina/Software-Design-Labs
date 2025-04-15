import { useEffect, useState } from "react";
import { DoctorDTO } from "../types/DoctorDTO";
import api from "../api";
import React from "react";

export function AdminDoctorManagement() {
  const [doctors, setDoctors] = useState<Array<DoctorDTO>>([]);
  const [selectedDoctor, setSelectedDoctor] = useState<DoctorDTO | null>(null);
  const [id, setId] = useState<number | null>(null);
  const [name, setName] = useState("");
  const [specialization, setSpecialization] = useState("");
  const [startOfProgram, setStartOfProgram] = useState("07:00");
  const [endOfProgram, setEndOfProgram] = useState("07:30");
  const [error, setError] = useState("");

  const fetchDoctors = async () => {
    try {
      const response = await api.get<Array<DoctorDTO>>("/admin/doctors");
      setDoctors(response.data);
    } catch (error) {
      console.error("Error fetching doctors:", error);
    }
  };

  useEffect(() => {
    fetchDoctors();
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!startOfProgram || !endOfProgram) {
      setError("Start and End of Program cannot be empty.");
      return;
    }

    try {
      const newDoctor: DoctorDTO = {
        id,
        name,
        specialization,
        startOfProgram,
        endOfProgram,
      };

      await api.post("/admin/doctors", newDoctor);

      handleClear();
      setError("");

      fetchDoctors();
    } catch (error) {
      console.error("Error adding doctor:", error);
      setError("Failed to add doctor. Please try again.");
    }
  };

  const handleSelectDoctor = (doctor: DoctorDTO) => {
    setId(doctor.id);
    setName(doctor.name);
    setSpecialization(doctor.specialization);
    setStartOfProgram(doctor.startOfProgram.substring(0, 5));
    setEndOfProgram(doctor.endOfProgram.substring(0, 5));
    setSelectedDoctor(doctor);
  };

  const handleClear = () => {
    setId(null);
    setName("");
    setSpecialization("");
    setStartOfProgram("07:00");
    setEndOfProgram("07:30");
    setError("");
    setSelectedDoctor(null);
  };

  const handleDelete = async () => {
    try {
      if (selectedDoctor) {
        await api.post("/admin/doctors/delete", selectedDoctor);
        handleClear();
        fetchDoctors();
      } else {
        setError("Please select a doctor to delete.");
      }
    } catch (error) {
      console.error("Error deleting doctor:", error);
      setError("Failed to delete doctor. Please try again.");
    }
  };

  return (
    <div className="container mt-4" style={{ fontFamily: "Arial, sans-serif" }}>
      <h1 className="text-center text-success mb-4">Admin Doctor Management</h1>

      <form
        className="row g-3 mt-4"
        style={{ maxWidth: "600px", margin: "0 auto" }}
        onSubmit={handleSubmit}
      >
        <div className="col-md-6">
          <label htmlFor="nameInput" className="form-label">
            Name
          </label>
          <input
            type="text"
            className="form-control"
            id="nameInput"
            placeholder="Enter name"
            aria-label="Name"
            value={name}
            onChange={(e) => setName(e.target.value)}
          />
        </div>
        <div className="col-md-6">
          <label htmlFor="specializationInput" className="form-label">
            Specialization
          </label>
          <input
            type="text"
            className="form-control"
            id="specializationInput"
            placeholder="Enter specialization"
            aria-label="Specialization"
            value={specialization}
            onChange={(e) => setSpecialization(e.target.value)}
          />
        </div>
        <div className="col-md-6">
          <label htmlFor="startOfProgramInput" className="form-label">
            Start of Program
          </label>
          <select
            className="form-select"
            id="startOfProgramInput"
            aria-label="Start of Program"
            value={startOfProgram}
            onChange={(e) => setStartOfProgram(e.target.value)}
            required
          >
            {Array.from({ length: 29 }, (_, i) => {
              const hour = 7 + Math.floor(i / 2);
              const minutes = i % 2 === 0 ? "00" : "30";
              const time = `${hour.toString().padStart(2, "0")}:${minutes}`;
              return (
                <option key={time} value={time}>
                  {time}
                </option>
              );
            })}
          </select>
        </div>
        <div className="col-md-6">
          <label htmlFor="endOfProgramInput" className="form-label">
            End of Program
          </label>
          <select
            className="form-select"
            id="endOfProgramInput"
            aria-label="End of Program"
            value={endOfProgram}
            onChange={(e) => setEndOfProgram(e.target.value)}
            required
          >
            {Array.from({ length: 29 }, (_, i) => {
              const hour = 7 + Math.floor(i / 2);
              const minutes = i % 2 === 0 ? "00" : "30";
              const time = `${hour.toString().padStart(2, "0")}:${minutes}`;
              return (
                <option key={time} value={time}>
                  {time}
                </option>
              );
            })}
          </select>
        </div>
        {error && (
          <div className="col-12">
            <div className="alert alert-danger" role="alert">
              {error}
            </div>
          </div>
        )}
        <div className="col-12">
          <button type="submit" className="btn btn-primary w-100">
            Submit
          </button>
        </div>
        <div className="col-12">
          <button
            type="button"
            className="btn btn-primary w-100"
            onClick={handleClear}
          >
            Clear
          </button>
        </div>
        <div className="col-12">
          <button
            type="button"
            className="btn btn-primary w-100"
            onClick={handleDelete}
          >
            Delete
          </button>
        </div>
      </form>

      <div
        className="table-responsive mx-auto mt-5"
        style={{ maxWidth: "600px" }}
      >
        <table className="table table-bordered">
          <thead className="thead-light">
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Specialization</th>
              <th>Start of Program</th>
              <th>End of Program</th>
            </tr>
          </thead>
          <tbody>
            {doctors.map((doctor) => (
              <tr
                key={doctor.id}
                onClick={() => handleSelectDoctor(doctor)}
                className={
                  selectedDoctor?.id === doctor.id ? "table-primary" : ""
                }
                style={{ cursor: "pointer" }}
              >
                <td>{doctor.id}</td>
                <td>{doctor.name}</td>
                <td>{doctor.specialization}</td>
                <td>{doctor.startOfProgram}</td>
                <td>{doctor.endOfProgram}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default AdminDoctorManagement;
