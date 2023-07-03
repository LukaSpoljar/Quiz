import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SolveQuizComponent } from './solve-quiz.component';

describe('SolveQuizComponent', () => {
  let component: SolveQuizComponent;
  let fixture: ComponentFixture<SolveQuizComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SolveQuizComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SolveQuizComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
