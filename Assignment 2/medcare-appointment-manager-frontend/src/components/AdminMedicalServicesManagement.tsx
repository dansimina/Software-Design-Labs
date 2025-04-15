import { useEffect, useState } from "react";
import { MedicalServiceDTO } from "../types/MedicalServiceDTO";
import api from "../api";

function AdminMedicalServicesManagement() {
  const [services, setServices] = useState<Array<MedicalServiceDTO>>([]);
  const [selectedService, setSelectedService] =
    useState<MedicalServiceDTO | null>(null);
  const [id, setId] = useState<number | null>(null);
  const [name, setName] = useState("");
  const [price, setPrice] = useState<number | null>(null);
  const [duration, setDuration] = useState<number>(30);
  const [error, setError] = useState("");

  const fetchServices = async () => {
    try {
      const response = await api.get<Array<MedicalServiceDTO>>(
        "/admin/services"
      );
      setServices(response.data);
    } catch (error) {
      console.error("Error fetching services:", error);
    }
  };

  useEffect(() => {
    fetchServices();
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const newService: MedicalServiceDTO = {
        id,
        name,
        price: price || 0,
        duration: duration || 0,
      };

      await api.post("/admin/services", newService);

      handleClear();
      setError("");
      fetchServices();
    } catch (error) {
      console.error("Error adding service:", error);
      setError("Failed to add service. Please try again.");
    }
  };

  const handleSelectService = (service: MedicalServiceDTO) => {
    setId(service.id);
    setName(service.name);
    setPrice(service.price);
    setDuration(service.duration);
    setSelectedService(service);
  };

  const handleClear = () => {
    setId(null);
    setName("");
    setPrice(null);
    setDuration(30);
  };

  const handleDelete = async () => {
    try {
      if (selectedService) {
        await api.post("/admin/services/delete", selectedService);
        handleClear();
        fetchServices();
        setError("");
      }
    } catch (error) {
      console.error("Error deleting service:", error);
      setError("Failed to delete service. Please try again.");
    }
  };

  return (
    <div className="container mt-4" style={{ fontFamily: "Arial, sans-serif" }}>
      <h1 className="text-center text-success mb-4">
        Admin Medical Services Management
      </h1>

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
          <label htmlFor="priceInput" className="form-label">
            Price
          </label>
          <input
            type="number"
            className="form-control"
            id="priceInput"
            placeholder="Enter price"
            aria-label="Price"
            value={price || ""}
            onChange={(e) => setPrice(Number(e.target.value))}
          />
        </div>
        <div className="col-md-6">
          <label htmlFor="durationInput" className="form-label">
            Duration (minutes)
          </label>
          <select
            className="form-select"
            id="durationInput"
            aria-label="Duration"
            value={duration || ""}
            onChange={(e) => setDuration(Number(e.target.value))}
          >
            {Array.from({ length: 6 }, (_, i) => i * 30 + 30).map((value) => (
              <option key={value} value={value}>
                {value}
              </option>
            ))}
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
              <th>Price</th>
              <th>Duration (minutes)</th>
            </tr>
          </thead>
          <tbody>
            {services.map((service) => (
              <tr
                key={service.id}
                onClick={() => handleSelectService(service)}
                className={id === service.id ? "table-primary" : ""}
                style={{ cursor: "pointer" }}
              >
                <td>{service.id}</td>
                <td>{service.name}</td>
                <td>{service.price}</td>
                <td>{service.duration}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default AdminMedicalServicesManagement;
