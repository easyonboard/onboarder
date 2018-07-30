import {UserDTO} from './user';
import {LocationDTO} from './location';

export class EventDTO {
  idEvent: number;
  titleEvent: string;
  overview: string;
  keywords: string;
  contactPersons: UserDTO[];
  enrolledUsers: UserDTO[];
  maxEnrolledUsers: number;
  location: LocationDTO;
  stringDate: string;
  eventDate: Date;
  eventTime: string;
  placesLeft: number;
  meetingHall: MeetingHall;
  isUserEnrolled: Boolean;
}

export class MeetingHall {
  idMeetingHall: number;
  hallName: string;
  capacity: number;
  location: LocationDTO;
  floor: number;
}
