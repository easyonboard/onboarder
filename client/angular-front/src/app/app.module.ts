import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AppComponent, DialogCheckListUser, DialogEnrolledCoursesForUser, DialogNewEmployees} from './app.component';
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
  MatCheckboxModule, MatDatepickerModule, MatSelectModule, MatOptionModule, MatNativeDateModule} from '@angular/material';
import { ReviewService } from './service/review.service';
import { UserInformationService } from './service/user-information.service';
import { GeneralInfosComponent } from './general-infos/general-infos.component';
import { UserAddComponent } from './users/user-add/user-add.component';
import { UserInfoFormularComponent } from './users/reusables/user-info-formular/user-info-formular.component';
import { UserInfoUpdateComponent } from './users/user-info-update/user-info-update.component';
import {ToDoListForBuddyComponent} from './common/ToDoListForBuddy/toDoListForBuddy.component';
import {CommonComponentsService} from './common/common-components.service';

@NgModule({
  declarations: [
    AppComponent,
    CoursesComponent,
    LoginComponent,
    CourseDetailComponent,
    AddCourseComponent,
    SubjectDetailComponent,
    DialogEnrolledCoursesForUser,
    GeneralInfosComponent,
    DialogNewEmployees,
    DialogCheckListUser,
    UserAddComponent,
    UserInfoFormularComponent,
    UserInfoUpdateComponent,
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
    DialogEnrolledCoursesForUser,
    DialogNewEmployees,
    DialogCheckListUser,
    UserAddComponent,
    UserInfoUpdateComponent,
    ToDoListForBuddyComponent
  ],
  providers: [CourseService, UserService, UtilityService, MaterialService, SubjectService, ReviewService,
    UserInformationService, CommonComponentsService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
