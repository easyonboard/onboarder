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

  public selectedContactPerson: string;
  public dropdownContactPersonsSettings = {};
  public selectedEnrolledPersonsItems = [];
  public dropdownEnrolledPersonsSettings = {};
  public selectedLocation : LocationDTO;
  public selectedRoom : MeetingHall;

  public dropdownLocationSettings = {};
  public usersOptions: UserDTO[];
  public locationOptions: LocationDTO[];
  public meetingHallOptions: MeetingHall[];

  public event: EventDTO;
  public eventErrorMessage: string;
  public keywords: String[];

  public saved: Boolean;
  private currentStep: string;

  public inputKeyword: any;

  public contactPersonUsername: string;
  public enrolledPersonUsername: string[];
  public today: Date;
  separatorKeysCodes = [ENTER, COMMA, SPACE];

  constructor(private location: Location, private eventService: EventService, private userService: UserService, private locationService: LocationService,  public snackBar: MatSnackBar) {
    this.keywords = [];
    this.rootConst = new RootConst();
    this.event = new EventDTO();
    this.event.overview = '';
    this.event.titleEvent = '';
    this.eventErrorMessage='';
    this.saved = false;
    this.enrolledPersonUsername = [];
    this.userService.getAllUsers().subscribe(us => {
      this.usersOptions=us
    });
    this.selectedRoom=new MeetingHall();
    this.selectedLocation=new LocationDTO();


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
    this.locationService.getLocations().subscribe(resp=>{this.locationOptions=resp; console.log(this.locationOptions)});
    this.locationService.getRooms().subscribe(resp=>this.meetingHallOptions=resp);
  }

  addEvent(): void {
    console.log('selected  loc  '+ this.selectedLocation.idLocation);
    console.log('contact oersons   '+ this.selectedContactPerson);
    console.log('selected room   '+ this.selectedRoom.idMeetingHall);
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
    this.eventService.addEvent(this.event, this.selectedContactPerson,  this.selectedEnrolledPersonsItems, this.selectedLocation, this.selectedRoom).subscribe(event => {
      this.event = event;
      this.saved = true;
      this.incStep();
    }, err => {
      alert(err.error.message);
    });
  }


  snackBarMessagePopup(message: string) {
    console.log('tralalalaaaa\n');
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
    console.log('keywords: ' + this.keywords);
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
