import {UserDTO} from "./user";
import {Course} from "./course";

export class Review {
  idReview: number;
  user: UserDTO;
  course: Course;
  message: string;
  rating: number;
  pointsFromUsers: number;
}
