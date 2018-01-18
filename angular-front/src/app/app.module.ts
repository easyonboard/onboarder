import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AppComponent} from './app.component';
import {CoursesComponent} from './courses/courses.component';
import {CourseService} from "./course.service";
import {HttpClientModule} from "@angular/common/http";
import {CourseDetailComponent} from "./courses/course-detail/course-detail.component";
import {AppRoutingModule} from './/app-routing.module';
import {UserService} from "./user.service";
import {LoginComponent} from "./login/login.component";
import {Ng2OrderModule} from "ng2-order-pipe/dist/index";
import {UtilityService} from './utility.service';
import {MaterialService} from "./service/material.service";
import {AddCourseComponent} from "./courses/add-course/add-course.component";
import {SubjectDetailComponent} from './subject-detail/subject-detail.component';

@NgModule({
  declarations: [
    AppComponent,
    CoursesComponent,
    LoginComponent,
    CourseDetailComponent,
    AddCourseComponent,
    SubjectDetailComponent
  ],
  imports: [
    FormsModule,
    ReactiveFormsModule,
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
  ],
  providers: [CourseService, UserService, UtilityService, MaterialService],
  bootstrap: [AppComponent]
})
export class AppModule { }
