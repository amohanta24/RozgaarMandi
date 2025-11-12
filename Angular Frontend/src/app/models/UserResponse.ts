import { Role } from "./Enum.model";

export class UserResponse{
    username!: string;
    phoneNumber!: string;
    email!: string;
    role!: Role
    receivedReviewIds?: []
    writtenReviewIds?: []
}
