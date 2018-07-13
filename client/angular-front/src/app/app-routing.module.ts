import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {CourseDetailComponent} from './courses/course-detail/course-detail.component';
import {CoursesComponent} from './courses/courses.component';
import {LoginComponent} from './login/login.component';
import {AddCourseComponent} from './courses/add-course/add-course.component';
import {SubjectDetailComponent} from './subject-detail/subject-detail.component';
import {StarRatingModule} from 'angular-star-rating';
import {GeneralInfosComponent} from './general-infos/general-infos.component';
import {UserInfoFormularComponent} from './users/user-info-formular/user-info-formular.component';
import {TutorialsComponent} from './tutorials/tutorials.component';
import {ToDoListForBuddyComponent} from './common/DialogToDoListForBuddy/dialog-to-do-list-for-buddy.component';
import {AddUpdateTutorialComponent} from './tutorials/add-update-tutorial/add-update-tutorial.component';
import {ViewTutorialComponent} from './tutorials/view-tutorial/view-tutorial.component';
import {EventsComponent} from './events/events.component';
import {AddEventComponent} from './events/add-event/add-event.component';

const routes: Routes = [
  {path: 'courses/detailedCourse/:id', component: CourseDetailComponent},
  {path: 'courses', component: CoursesComponent},
  {path: 'courses/addCourse', component: AddCourseComponent, pathMatch: 'full'},
  {path: 'login', component: LoginComponent},
  {path: '', component: LoginComponent},
  {path: 'info', component: GeneralInfosComponent},
  {path: 'courses/detailedCourse/:id/subject/:idSubject', component: SubjectDetailComponent},
  {path: 'userinfo', component: UserInfoFormularComponent},
  {path: 'tutorials', component: TutorialsComponent},
  {path: 'tutorials/draft', component: TutorialsComponent},
  {path: 'tutorials/keywords/:keyword', component: TutorialsComponent},
  {path: 'tutorials/addTutorialRouterLink', component: AddUpdateTutorialComponent},
  {path: 'tutorials/addTutorialRouterLink/:id', component: AddUpdateTutorialComponent},
  {path: 'events/addEventRouterLink', component: AddEventComponent},
  {path: 'tutorials/:id', component: ViewTutorialComponent},
  {path: 'buddyMenu', component: ToDoListForBuddyComponent},
  {path: 'events/viewEvents', component: EventsComponent}
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot(routes),
    StarRatingModule.forRoot()
  ],
  declarations: [],
  exports: [RouterModule]
})

export class AppRoutingModule {
}
