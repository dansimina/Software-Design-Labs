import { useState } from "react";
import { UserDTO } from "../types/UserDTO";
import AdminDoctorManagement from "../components/AdminDoctorManagement";
import AdminMedicalServicesManagement from "../components/AdminMedicalServicesManagement";
import AdminReport from "../components/AdminReport";
import AdminReceptionistManagement from "../components/AdminReceptionistManagement";
import { useNavigate } from "react-router-dom";
import WelcomeMessage from "../components/WelcomeMessage";

function AdminWindow() {
  const navigate = useNavigate();
  const userString = localStorage.getItem("user");
  const user: UserDTO | null = userString ? JSON.parse(userString) : null;

  if (!user) {
    return <div>Error: User not found. Please log in again.</div>;
  }

  const [activeSection, setActiveSection] = useState<
    "receptionist" | "doctor" | "service" | "report"
  >("report");

  return (
    <div className="container mt-5">
      <WelcomeMessage {...user} />
      <div className="d-flex justify-content-center mb-4">
        <div className="btn-group" role="group" aria-label="Navigation">
          <button
            type="button"
            className={`btn btn-primary ${
              activeSection === "report" ? "active" : ""
            }`}
            onClick={() => setActiveSection("report")}
          >
            Report
          </button>
          <button
            type="button"
            className={`btn btn-primary ${
              activeSection === "receptionist" ? "active" : ""
            }`}
            onClick={() => setActiveSection("receptionist")}
          >
            Receptionist
          </button>
          <button
            type="button"
            className={`btn btn-primary ${
              activeSection === "doctor" ? "active" : ""
            }`}
            onClick={() => setActiveSection("doctor")}
          >
            Manage Doctors
          </button>
          <button
            type="button"
            className={`btn btn-primary ${
              activeSection === "service" ? "active" : ""
            }`}
            onClick={() => setActiveSection("service")}
          >
            Manage Medical Services
          </button>
        </div>
      </div>
      <div className="card shadow-lg border-0">
        <div className="card-body">
          {activeSection === "report" && <AdminReport />}
          {activeSection === "receptionist" && <AdminReceptionistManagement />}
          {activeSection === "doctor" && <AdminDoctorManagement />}
          {activeSection === "service" && <AdminMedicalServicesManagement />}
        </div>
      </div>
      <button
        className="btn btn-danger position-absolute top-0 end-0 m-3"
        onClick={() => {
          localStorage.removeItem("user");
          navigate("/login");
          window.location.reload();
        }}
      >
        Logout
      </button>
    </div>
  );
}

export default AdminWindow;
