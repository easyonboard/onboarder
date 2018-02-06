import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {CourseDetailComponent} from "./courses/course-detail/course-detail.component";
import {CoursesComponent} from "./courses/courses.component";
import {LoginComponent} from "./login/login.component";
import {AddCourseComponent} from "./courses/add-course/add-course.component";
import {SubjectDetailComponent} from "./subject-detail/subject-detail.component";


const routes: Routes= [
  {path:'courses/detailedCourse/:id', component: CourseDetailComponent},
  {path:'courses', component: CoursesComponent},
  {path:'courses/addCourse',  component:AddCourseComponent, pathMatch:'full'},
  {path: 'login', component:LoginComponent},
  {path: '', component:LoginComponent},
  {path: 'courses/detailedCourse/:id/subject/:idSubject', component: SubjectDetailComponent}
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot(routes)
  ],
  declarations: [],
  exports: [RouterModule]
})

export class AppRoutingModule { }