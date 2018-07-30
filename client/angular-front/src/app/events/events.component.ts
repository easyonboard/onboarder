import {Component, OnInit} from '@angular/core';
import {EventService} from '../service/event.service';
import {EventDTO} from '../domain/event';
import {UserDTO} from '../domain/user';
import {ActivatedRoute, Params, Router} from '@angular/router';

@Component({
  selector: 'app-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.css']
})
export class EventsComponent implements OnInit {
  panelOpenState = false;
  pastEvents: EventDTO[];
  upcomingEvents: EventDTO[];
  canEnroll: boolean;
  user: UserDTO;


  constructor(private eventService: EventService,
              private route: ActivatedRoute,
              private router: Router) {
    this.pastEvents = [];
    this.upcomingEvents = [];
    this.canEnroll = true;
    this.user = new UserDTO();
    this.user.username = localStorage.getItem('userLogged');
  }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      const keyword = params['keyword'];
      this.eventService.getPastEvents(keyword).subscribe(pastEvents => {
          this.pastEvents = pastEvents;
          this.processDateAndTime(this.pastEvents);
        }, error => {
          this.pastEvents = [];
        }
      );

      this.eventService.getUpcomingEvents(keyword).subscribe(upcomingEvents => {
        this.upcomingEvents = upcomingEvents;
        this.processDateAndTime(this.upcomingEvents);
        this.processPlacesLeftToEnroll();
      }, error => {
        this.upcomingEvents = [];
      });
    });
  }

  private processDateAndTime(events: EventDTO[]) {
    events.forEach(event => {
      const myDate = new Date(event.eventDate).toDateString();
      event.stringDate = myDate;
    });

  }


  enrollUser(event: EventDTO) {
    this.eventService.enrollUser(this.user, event).subscribe(resp => {
      this.upcomingEvents = resp;
      this.processDateAndTime(this.upcomingEvents);
      this.processPlacesLeftToEnroll();
    });

  }

  private processPlacesLeftToEnroll() {
    this.upcomingEvents.forEach(event => {
      if (event.maxEnrolledUsers !== undefined) {
        let x = event.placesLeft = event.meetingHall.capacity - event.enrolledUsers.length;

        if (x <= 0) {
          x = 0;
          this.canEnroll = false;
        }
        event.placesLeft = x;
      }
    });
  }

  filterByKeyword(keyword: string) {
    const queryParams: Params = Object.assign({'keyword': keyword}, this.route.snapshot.queryParams);
    queryParams['keyword'] = keyword;
    this.router.navigate(['/events/viewEvents'], {queryParams: queryParams});
  }
}
