import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { CoursesComponent } from './courses/courses.component';
import {CourseService} from "./course.service";
import {HttpClientModule} from "@angular/common/http";
import { CourseDetailComponent} from "./courses/course-detail/course-detail.component";
import {RouterModule, Routes} from "@angular/router";
import { AppRoutingModule } from './/app-routing.module';


@NgModule({
  declarations: [
    AppComponent,
    CoursesComponent,
    CourseDetailComponent
  ],
  imports: [
    FormsModule,
    BrowserModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [CourseService],
  bootstrap: [AppComponent]
})
export class AppModule { }
