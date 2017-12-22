import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { CoursesComponent } from './courses/courses.component';
import {CourseService} from "./course.service";
import {HttpClientModule} from "@angular/common/http";
import { CourseDetailComponent} from "./courses/course-detail/course-detail.component";
import {RouterModule, Routes} from "@angular/router";
import {AppRoutingModule } from './/app-routing.module';
import {UserService} from "./user.service";
import {LoginComponent} from "./login/login.component";
import {Ng2OrderModule} from "ng2-order-pipe/dist/index";
import { UtilityService } from './utility.service';


@NgModule({
  declarations: [
    AppComponent,
    CoursesComponent,
    LoginComponent,
    CourseDetailComponent
  ],
  imports: [
    FormsModule,
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    Ng2OrderModule
  ],
  providers: [CourseService, UserService, UtilityService],
  bootstrap: [AppComponent]
})
export class AppModule { }
