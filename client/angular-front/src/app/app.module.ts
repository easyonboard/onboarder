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
  MatProgressSpinnerModule, MatChipsModule, MatIconModule, MatFormFieldModule, MatProgressBarModule,
  MatDialogModule, MatGridListModule, MatCardModule, MatTooltipModule, MatButtonModule, MatListModule, MatTableModule,
  MatCheckboxModule, MatDatepickerModule, MatSelectModule, MatOptionModule, MatNativeDateModule
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
    ToDoListForBuddyComponent
  ],
  imports: [
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
    MatNativeDateModule
  ],
  entryComponents: [
    DialogCheckListComponent,
    DialogEnrolledCoursesForUserComponent,
    DialogNewEmployeeComponent,
    ToDoListForBuddyComponent,
    UserAddComponent
  ],
  providers: [CourseService, UserService, UtilityService, MaterialService, SubjectService, ReviewService,
    UserInformationService, CommonComponentsService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
