import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {StarRatingModule} from 'angular-star-rating';
import {GeneralInfosComponent} from './general-infos/general-infos.component';
import {TutorialsComponent} from './tutorials/tutorials.component';
import {ToDoListForBuddyComponent} from './common/DialogToDoListForBuddy/dialog-to-do-list-for-buddy.component';
import {AddUpdateTutorialComponent} from './tutorials/add-update-tutorial/add-update-tutorial.component';
import {ViewTutorialComponent} from './tutorials/view-tutorial/view-tutorial.component';
import {EventsComponent} from './events/events.component';
import {AddEventComponent} from './events/add-event/add-event.component';
import {NotFoundComponent} from './not-found-component/not-found-component.component';
import {LoggedInGuard} from './guard/logged-in.guard';

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: '', component: LoginComponent},
  {path: 'info', component: GeneralInfosComponent, canActivate: [LoggedInGuard]},
  {path: 'tutorials', component: TutorialsComponent, canActivate: [LoggedInGuard]},
  {path: 'tutorials/draft', component: TutorialsComponent, canActivate: [LoggedInGuard]},
  {path: 'tutorials/addTutorial', component: AddUpdateTutorialComponent, canActivate: [LoggedInGuard]},
  {path: 'tutorials/addTutorial/:id', component: AddUpdateTutorialComponent, canActivate: [LoggedInGuard]},
  {path: 'events/addEvent', component: AddEventComponent, canActivate: [LoggedInGuard]},
  {path: 'tutorials/:id', component: ViewTutorialComponent, canActivate: [LoggedInGuard]},
  {path: 'buddyMenu', component: ToDoListForBuddyComponent, canActivate: [LoggedInGuard]},
  {path: 'events/viewEvents', component: EventsComponent, canActivate: [LoggedInGuard]},
  {path: '404', component: NotFoundComponent},
  {path: '**', redirectTo: '/404'}
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
