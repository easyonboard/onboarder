import {UserDTO} from './user';
import {LocationDTO} from './location';


export  class EventDTO {
  idEvent: number;
  titleEvent: string;
  overview: string;
  keywords: string;
  contactPersons: UserDTO[];
  enrolledPersons: UserDTO[];
  maxEnrolledUsers: number;
  location: LocationDTO;
  stringDate: string;
  stringHour: string;
  eventDate: Date;

}
