import {Component, Inject, OnDestroy, OnInit} from '@angular/core';
import {MAT_BOTTOM_SHEET_DATA, MatBottomSheetRef} from "@angular/material/bottom-sheet";
import {Answer} from "../solve-quiz.component";

@Component({
  selector: 'app-confirm-answers',
  templateUrl: './confirm-answers.component.html',
  styleUrls: ['./confirm-answers.component.scss']
})
export class ConfirmAnswersComponent implements OnInit, OnDestroy {

  private readonly className: string;

  isFinishConfirmed: boolean = false;

  constructor(private _bottomSheetRef: MatBottomSheetRef<ConfirmAnswersComponent>, @Inject(MAT_BOTTOM_SHEET_DATA) public data: Answer[]) {
    this.className = this.constructor.name;
  }

  confirmFinish(event: UIEvent): void {
    this.isFinishConfirmed = true;
    this._bottomSheetRef.dismiss();
  }

  ngOnInit(): void {
    console.log(`${this.className} initialized`);
    console.log(this.data);

    /* Sorting questions ascending by orderIndex */
    this.data.sort(function (a, b) {
      let x: number = a.orderIndex;
      let y: number = b.orderIndex;

      if (x < y) { return -1; } else if (x > y) { return 1; } { return 0; }
    });
  }

  ngOnDestroy(): void {
    console.log(`${this.className} destroyed`);
  }

}
