import {Component, OnInit} from '@angular/core';
import {EventService} from '../service/event.service';
import {EventDTO} from '../domain/event';

@Component({
  selector: 'app-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.css']
})
export class EventsComponent implements OnInit {
  panelOpenState: boolean = false;
  pastEvents: EventDTO[];
  upcomingEvents: EventDTO[];


  constructor(private eventService: EventService) {
    this.pastEvents = [];
    this.upcomingEvents = [];
  }

  ngOnInit() {
    this.eventService.getPastEvents().subscribe(pastEvents => {
        this.pastEvents = pastEvents;
        this.processDateAndTime(this.pastEvents);
      }
    );
    this.eventService.getUpcomingEvents().subscribe(upcomingEvents => { this.upcomingEvents = upcomingEvents;
    this.processDateAndTime(this.upcomingEvents)});


  }

  private processDateAndTime(events: EventDTO[]) {
    events.forEach(event => {
      const myDate = new Date(event.eventDate).toDateString();
      event.stringDate = myDate;
      event.stringHour = new Date(event.eventDate).getHours() + ':' + new Date(event.eventDate).getMinutes();
    });

  }


}
