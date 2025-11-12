export interface Payment {
  jobId: number;
  employerId: number;
  workerId: number;
  amount: number;
  paymentDate: any;
  transactionId: string;
  methodOfPayment: 'ONLINE'|'CASH'
  status: any;
}