import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserInfoFormularComponent } from './user-info-formular.component';

describe('UserInfoFormularComponent', () => {
  let component: UserInfoFormularComponent;
  let fixture: ComponentFixture<UserInfoFormularComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserInfoFormularComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserInfoFormularComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
