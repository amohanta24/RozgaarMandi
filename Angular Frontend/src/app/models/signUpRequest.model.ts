import { EmployerType, Role } from "./Enum.model";

export class signUpRequest{
   
    //common for WORKER AND EMPLOYER
    username!: string;
    password!: string;
    email!: string;
    phoneNumber!: string;
    role!: Role;
    

    employerType?: EmployerType;

    //IF EMPLOYER == BUSINESS
    contactPersonName?: string;
    contactPersonNumber?: string;
    gstNumber?: string;
    businessDescription?: string;

    //IF EMPLOYER==IND
    employerFirstName?: string;
    employerLastName?: string;
    workerFirstName?: string;
    workerLastName?: string;

    //ROLE == WORKER
    skills?: string[];

    businessName?: string;
    location?: string


  }

  