import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AppComponent} from './app.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {AppRoutingModule} from './app-routing.module';
import {UserService} from './service/user.service';
import {LoginComponent} from './login/login.component';
import {MaterialService} from './service/material.service';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ScrollEventModule} from 'ngx-scroll-event';
import {GeneralInfosComponent} from './general-infos/general-infos.component';
import {UserAddComponent} from './users/user-add/user-add-update.component';
import {CommonComponentsService} from './common/common-components.service';
import {ToDoListForBuddyComponent} from './common/DialogToDoListForBuddy/dialog-to-do-list-for-buddy.component';
import {DialogCheckListComponent} from './common/DialogCheckList/dialog-check-list.component';
import {DialogNewEmployeeComponent} from './common/DialogNewEmployee/dialog-new-employee.component';
import {UsersInDepartmentListComponent} from './users/users-in-department-list/users-in-department-list.component';
import {DialogDeleteUsersComponent} from './common/DialogDeleteUsers/dialog-delete-users.component';
import {ExcelService} from './service/excel.service';
import {TutorialsComponent} from './tutorials/tutorials.component';
import {DialogLeaveCheckListComponent} from './common/DialogLeaveCheckList/dialog-leave-check-list.component';
import {AddTutorialComponent} from './tutorials/add-tutorial/add-tutorial.component';
import {AddEventComponent} from './events/add-event/add-event.component';
import {NgMultiSelectDropDownModule} from 'ng-multiselect-dropdown';
import {TutorialService} from './service/tutorial.service';
import {EventService} from './service/event.service';
import {FlexLayoutModule} from '@angular/flex-layout';
import {ViewTutorialComponent} from './tutorials/view-tutorial/view-tutorial.component';
import {EventsComponent} from './events/events.component';
import {LocationService} from './service/location.service';
import {AngularMultiSelectModule} from 'angular2-multiselect-dropdown';
import {NotFoundComponent} from './not-found-component/not-found-component.component';
import {
  MatButtonModule,
  MatCardModule,
  MatCheckboxModule,
  MatChipsModule,
  MatDatepickerModule,
  MatDialogModule,
  MatExpansionModule,
  MatFormFieldModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatMenuModule,
  MatNativeDateModule,
  MatOptionModule,
  MatPaginatorModule,
  MatProgressBarModule,
  MatProgressSpinnerModule,
  MatSelectModule,
  MatSnackBarModule,
  MatTableModule,
  MatToolbarModule,
  MatTooltipModule,
} from '@angular/material';
import {UtilityService} from './service/utility.service';
import {Angular5TimePickerModule} from 'angular5-time-picker';
import {DialogProfileInfoComponent} from './common/DialogProfileInfoComponent/dialog-profile-info.component';
import {Interceptor} from './common/core-auth/interceptor';
import {AuthService} from './common/core-auth/auth.service';
import {TokenStorage} from './common/core-auth/token.storage';
import {LoggedInGuard} from './guard/logged-in.guard';
import {InterceptorExpiredTokenGuard} from './guard/interceptor-expired-token.guard';
import {DepartmentService} from './service/department.service';
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    GeneralInfosComponent,
    UserAddComponent,
    DialogCheckListComponent,
    DialogNewEmployeeComponent,
    ToDoListForBuddyComponent,
    DialogDeleteUsersComponent,
    UsersInDepartmentListComponent,
    DialogProfileInfoComponent,
    TutorialsComponent,
    AddTutorialComponent,
    AddEventComponent,
    DialogLeaveCheckListComponent,
    ViewTutorialComponent,
    EventsComponent,
    AddEventComponent,
    NotFoundComponent,
  ],
  imports: [
    NgMultiSelectDropDownModule,
    Angular5TimePickerModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ScrollEventModule,
    MatProgressSpinnerModule,
    MatProgressBarModule,
    MatTooltipModule,
    MatChipsModule,
    MatIconModule,
    MatButtonModule,
    MatFormFieldModule,
    MatDialogModule,
    MatGridListModule,
    MatCardModule,
    MatListModule,
    MatTableModule,
    MatCheckboxModule,
    MatSelectModule,
    MatOptionModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatExpansionModule,
    MatPaginatorModule,
    MatSnackBarModule,
    MatInputModule,
    FlexLayoutModule,
    MatMenuModule,
    MatToolbarModule,
    AngularMultiSelectModule
  ],
  entryComponents: [
    DialogCheckListComponent,
    DialogNewEmployeeComponent,
    DialogLeaveCheckListComponent,
    ToDoListForBuddyComponent,
    UserAddComponent,
    UsersInDepartmentListComponent,
    DialogDeleteUsersComponent,
    DialogProfileInfoComponent
  ],
  providers: [UserService,
    MaterialService,
    UtilityService,
    DepartmentService,
    CommonComponentsService,
    ExcelService,
    TutorialService,
    EventService,
    LocationService,
    AuthService,
    TokenStorage,
    LoggedInGuard,
    InterceptorExpiredTokenGuard,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: Interceptor,
      multi: true
    }

  ],
  bootstrap: [AppComponent]
})

export class AppModule {
}
