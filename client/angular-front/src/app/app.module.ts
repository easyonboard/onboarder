import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AppComponent} from './app.component';
import {CoursesComponent} from './courses/courses.component';
import {CourseService} from './service/course.service';
import {HttpClientModule} from '@angular/common/http';
import {CourseDetailComponent} from './courses/course-detail/course-detail.component';
import {AppRoutingModule} from './app-routing.module';
import {UserService} from './service/user.service';
import {LoginComponent} from './login/login.component';
import {UtilityService} from './service/utility.service';
import {MaterialService} from './service/material.service';
import {AddCourseComponent} from './courses/add-course/add-course.component';
import {SubjectDetailComponent} from './subject-detail/subject-detail.component';
import {SubjectService} from './service/subject.service';
import {MultiselectDropdownModule} from 'angular-2-dropdown-multiselect';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ScrollEventModule} from 'ngx-scroll-event';
import {StarRatingModule} from 'angular-star-rating';
import {
  MatButtonModule,
  MatCardModule,
  MatCheckboxModule,
  MatChipsModule,
  MatDatepickerModule,
  MatDialogModule,
  MatExpansionModule,
  MatFormFieldModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatMenuModule,
  MatNativeDateModule,
  MatOptionModule,
  MatPaginatorModule,
  MatProgressBarModule,
  MatProgressSpinnerModule,
  MatSelectModule,
  MatSnackBarModule,
  MatTableModule,
  MatToolbarModule,
  MatTooltipModule
} from '@angular/material';
import {ReviewService} from './service/review.service';
import {UserInfoFormularComponent} from './users/user-info-formular/user-info-formular.component';
import {UserInformationService} from './service/user-information.service';
import {GeneralInfosComponent} from './general-infos/general-infos.component';
import {UserAddComponent} from './users/user-add/user-add.component';
import {CommonComponentsService} from './common/common-components.service';
import {ToDoListForBuddyComponent} from './common/DialogToDoListForBuddy/dialog-to-do-list-for-buddy.component';
import {DialogEnrolledCoursesForUserComponent} from './common/DialogEnrolledCoursesForUser/dialog-enrolled-courses-for-user.component';
import {DialogCheckListComponent} from './common/DialogCheckList/dialog-check-list.component';
import {DialogNewEmployeeComponent} from './common/DialogNewEmployee/dialog-new-employee.component';
import {UsersInDepartmentListComponent} from './users/users-in-department-list/users-in-department-list.component';
import {DialogDeleteUsersComponent} from './common/DialogDeleteUsers/dialog-delete-users.component';
import {ExcelService} from './service/excel.service';
import {UserInfoUpdateComponent} from './users/user-info-update/user-info-update.component';
import {DialogEditProfileComponent} from './common/DialogEditProfile/dialog-edit-profile.component';
import {TutorialsComponent} from './tutorials/tutorials.component';
import {DialogLeaveCheckListComponent} from './common/DialogLeaveCheckList/dialog-leave-check-list.component';
import {AddUpdateTutorialComponent} from './tutorials/add-update-tutorial/add-update-tutorial.component';
import {AddEventComponent} from './events/add-event/add-event.component';
import {NgMultiSelectDropDownModule} from 'ng-multiselect-dropdown';
import {TutorialService} from './service/tutorial.service';
import {EventService} from './service/event.service';
import {FlexLayoutModule} from '@angular/flex-layout';
import {ViewTutorialComponent} from './tutorials/view-tutorial/view-tutorial.component';
import {EventsComponent} from './events/events.component';
import {LocationService} from './service/location.service';
import {AngularMultiSelectModule} from 'angular2-multiselect-dropdown';
import {NotFoundComponent} from './not-found-component/not-found-component.component';

@NgModule({
  declarations: [
    AppComponent,
    CoursesComponent,
    LoginComponent,
    CourseDetailComponent,
    AddCourseComponent,
    SubjectDetailComponent,
    UserInfoFormularComponent,
    GeneralInfosComponent,
    UserAddComponent,
    //  dialogs
    DialogCheckListComponent,
    DialogEnrolledCoursesForUserComponent,
    DialogNewEmployeeComponent,
    ToDoListForBuddyComponent,
    DialogDeleteUsersComponent,
    UserInfoFormularComponent,
    UserInfoUpdateComponent,
    UsersInDepartmentListComponent,
    DialogEditProfileComponent,
    TutorialsComponent,
    AddUpdateTutorialComponent,
    AddEventComponent,
    DialogLeaveCheckListComponent,
    ViewTutorialComponent,
    EventsComponent,
    AddEventComponent,
    NotFoundComponent,
  ],
  imports: [
    NgMultiSelectDropDownModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    MultiselectDropdownModule,
    BrowserAnimationsModule,
    ScrollEventModule,
    MatProgressSpinnerModule,
    MatProgressBarModule,
    MatTooltipModule,
    MatChipsModule,
    MatIconModule,
    MatButtonModule,
    MatFormFieldModule,
    MatDialogModule,
    MatGridListModule,
    MatCardModule,
    StarRatingModule,
    MatListModule,
    MatTableModule,
    MatCheckboxModule,
    MatSelectModule,
    MatOptionModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatExpansionModule,
    MatPaginatorModule,
    MatSnackBarModule,
    MatInputModule,
    FlexLayoutModule,
    MatMenuModule,
    MatToolbarModule,
    AngularMultiSelectModule
  ],
  entryComponents: [
    DialogCheckListComponent,
    DialogEnrolledCoursesForUserComponent,
    DialogNewEmployeeComponent,
    DialogLeaveCheckListComponent,
    ToDoListForBuddyComponent,
    UserAddComponent,
    UsersInDepartmentListComponent,
    DialogDeleteUsersComponent,
    UserInfoUpdateComponent,
    UserInfoFormularComponent,
    DialogEditProfileComponent
  ],
  providers: [CourseService, UserService, UtilityService, MaterialService, SubjectService, ReviewService,
    UserInformationService, CommonComponentsService, ExcelService, TutorialService, EventService, LocationService],
  bootstrap: [AppComponent]
})

export class AppModule {
}
