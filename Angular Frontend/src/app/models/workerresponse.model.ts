
export interface Worker {
  skills: string;
  appliedJobIds: number[];
  assignedJobIds: number[];
  paymentIds: number[];

  firstName: string;
  lastName: string;
  rating: number;
  location: string;
}