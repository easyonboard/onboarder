import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from "@angular/router";
import {CourseDetailComponent} from "./courses/course-detail/course-detail.component";
import {CoursesComponent} from "./courses/courses.component";


const routes: Routes=[
  {path:'detailedCourse/:id', component: CourseDetailComponent},
  { path:'courses', component: CoursesComponent},
  {path: '', component:CoursesComponent}
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
