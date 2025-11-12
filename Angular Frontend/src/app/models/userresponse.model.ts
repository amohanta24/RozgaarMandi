export interface User {
  username: string;
  phoneNumber: string;
  email: string;
  role: 'WORKER' | 'EMPLOYER' | 'ADMIN';  
  receivedReviewIds: number[];           
  writtenReviewIds: number[];           
}
