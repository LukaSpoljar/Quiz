import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {FormControl} from "@angular/forms";


export interface MakingQuestion {
  title: string,
  answers: AnswerState[]
}

export interface AnswerState {
  title: string,
  checked: boolean
}


@Component({
  selector: 'app-make-question-dialog',
  templateUrl: './make-question-dialog.component.html',
  styleUrls: ['../../Shared/Style/Dialogs.scss', './make-question-dialog.component.scss']
})
export class MakeQuestionDialogComponent implements OnInit {

  answersList: AnswerState[] = [];
  titleControl: FormControl;

  private defaultAnswer: AnswerState | null = null;

  constructor(public dialogRef: MatDialogRef<MakeQuestionDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: MakingQuestion) {

    this.data = new class implements MakingQuestion {
      answers: AnswerState[] = [];
      title: string = '';
    }

    this.titleControl = new FormControl('');
  }

  ngOnInit(): void {
    this.titleControl.valueChanges.subscribe((value: any): void => {
      this.data.title = this.titleControl.value;
    });
  }

  addAnswer(): void {
    this.defaultAnswer = new class implements AnswerState {
      checked: boolean = false;
      title: string = '';
    }
    this.answersList.push(this.defaultAnswer);
  }

  removeAnswer(event: UIEvent, indexToRemove: number = -1): void {
    if (indexToRemove >= 0) {
      this.answersList.splice(indexToRemove, 1);
    }
  }

  done(): void {
    let answers: any[] = Object.values(this.answersList);
    this.data.answers = answers.filter((element: any): boolean => element?.['title'] != '');
    console.dir(this.answersList)
  }
}
