import { useState } from "react";
import WelcomeMessage from "../components/WelcomeMessage";
import { UserDTO } from "../types/UserDTO";
import { useNavigate } from "react-router-dom";
import ReceptionistViewAppointments from "../components/ReceptionistViewAppointments";
import ReceptionistCreateAppoinment from "../components/ReceptionistCreateAppointment";

function ReceptionistWindow() {
  const navigate = useNavigate();
  const userString = localStorage.getItem("user");
  const user: UserDTO | null = userString ? JSON.parse(userString) : null;

  if (!user) {
    return <div>Error: User not found. Please log in again.</div>;
  }

  const [activeSection, setActiveSection] = useState<
    "newAppointment" | "viewAppointments"
  >("viewAppointments");

  return (
    <div className="container mt-5">
      <WelcomeMessage {...user} />
      <div className="d-flex justify-content-center mb-4">
        <div
          className="btn-group btn-group-lg w-100"
          role="group"
          aria-label="Navigation"
        >
          <button
            type="button"
            className={`btn btn-primary ${
              activeSection === "viewAppointments" ? "active" : ""
            }`}
            onClick={() => setActiveSection("viewAppointments")}
          >
            View Appointments
          </button>

          <button
            type="button"
            className={`btn btn-primary ${
              activeSection === "newAppointment" ? "active" : ""
            }`}
            onClick={() => setActiveSection("newAppointment")}
          >
            New Appointment
          </button>
        </div>
      </div>

      <div className="card shadow-lg border-0">
        <div className="card-body">
          {activeSection === "viewAppointments" && (
            <ReceptionistViewAppointments />
          )}
          {activeSection === "newAppointment" && (
            <ReceptionistCreateAppoinment />
          )}
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

export default ReceptionistWindow;
