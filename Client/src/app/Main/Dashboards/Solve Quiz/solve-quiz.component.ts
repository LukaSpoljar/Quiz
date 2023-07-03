import {AfterViewInit, Component, ComponentRef, ElementRef, OnDestroy, OnInit, ViewChild, ViewContainerRef} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ClientRoutesService} from "../../../Shared/Services/Client Routes/client-routes.service";
import {MatBottomSheet, MatBottomSheetRef} from "@angular/material/bottom-sheet";
import {ConfirmAnswersComponent} from "./Confirm Answers/confirm-answers.component";
import {QuizResultsComponent} from "./Results/quiz-results.component";
import {MainComponent} from "../../Router Main/main.component";


export interface Answer {
  orderIndex: number,
  questionID: string,
  question: string,
  answers: string[]
}

@Component({
  selector: 'app-solve-quiz',
  templateUrl: './solve-quiz.component.html',
  styleUrls: ['./solve-quiz.component.scss']
})
export class SolveQuizComponent implements OnInit, AfterViewInit, OnDestroy {

  private readonly className: string;

  readonly tabsNames: string[] = ["Questions", "Results"];
  readonly allQuizQuestions: any[] = [
    {title: "What number comes after 3 ?", answers: ["-1", "2", "21", "4"], correctAnswer: '4'},
    {title: "What number comes before 2 ?", answers: ["19", "1", "34", "-10"], correctAnswer: '1'},
    {title: "Which ones are even numbers?", answers: ["1, 2, 3", "2, 4, 7", "100, 102, 14", "12, 4, 89"], correctAnswer: '100, 102, 14'}
  ];

  public selectedTabIndex: number = 0;
  public tabComponentRef: ComponentRef<any> | null = null;

  private myAnswers: Answer[] = [];

  @ViewChild('tabContent', {read: ViewContainerRef}) tabContent: ViewContainerRef | null = null;

  constructor(readonly clientRoutes: ClientRoutesService, private _activatedRoute: ActivatedRoute, private _bottomSheet: MatBottomSheet) {
    this.className = this.constructor.name;
    this.clientRoutes.setActivatedRoute(_activatedRoute);
    console.log("Parametar rute je: " + this.clientRoutes.getRouteParameter('uuid'));
  }

  ngOnInit(): void {

  }

  ngAfterViewInit(): void {
    MainComponent.setCSSVariablesOnResize();
  }

  ngOnDestroy(): void {
    console.log(`${this.className} destroyed`);
  }

  tryToSendQuizAnswers(): void {

    let confirmAnswersComponentRef: MatBottomSheetRef<ConfirmAnswersComponent> = this._bottomSheet.open(ConfirmAnswersComponent, {
      data: this.myAnswers,
      panelClass: 'custom-bottom-sheet'
    });

    confirmAnswersComponentRef.afterDismissed().subscribe((): void => {

      if (confirmAnswersComponentRef.instance.isFinishConfirmed && this.myAnswers.length > 0) {
        console.dir("ANSWERS CONFIRMED");

        if (this.tabContent) {
          this.tabContent.clear();
          this.tabComponentRef = this.tabContent.createComponent(QuizResultsComponent);
          this.tabComponentRef.instance.data = {
            tabTitle: this.tabsNames[1]
          };
        }
        this.selectedTabIndex = 1;
      }
    });
  }

  checkboxIsClicked(event: any, question: string, questionIndex: number): void {

    let selectedByUser: Answer = {questionID: questionIndex.toString(), question: question, answers: [event.source.value], orderIndex: questionIndex}

    let isNewQuestion: boolean = this.myAnswers.every((element: Answer): boolean => {
      if (element.questionID == selectedByUser.questionID && element.question == selectedByUser.question) {
        let doesNotExist: boolean = element.answers.every((oneExistingAnswer: string): boolean => oneExistingAnswer != selectedByUser.answers[0]);

        if (doesNotExist) {/* When answer is CHECKED but multiple answers in Question*/
          element.answers.push(selectedByUser.answers[0]);

        } else {/* When answer is UNCHECKED */
          element.answers.splice(element.answers.indexOf(selectedByUser.answers[0]), 1);

          if (element.answers.length <= 0) {/* When question gets UNANSWERED */
            this.myAnswers.splice(this.myAnswers.indexOf(element), 1);
          }
        }
        return false;
      }
      return true;
    });
    if (isNewQuestion) {/* When answer is CHECKED but only 1 answer in Question */
      this.myAnswers.push(selectedByUser);
    }
  }
}
