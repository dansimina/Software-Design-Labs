import { UserTypeDTO } from "./UserTypeDTO";

export interface UserDTO {
    id: number;
    name: string;
    username: string;
    type: UserTypeDTO;
}