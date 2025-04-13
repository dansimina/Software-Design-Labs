import { UserDTO } from "../types/UserDTO";

function WelcomeMessage({ name, id, username, type }: UserDTO) {
  return (
    <div className="text-center my-5 p-4 bg-light rounded shadow-sm">
      <h1 className="display-4 fw-bold text-primary mb-3">
        {name ? `Welcome, ${name}!` : "Welcome!"}
      </h1>
      <p className="text-muted fst-italic">
        We're glad to have you here. Let's make today productive!
      </p>
      <div className="d-flex flex-column align-items-center mt-4">
        <div className="mb-3">
          <span className="text-muted me-2">👤 User ID:</span>
          <span className="fw-semibold">{id || "N/A"}</span>
        </div>
        <div className="mb-3">
          <span className="text-muted me-2">📛 Username:</span>
          <span className="fw-semibold">{username || "N/A"}</span>
        </div>
        <div className="mb-3">
          <span className="text-muted me-2">🔖 Type:</span>
          <span className="fw-semibold">{type?.type || "N/A"}</span>
        </div>
      </div>
    </div>
  );
}

export default WelcomeMessage;
