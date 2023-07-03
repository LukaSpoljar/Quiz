import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MakeQuestionDialogComponent } from './make-question-dialog.component';

describe('MakeQuestionDialogComponent', () => {
  let component: MakeQuestionDialogComponent;
  let fixture: ComponentFixture<MakeQuestionDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MakeQuestionDialogComponent]
    });
    fixture = TestBed.createComponent(MakeQuestionDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
