import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddUpdateTutorialComponent } from './add-update-tutorial.component';

describe('AddUpdateTutorialComponent', () => {
  let component: AddUpdateTutorialComponent;
  let fixture: ComponentFixture<AddUpdateTutorialComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddUpdateTutorialComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddUpdateTutorialComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
