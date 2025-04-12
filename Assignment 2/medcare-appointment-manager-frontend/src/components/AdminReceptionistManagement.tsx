import { useEffect, useState } from "react";
import api from "../api";
import { UserDTO } from "../types/UserDTO";
import { CreateUserDTO } from "../types/CreateUserDTO";

function AdminReceptionistManagement() {
  const [receptionists, setReceptionists] = useState<Array<UserDTO>>([]);
  const [name, setName] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const fetchReceptionists = async () => {
    try {
      const response = await api.get<Array<UserDTO>>("/admin/receptionists");
      setReceptionists(response.data);
    } catch (error) {
      console.error("Error fetching receptionists:", error);
    }
  };

  useEffect(() => {
    fetchReceptionists();
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const newReceptionist: CreateUserDTO = {
        name,
        username,
        password,
        type: "receptionist",
      };

      await api.post("/admin/receptionists", newReceptionist);

      setName("");
      setUsername("");
      setPassword("");
      setError("");

      fetchReceptionists();
    } catch (error) {
      console.error("Error adding receptionist:", error);
      setError("Failed to add receptionist. Please try again.");
    }
  };

  return (
    <div className="container mt-4" style={{ fontFamily: "Arial, sans-serif" }}>
      <h1 className="text-center text-success mb-4">
        Admin Receptionist Management
      </h1>

      <div className="table-responsive mx-auto" style={{ maxWidth: "600px" }}>
        <table className="table table-bordered">
          <thead className="thead-light">
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Username</th>
              <th>Type</th>
            </tr>
          </thead>
          <tbody>
            {receptionists.map((receptionist) => (
              <tr key={receptionist.id}>
                <td>{receptionist.id}</td>
                <td>{receptionist.name}</td>
                <td>{receptionist.username}</td>
                <td>{receptionist.type?.type}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      <form
        className="row g-3"
        style={{ maxWidth: "600px", margin: "0 auto" }}
        onSubmit={handleSubmit}
      >
        <div className="col-md-4">
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
        <div className="col-md-4">
          <label htmlFor="usernameInput" className="form-label">
            Username
          </label>
          <input
            type="text"
            className="form-control"
            id="usernameInput"
            placeholder="Enter username"
            aria-label="Username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
        </div>
        <div className="col-md-4">
          <label htmlFor="passwordInput" className="form-label">
            Password
          </label>
          <input
            type="password"
            className="form-control"
            id="passwordInput"
            placeholder="Enter password"
            aria-label="Password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
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
      </form>
    </div>
  );
}

export default AdminReceptionistManagement;
