import {Component, OnInit} from '@angular/core';
import {ClientRoutesService} from "../../../Shared/Services/Client Routes/client-routes.service";
import {AbstractControl, FormControl, FormGroup} from "@angular/forms";
import {ValidationService} from "../../../Shared/Services/Validation/validation.service";
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {MakeQuestionDialogComponent} from "./Make Question Dialog/make-question-dialog.component";
import {subscribeOn} from "rxjs";


export interface Question {
  title: string,
  answers: string[],
  correctAnswers: string[]
}

@Component({
  selector: 'app-make-quiz',
  templateUrl: './make-quiz.component.html',
  styleUrls: ['./make-quiz.component.scss']
})
export class MakeQuizComponent implements OnInit {

  readonly quizForm: FormGroup;
  readonly quizTitleControl: AbstractControl;
  readonly quizCategoryControl: AbstractControl;
  readonly quizDifficultyControl: AbstractControl;

  questionsList: Question[] = [];


  constructor(readonly clientRoutes: ClientRoutesService, private _validationService: ValidationService, private _dialog: MatDialog) {

    this.quizForm = new FormGroup({
      'title': new FormControl('', this._validationService.createValidators()),
      'category': new FormControl('', this._validationService.createValidators()),
      'difficulty': new FormControl('1')
    })

    this.quizTitleControl = this.quizForm.controls['title'];
    this.quizCategoryControl = this.quizForm.controls['category'];
    this.quizDifficultyControl = this.quizForm.controls['difficulty'];
  }


  openDialog(): void {

    const dialogRef: MatDialogRef<MakeQuestionDialogComponent> = this._dialog.open(MakeQuestionDialogComponent, {
      maxWidth: '20cm'
    });

    dialogRef.afterClosed().subscribe((result: any): void => {

      console.log('The dialog was closed with result: ');

      if (result && result.answers.length > 0) {

        let answersToSave: any[] = [];
        let correctAnswersToSave: any[] = [];

        result.answers.forEach((element: any) => {
          answersToSave.push(element.title);
          if (element.checked) {
            correctAnswersToSave.push(element.title);
          }
        });

        let fullQuestion: Question = {
          title: result.title,
          answers: answersToSave,
          correctAnswers: correctAnswersToSave
        }

        if (correctAnswersToSave.length > 0) {
          this.questionsList.push(fullQuestion);
        }
      }
      console.dir(this.questionsList);
    });
  }

  removeQuiz(event: any, index: number): void {
    this.questionsList.splice(index, 1);
  }

  saveQuiz(): void {

    let quizData: any = this.quizForm.getRawValue();
    quizData.questions = this.questionsList;

    console.log("Quiz data:");
    console.dir(quizData)
  }


  ngOnInit(): void {
    this.quizForm.valueChanges.subscribe((value: any): void => {
      console.dir(this.quizForm.getRawValue());
    })
  }

  formatLabel(value: number): string {

    if (value == 1) {
      return 'Easy';
    } else if (value == 2) {
      return 'Medium';
    } else if (value == 3) {
      return 'Hard';
    }

    return `${value}`;
  }
}
