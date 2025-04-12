import { DoctorDTO } from "./DoctorDTO";
import { MedicalServiceDTO } from "./MedicalServiceDTO";

export interface AppointmentDTO {
  id: number | null;
  patientName: string;
  doctor: DoctorDTO;
  medicalService: MedicalServiceDTO;
  date: string;
  time: string;
  status: string;
}