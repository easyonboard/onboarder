import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AppComponent} from './app.component';
import {CoursesComponent} from './courses/courses.component';
import {CourseService} from './service/course.service';
import {HttpClientModule} from '@angular/common/http';
import {CourseDetailComponent} from './courses/course-detail/course-detail.component';
import {AppRoutingModule} from './/app-routing.module';
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

import {DialogEnrolledCoursesForUser} from './app.component';

import {
  MatProgressSpinnerModule, MatChipsModule, MatIconModule, MatFormFieldModule, MatProgressBarModule,
  MatDialogModule, MatGridListModule, MatCardModule, MatTooltipModule

} from '@angular/material';

@NgModule({
  declarations: [
    AppComponent,
    CoursesComponent,
    LoginComponent,
    CourseDetailComponent,
    AddCourseComponent,
    SubjectDetailComponent,
    DialogEnrolledCoursesForUser
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
    MatFormFieldModule,
    MatDialogModule,
    MatGridListModule,
    MatCardModule
  ],
  entryComponents: [
    DialogEnrolledCoursesForUser
  ],
  providers: [CourseService, UserService, UtilityService, MaterialService, SubjectService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
