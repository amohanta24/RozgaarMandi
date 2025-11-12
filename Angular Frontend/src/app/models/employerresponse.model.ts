import { EmployerType } from "./Enum.model";


export interface Employer {
  postedJobsIds: number[];
  employerType: EmployerType;
  paymentIds: number[];
  contactPersonName: string;
  contactPersonNumber: string;
  address: string;
  gstNumber: string;
  businessDescription: string;
}
