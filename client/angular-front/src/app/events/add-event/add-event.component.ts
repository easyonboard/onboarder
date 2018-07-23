import {Component, OnInit, ANALYZE_FOR_ENTRY_COMPONENTS} from '@angular/core';
import {UserDTO} from '../../domain/user';
import {Location, Time} from '@angular/common';
import {UserService} from '../../service/user.service';
import {EventDTO, MeetingHall} from '../../domain/event';
import {EventService} from '../../service/event.service';
import {LocationService} from '../../service/location.service';
import {MatChipInputEvent, MatSnackBar} from '@angular/material';
import {RootConst} from '../../util/RootConst';
import {COMMA, ENTER, SPACE} from '@angular/cdk/keycodes';
import {LocationDTO} from '../../domain/location';
import {debug} from 'util';
import { TimepickerDirective, ITime, TimeFormat } from 'angular5-time-picker';
import { Item } from 'angular2-multiselect-dropdown';
import { DEFAULT_RESIZE_TIME } from '@angular/cdk/scrolling';
import { Timestamp } from 'rxjs';

@Component({
  selector: 'app-add-event',
  templateUrl: './add-event.component.html',
  styleUrls: ['./add-event.component.css']
})
export class AddEventComponent implements OnInit {

  private rootConst: RootConst;

  public selectedContactPerson: string;
  public dropdownContactPersonsSettings = {};
  public selectedEnrolledPersonsItems = [];
  public dropdownEnrolledPersonsSettings = {};
  public selectedLocation: LocationDTO;
  public selectedRoom: MeetingHall;

  public dropdownLocationSettings = {};
  public usersOptions: UserDTO[];

  public allLocationOptions: LocationDTO[] = [];
  public allMeetingHallOptions: MeetingHall[] = [];
  public locationOptions: LocationDTO[] = [];
  public meetingHallOptions: MeetingHall[] = [];

  public event: EventDTO;
  public eventErrorMessage: string;
  public keywords: String[];

  public time: any;

  public saved: Boolean;
  private currentStep: string;

  public inputKeyword: any;

  public contactPersonUsername: string;
  public enrolledPersonUsername: string[];
  public today: Date;
  separatorKeysCodes = [ENTER, COMMA, SPACE];

  constructor(private location: Location, private eventService: EventService, private userService: UserService,
              private locationService: LocationService, public snackBar: MatSnackBar) {
    this.keywords = [];
    this.rootConst = new RootConst();
    this.event = new EventDTO();
    this.event.overview = '';
    this.event.titleEvent = '';
    this.eventErrorMessage = '';
    this.saved = false;
    this.enrolledPersonUsername = [];
    this.userService.getAllUsers().subscribe(us => {
      this.usersOptions = us;
    });
    this.selectedRoom = new MeetingHall();
    this.selectedLocation = new LocationDTO();
  }

  ngOnInit() {
    this.currentStep = 'one';
    this.today = new Date(Date.now());

    this.dropdownContactPersonsSettings = {
      singleSelection: true,
      allowSearchFilter: true
    };

    this.dropdownEnrolledPersonsSettings = {
      singleSelection: false,
      allowSearchFilter: true
    };

    this.dropdownLocationSettings = {
      singleSelection: true,
      allowSearchFilter: true
    };

    this.locationService.getLocations().subscribe(resp => {
      this.allLocationOptions = resp;
      this.locationOptions = this.allLocationOptions;
      console.log(this.locationOptions);
    });

    this.locationService.getRooms().subscribe(resp => {
      this.allMeetingHallOptions = resp;
      this.meetingHallOptions = this.allMeetingHallOptions;
      console.log(this.meetingHallOptions [0]);
    });
  }

  addEvent(time: string): void {
    debug;

    this.event.eventTime = time;

    if (this.event.titleEvent.length < 5) {
      this.eventErrorMessage += 'Title is too short!\n';
    }
    if (this.event.overview.length > 500) {
      this.eventErrorMessage += 'Description must contain at most 500 characters!\n';
    }

    if (this.eventErrorMessage !== '') {
      this.snackBarMessagePopup(this.eventErrorMessage);
      this.eventErrorMessage = '';
      return;
    }

    this.event.keywords = this.keywords.join(' ');
    this.eventService.addEvent(this.event, this.selectedContactPerson, this.selectedEnrolledPersonsItems,
                               this.selectedLocation, this.selectedRoom).subscribe(event => {
      this.event = event;
      this.saved = true;
      this.incStep();
    }, err => {
      alert(err.error.message);
    });
  }

  filterLocations(state: any) {
    console.log('Here filter locations by ' + state + ' which is the hall\'s id');
    this.locationOptions = [];
    if (state === undefined) {
      this.locationOptions = this.allLocationOptions;
    } else {
      const meetingHall = this.meetingHallOptions.find(mh => mh.idMeetingHall === state);
      this.locationOptions.push(meetingHall.location);
    }
  }

  filterHalls(state: any) {
    console.log('Here filter halls ' + state + ' which is the location\'s id');
    this.meetingHallOptions = [];
    if (state === undefined) {
      this.meetingHallOptions = this.allMeetingHallOptions;
    } else {
      this.allMeetingHallOptions.forEach(mh => {
        if (mh.location.idLocation === state) {
          this.meetingHallOptions.push(mh);
        }
      });
    }
  }

  snackBarMessagePopup(message: string) {
    this.snackBar.open(message, null, {
      duration: 3000
    });
  }

  incStep() {
    switch (this.currentStep) {
      case ('one'):
        this.currentStep = 'two';
        this.saved = false;
        break;
    }
  }

  getCurrentStep(): string {
    return this.currentStep;
  }

  removeKeyword(keyword: any): void {
    let index = this.keywords.indexOf(keyword);
    if (index >= 0) {
      this.keywords.splice(index, 1);
      if (this.inputKeyword.hidden === true) {
        this.inputKeyword.hidden = false;
      }
    }
  }

  addKeyword(event: MatChipInputEvent): void {
    this.inputKeyword = event.input;
    let value = event.value;

    if ((value || '').trim() && this.keywords.length < 4) {
      this.keywords.push(value.trim());
      if (this.inputKeyword) {
        this.inputKeyword.value = '';
      }
      if (this.keywords.length === 4) {
        this.inputKeyword.hidden = true;
      }
    }
  }

  redirectToEventPage() {
    location.replace(this.rootConst.FRONT_EVENTS_PAGE);
  }

  getDate(): Date {
    return new Date(this.event.eventDate);
  }

  // getTime(): TimeFormat {
    // let time: Time;
    // time.hours = 10;
    // time.minutes = 10;
    // time.meriden = this.event.eventMeriden === 'AM' ? 'AM' : 'PM';
    // return
  // }

  // parseTime(time: Time) {
  //   console.log(time);
  //   this.event.eventHours = time.hours;
  //   this.event.eventMinutes = time.minutes;
  //   this.event.eventMeriden = time.toString().includes('AM') === true ? 'AM' : 'PM';
  // }
}
