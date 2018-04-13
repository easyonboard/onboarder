import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UsersInDepartmentListComponent } from './users-in-department-list.component';

describe('UsersInDepartmentListComponent', () => {
  let component: UsersInDepartmentListComponent;
  let fixture: ComponentFixture<UsersInDepartmentListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UsersInDepartmentListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UsersInDepartmentListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
