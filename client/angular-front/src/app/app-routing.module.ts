import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {StarRatingModule} from 'angular-star-rating';
import {GeneralInfosComponent} from './general-infos/general-infos.component';
import {UserInfoFormularComponent} from './users/user-info-formular/user-info-formular.component';
import {TutorialsComponent} from './tutorials/tutorials.component';
import {ToDoListForBuddyComponent} from './common/DialogToDoListForBuddy/dialog-to-do-list-for-buddy.component';
import {AddUpdateTutorialComponent} from './tutorials/add-update-tutorial/add-update-tutorial.component';
import {ViewTutorialComponent} from './tutorials/view-tutorial/view-tutorial.component';
import {EventsComponent} from './events/events.component';
import {AddEventComponent} from './events/add-event/add-event.component';
import {NotFoundComponent} from './not-found-component/not-found-component.component';

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: '', component: LoginComponent},
  {path: 'info', component: GeneralInfosComponent},
  {path: 'userinfo', component: UserInfoFormularComponent},
  {path: 'tutorials', component: TutorialsComponent},
  {path: 'tutorials/draft', component: TutorialsComponent},
  {path: 'tutorials/addTutorialRouterLink', component: AddUpdateTutorialComponent},
  {path: 'tutorials/addTutorialRouterLink/:id', component: AddUpdateTutorialComponent},
  {path: 'events/addEventRouterLink', component: AddEventComponent},
  {path: 'tutorials/:id', component: ViewTutorialComponent},
  {path: 'buddyMenu', component: ToDoListForBuddyComponent},
  {path: 'events/viewEvents', component: EventsComponent},
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
