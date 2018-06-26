import {Component, OnInit} from '@angular/core';
import {EventService} from '../service/event.service';
import {EventDTO} from '../domain/event';
import {UserDTO} from '../domain/user';

@Component({
  selector: 'app-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.css']
})
export class EventsComponent implements OnInit {
  panelOpenState: boolean = false;
  pastEvents: EventDTO[];
  upcomingEvents: EventDTO[];
  canEnroll: boolean;
  user: UserDTO;


  constructor(private eventService: EventService) {
    this.pastEvents = [];
    this.upcomingEvents = [];
    this.canEnroll = true;
    this.user=new UserDTO();
    this.user.username = localStorage.getItem('userLogged');
  }

  ngOnInit() {
    this.eventService.getPastEvents().subscribe(pastEvents => {
        this.pastEvents = pastEvents;
        this.processDateAndTime(this.pastEvents);
      }
    );

    this.eventService.getUpcomingEvents().subscribe(upcomingEvents => {
      this.upcomingEvents = upcomingEvents;
      this.processDateAndTime(this.upcomingEvents);
      this.processPlacesLeftToEnroll();
      console.log(upcomingEvents);
    });


  }

  private processDateAndTime(events: EventDTO[]) {
    events.forEach(event => {
      const myDate = new Date(event.eventDate).toDateString();
      event.stringDate = myDate;
      event.stringHour = new Date(event.eventDate).getHours() + ':' + new Date(event.eventDate).getMinutes();
    });

  }


  enrollUser(event: EventDTO) {
    this.eventService.enrollUser(this.user, event).subscribe(resp=>{
      this.upcomingEvents=resp;
      this.processDateAndTime(this.upcomingEvents)
      this.processPlacesLeftToEnroll();
    });

  }

  private processPlacesLeftToEnroll() {

    this.upcomingEvents.forEach(event => {

      let x = event.placesLeft = event.meetingHall.capacity - event.enrolledUsers.length;

      if (x <= 0) {
        x = 0;
        this.canEnroll = false;
      }
      event.placesLeft = x;
    });
  }
}
