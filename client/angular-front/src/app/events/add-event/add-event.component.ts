import {Component, OnInit} from '@angular/core';
import {UserDTO} from '../../domain/user';
import {Location} from '@angular/common';
import {UserService} from '../../service/user.service';
import {EventDTO, MeetingHall} from '../../domain/event';
import {EventService} from '../../service/event.service';
import {LocationService} from '../../service/location.service';
import {MatChipInputEvent, MatSnackBar} from '@angular/material';
import {RootConst} from '../../util/RootConst';
import {COMMA, ENTER, SPACE} from '@angular/cdk/keycodes';
import {LocationDTO} from '../../domain/location';

@Component({
  selector: 'app-add-event',
  templateUrl: './add-event.component.html',
  styleUrls: ['./add-event.component.css']
})
export class AddEventComponent implements OnInit {

  private rootConst: RootConst;

  public dropdownSettingsEnrolled = {};
  public dropdownSettingsContact = {};
  public allUsers: String[] = [];
  public selectedContactPerson: string = '';
  public selectedEnrolledPersons: string[] = [];
  public selectedLocation: LocationDTO;
  public selectedRoom: MeetingHall;

  public dropdownLocationSettings = {};
  public usersOptions: UserDTO[];

  public allLocationOptions: LocationDTO[] = [];
  public allMeetingHallOptions: MeetingHall[] = [];
  public locationOptions: LocationDTO[] = [];
  public meetingHallOptions: MeetingHall[] = [];
  public otherLocation: LocationDTO;
  public event: EventDTO;
  public eventErrorMessage: string;
  public keywords: String[];
  public saved: Boolean;
  private currentStep: string;

  public inputKeyword: any;

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
    this.userService.getAllUsers().subscribe(us => {
      this.usersOptions = us;
    });
    this.selectedRoom = new MeetingHall();
    this.selectedLocation = new LocationDTO();
  }

  ngOnInit() {
    this.getUserMsgMails();

    this.currentStep = 'one';
    this.today = new Date(Date.now());

    this.dropdownLocationSettings = {
      singleSelection: true,
      allowSearchFilter: true
    };

    this.locationService.getLocations().subscribe(resp => {
      this.allLocationOptions = resp;
      this.locationOptions = this.allLocationOptions;
      this.otherLocation = new LocationDTO();
      this.otherLocation.idLocation=0;
      this.otherLocation.locationName='Other';
      this.locationOptions.push(this.otherLocation);
      console.log(this.locationOptions);

    }, err => {
      alert(err.error.message);
    });


    this.locationService.getRooms().subscribe(resp => {
      this.allMeetingHallOptions = resp;
      this.meetingHallOptions = this.allMeetingHallOptions;
      console.log(this.meetingHallOptions [0]);
    });

    this.dropdownSettingsEnrolled = {
      singleSelection: false,
      allowSearchFilter: true,
      selectAllText: 'Select All',
      unSelectAllText: 'Unselect All',
      itemsShowLimit: 1,
    };

    this.dropdownSettingsContact = {
      singleSelection: true,
      allowSearchFilter: true,
      selectAllText: 'Select All',
      unSelectAllText: 'Unselect All',
      itemsShowLimit: 1,
    };

  }

  private getUserMsgMails() {
    this.userService.getAllMsgMails().subscribe((users: String[]) => {
      this.allUsers = users;
    });
  }

  addEvent(time: string): void {

    this.event.eventTime = time;
    this.event.keywords = this.keywords.join(' ');
    if (!this.event.titleEvent || this.event.titleEvent.length < 5) {
      this.eventErrorMessage += 'Title is too short!\n';

    }
    if (!this.event.overview || this.event.overview.length > 500) {
      this.eventErrorMessage += 'Description must contain at most 500 characters!\n';
    }
    if (!this.selectedContactPerson) {
      this.eventErrorMessage += 'Please select a contact person!\n';
    }
    if (!this.event.eventDate) {
      this.eventErrorMessage += 'Please select the event date!\n';
    }
    if (!this.event.keywords || this.event.keywords.length < 1) {
      this.eventErrorMessage += 'Please add at least one keyword!\n';
    }
    if (this.selectedLocation.idLocation === this.otherLocation.idLocation) {
      this.selectedLocation = new LocationDTO();
    }
    if (this.eventErrorMessage !== '') {
      this.snackBarMessagePopup(this.eventErrorMessage, 'Close');
      this.eventErrorMessage = '';
      return;
    }


    this.eventService.addEvent(this.event, this.selectedContactPerson, this.selectedEnrolledPersons,
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

  snackBarMessagePopup(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 6000
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
}
