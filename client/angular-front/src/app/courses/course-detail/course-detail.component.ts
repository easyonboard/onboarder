import {Component, Inject, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {CourseService} from '../../service/course.service';
import {Course} from '../../domain/course';
import {RootConst} from '../../util/RootConst';
import {DOCUMENT} from '@angular/common';
import {UtilityService} from '../../service/utility.service';
import {MaterialService} from '../../service/material.service';
import {UserDTO} from '../../domain/user';
import {UserService} from '../../service/user.service';
import {IMultiSelectOption} from 'angular-2-dropdown-multiselect';
import {SubjectService} from '../../service/subject.service';
import {CoursesComponent} from '../courses.component';


@Component({
  selector: 'app-course-detail',
  templateUrl: './course-detail.component.html',
  styleUrls: ['./course-detail.component.css']
})

export class CourseDetailComponent implements OnInit {
  public course: Course;
  public rootConst: RootConst;
  private fragment: string;
  private isEnrolled: Boolean;
  public hasFinishedPreviousSubjects: Boolean[];
  public isInEditingMode: Boolean;
  private modalMessage: string;
  private errorMessage: string;
  private courseFound: boolean;
  private prevSectionId: string;
  public overviewEdited: string;
  public titleEdited: string;
  public userEmails: IMultiSelectOption[];
  public ownersIds: number[];
  public contactPersonsIds: number[];
  public editedCourse: Course;
  public progress: number;
  private user: UserDTO;


  constructor(private route: ActivatedRoute, private subjectService: SubjectService, private userService: UserService, private utilityService: UtilityService, private courseService: CourseService, private materialSevice: MaterialService, @Inject(DOCUMENT) private document: any,
              ) {
  }

  getCourse(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.route.fragment.subscribe(fragment => {
      this.fragment = fragment;
    });
    this.courseService.getCourse(id).subscribe(course => {
        this.course = course;
        this.courseFound = true;
        this.titleEdited = course.titleCourse;
        this.overviewEdited = course.overview;
      },
      err => {
        if (err.status == 400) {
          this.courseFound = false;
          this.modalMessage = 'Course not found!';
          this.utilityService.openModal('editSuccessful');
        }
      });
    var courseList = this.courseService.getCourse(id);
    courseList.subscribe(course => {
      this.course = course;
    });
  }

  openModal(id: string) {
    this.utilityService.openModal(id);
  }

  getProgress() {
    this.userService.getProgress(this.course, this.user).subscribe(progress => {
      this.progress = progress;
    });
  }

  isUserEnrollOnThisCourse(): any {
    const idCourse = +this.route.snapshot.paramMap.get('id');
    const username = localStorage.getItem('userLogged');
    this.courseService.isUserEnrollOnThisCourse(idCourse, username).subscribe(value => this.isEnrolled = value);
  }

  enrollUserToCourse(): any {

    const username = localStorage.getItem('userLogged');
    const idCourse = +this.route.snapshot.paramMap.get('id');
    this.courseService.enrollUserToCourse(idCourse, username).subscribe();
    this.isEnrolled = true;

  }

  unenrollUserFromCourse(): any {

    const username = localStorage.getItem('userLogged');
    const idCourse = +this.route.snapshot.paramMap.get('id');
    this.courseService.unenrollUserFromCourse(idCourse, username).subscribe();
    this.isEnrolled = false;

  }

  selectSection(id: string) {
    var allElements = this.document.getElementsByClassName('nav-link js-scroll-trigger');

    for (let pos = 0; pos < allElements.length; pos++) {
      allElements[pos].setAttribute('id', '');

    }
    var selectedElem = this.document.getElementsByName(id)[0];
    selectedElem.setAttribute('id', 'selectedSection');

  }

  editCourseMode() {
    this.isInEditingMode = true;
  }

  closeModal(id: string) {
    this.utilityService.closeModal(id);
  }

  editCourse() {
    this.overviewEdited = this.overviewEdited.trim();
    this.titleEdited = this.titleEdited.trim();
    this.editedCourse.idCourse = this.course.idCourse;
    this.editedCourse.titleCourse = this.titleEdited;
    this.editedCourse.overview = this.overviewEdited;

    this.courseService.editCourse(this.editedCourse, this.contactPersonsIds, this.ownersIds).subscribe(res => {
        this.modalMessage = 'Course edited! ';
        this.openModal('editSuccessful');
        this.exitEditingMode();
        this.getCourse();
        this.isInEditingMode = false;
      },
      err => {
        this.errorMessage = err.error.message;


        this.openModal('errorEditing');
      });
  }

  public exitEditingMode(): void {
    this.isInEditingMode = false;
    this.errorMessage = '';
  }

  deleteContactPerson(contactPerson: UserDTO) {
    this.courseService.deleteContactPerson(contactPerson, this.course).subscribe();
    this.getCourse();
  }

  deleteOwnerPerson(contactPerson: UserDTO) {
    this.courseService.deleteOwnerPerson(contactPerson, this.course).subscribe();
    this.getCourse();
  }

  deleteSubjectFromCourse(subject: number) {
    this.courseService.deleteSubjectFromCourse(subject, this.course).subscribe();
  }

  onHoverSection(sectionId: string) {
    var allElements = this.document.getElementsByClassName('nav-link js-scroll-trigger');

    for (let pos = 0; pos < allElements.length; pos++) {
      allElements[pos].setAttribute('id', '');
    }
    this.document.getElementsByName(sectionId)[0].setAttribute('id', 'selectedSection');
    if (this.prevSectionId != null && this.prevSectionId != sectionId) {
      this.document.getElementsByName(this.prevSectionId)[0].setAttribute('id', '');
      this.prevSectionId = sectionId;
    }
  }

  private getStatusSubjects() {

    this.subjectService.getStatusSubjects(this.course, this.user).subscribe(res => this.hasFinishedPreviousSubjects = res);
  }

  searchByKeyword(keyword:string){
     location.href = this.rootConst.FRONT_COURSES_PAGE_SEARCH_BY_KEYWORD+keyword
  }

  ngOnInit() {
    this.user = new UserDTO();
    this.user.username = localStorage.getItem('userLogged');
    this.errorMessage = '';
    this.isInEditingMode = false;
    this.rootConst = new RootConst();
    this.userEmails = [];
    this.editedCourse = new Course();
    this.course = new Course();
    this.course.idCourse = +this.route.snapshot.paramMap.get('id');
    this.getCourse();
    this.getProgress();
    this.getStatusSubjects();
    var userArrayObjects: Array<UserDTO> = new Array<UserDTO>();
    this.userService.getAllUsers().subscribe(us => {
      userArrayObjects = userArrayObjects.concat(us);
      this.userEmails = [];
      userArrayObjects.forEach(u => this.userEmails.push({id: u.idUser, name: u.name + ', email:  ' + u.email}));
    });
    this.isUserEnrollOnThisCourse();
  }

}



