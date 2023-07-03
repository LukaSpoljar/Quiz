import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmAnswersComponent } from './confirm-answers.component';

describe('ConfirmAnswersComponent', () => {
  let component: ConfirmAnswersComponent;
  let fixture: ComponentFixture<ConfirmAnswersComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ConfirmAnswersComponent]
    });
    fixture = TestBed.createComponent(ConfirmAnswersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
