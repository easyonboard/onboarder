import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {StarRatingModule} from 'angular-star-rating';
import {GeneralInfosComponent} from './general-infos/general-infos.component';
import {TutorialsComponent} from './tutorials/tutorials.component';
import {ToDoListForBuddyComponent} from './common/DialogToDoListForBuddy/dialog-to-do-list-for-buddy.component';
import {AddTutorialComponent} from './tutorials/add-tutorial/add-tutorial.component';
import {ViewTutorialComponent} from './tutorials/view-tutorial/view-tutorial.component';
import {EventsComponent} from './events/events.component';
import {AddEventComponent} from './events/add-event/add-event.component';
import {NotFoundComponent} from './not-found-component/not-found-component.component';
import {LoggedInGuard} from './guard/logged-in.guard';
import {InterceptorExpiredTokenGuard} from './guard/interceptor-expired-token.guard';
import {FrontURLs} from './util/FrontURLs';

const routes: Routes = [
  {path: '', component: LoginComponent},
  {path: FrontURLs.LOGIN_PAGE, component: LoginComponent},
  {path: FrontURLs.INFO_PAGE, component: GeneralInfosComponent, canActivate: [LoggedInGuard, InterceptorExpiredTokenGuard]},
  {path: FrontURLs.TUTORIALS_PAGE, component: TutorialsComponent, canActivate: [LoggedInGuard, InterceptorExpiredTokenGuard]},
  {
    path: FrontURLs.ADD_TUTORIAL_PAGE,
    component: AddTutorialComponent,
    canActivate: [LoggedInGuard, InterceptorExpiredTokenGuard]
  },
  {
    path: FrontURLs.VIEW_TUTORIAL_PAGE,
    component: ViewTutorialComponent,
    canActivate: [LoggedInGuard, InterceptorExpiredTokenGuard]
  },


  {path: FrontURLs.ADD_EVENT, component: AddEventComponent, canActivate: [LoggedInGuard, InterceptorExpiredTokenGuard]},
  {path: FrontURLs.MATE_MENU, component: ToDoListForBuddyComponent, canActivate: [LoggedInGuard, InterceptorExpiredTokenGuard]},
  {path: FrontURLs.EVENTS_PAGE, component: EventsComponent, canActivate: [LoggedInGuard, InterceptorExpiredTokenGuard]},
  {path: FrontURLs.ERROR_404, component: NotFoundComponent},
  {path: '**', redirectTo: '/' + FrontURLs.ERROR_404}
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
