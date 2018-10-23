import {Component, OnInit} from '@angular/core';
import {EventService} from '../service/event.service';
import {Event} from '../domain/event';
import {User} from '../domain/user';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {LocalStorageConst} from '../util/LocalStorageConst';

@Component({
  selector: 'app-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.css']
})
export class EventsComponent implements OnInit {
  panelOpenState = false;
  pastEvents: Event[];
  upcomingEvents: Event[];
  canEnroll: boolean;
  user: User;


  constructor(private eventService: EventService,
              private route: ActivatedRoute,
              private router: Router) {
    this.pastEvents = [];
    this.upcomingEvents = [];
    this.canEnroll = true;
    this.user = new User();
    this.user.username = localStorage.getItem(LocalStorageConst._USER_FIRST_NAME);
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
        this.processPlacesToEnroll();
        this.getStatusEnrollment();
      }, error => {
        this.upcomingEvents = [];
      });
    });
  }

  private processDateAndTime(events: Event[]) {
    events.forEach(event => {
      const myDate = new Date(event.eventDate).toDateString();
      event.stringDate = myDate;
    });

  }


  enrollUser(event: Event) {
    this.eventService.enrollUser(this.user, event).subscribe(resp => {
      this.upcomingEvents = resp;
      this.processDateAndTime(this.upcomingEvents);
      this.processPlacesToEnroll();
      this.getStatusEnrollment();
    });

  }

  unenrollUser(event: Event) {
    this.eventService.unenrollUser(this.user, event).subscribe(resp => {
      this.upcomingEvents = resp;
      this.processDateAndTime(this.upcomingEvents);
      this.processPlacesToEnroll();
      this.getStatusEnrollment();
    });

  }

  private processPlacesToEnroll() {
    this.upcomingEvents.forEach(event => {
      if (event.maxEnrolledUsers === null) {
        if (event.meetingHall != null) {
          event.placesLeft = event.placesLeft = event.meetingHall.capacity - event.enrolledUsers.length;
        }
      } else {
        event.placesLeft = event.maxEnrolledUsers - event.enrolledUsers.length;
        ;
      }
    });
  }

  filterByKeyword(keyword: string) {
    const queryParams: Params = Object.assign({'keyword': keyword}, this.route.snapshot.queryParams);
    queryParams['keyword'] = keyword;
    this.router.navigate(['/events/viewEvents'], {queryParams: queryParams});
  }

  private getStatusEnrollment() {
    this.upcomingEvents.forEach(ev => this.eventService.getStatusEnrollmentForUser(this.user, ev).subscribe(bool => ev.isUserEnrolled = bool));
  }


}
