
export interface Worker {
  workerId: number;
  skills: string;
  appliedJobIds: number[];
  assignedJobIds: number[];
  paymentIds: number[];

  firstName: string;
  lastName: string;
  rating: number;
  location: string;
}