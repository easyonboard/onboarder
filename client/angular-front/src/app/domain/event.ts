import {User} from './user';
import {Location} from './location';

export class Event {
  idEvent: number;
  titleEvent: string;
  overview: string;
  keywords: string;
  contactPerson: User;
  enrolledUsers: User[];
  maxEnrolledUsers: number;
  location: Location;
  stringDate: string;
  eventDate: Date;
  eventTime: string;
  placesLeft: number;
  meetingHall: MeetingHall;
  isUserEnrolled: Boolean;
  otherLocation: string;
}

export class MeetingHall {
  idMeetingHall: number;
  hallName: string;
  capacity: number;
  location: Location;
  floor: number;
}
