import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./pages/Login";
import AdminWindow from "./pages/AdminWindow";
import ReceptionistWindow from "./pages/ReceptionistWindow";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="login" element={<Login />} />
        <Route path="admin" element={<AdminWindow />} />
        <Route path="receptionist" element={<ReceptionistWindow />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
