export interface Review {
  revieweeId: number;
  reviewerId: number;
  jobId: number;
  rating: number;
  comment: string;
  created_on: string;
}