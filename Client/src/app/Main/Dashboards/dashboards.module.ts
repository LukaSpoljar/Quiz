import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DashboardsRoutingModule} from './dashboards-routing.module';
import {HomeComponent} from "./Home/home.component";
import {SolveQuizComponent} from './Solve Quiz/solve-quiz.component';
import {MakeQuizComponent} from "./Make Quiz/make-quiz.component";
import {MatButtonToggleModule} from "@angular/material/button-toggle";
import {MatTableModule} from "@angular/material/table";
import {MatButtonModule} from "@angular/material/button";
import {FilterDialogComponent} from "./Home/Filter Dialog/filter-dialog.component";
import {MatInputModule} from "@angular/material/input";
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {MatRadioModule} from "@angular/material/radio";
import {MAT_DIALOG_DEFAULT_OPTIONS, MatDialogModule} from "@angular/material/dialog";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HighlightDirective} from "../../Shared/Directives/Highlight/highlight.directive";
import {MatChipsModule} from "@angular/material/chips";
import {MatTabsModule} from "@angular/material/tabs";
import {MatIconModule} from "@angular/material/icon";
import {MatCardModule} from "@angular/material/card";
import {MatListModule} from "@angular/material/list";
import {MatGridListModule} from "@angular/material/grid-list";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {QuizResultsComponent} from './Solve Quiz/Results/quiz-results.component';
import { ConfirmAnswersComponent } from './Solve Quiz/Confirm Answers/confirm-answers.component';
import {MAT_BOTTOM_SHEET_DEFAULT_OPTIONS, MatBottomSheetModule} from "@angular/material/bottom-sheet";
import {MatSliderModule} from "@angular/material/slider";
import {MatSlideToggleModule} from "@angular/material/slide-toggle";
import {MatBadgeModule} from "@angular/material/badge";
import { MakeQuestionDialogComponent } from './Make Quiz/Make Question Dialog/make-question-dialog.component';

@NgModule({
  declarations: [
    HomeComponent,
    MakeQuizComponent,
    SolveQuizComponent,
    HighlightDirective,
    FilterDialogComponent,
    QuizResultsComponent,
    ConfirmAnswersComponent,
    MakeQuestionDialogComponent
  ],
    imports: [
        CommonModule,
        DashboardsRoutingModule,
        MatButtonToggleModule,
        MatTableModule,
        MatButtonModule,
        MatInputModule,
        MatAutocompleteModule,
        MatRadioModule,
        MatDialogModule,
        ReactiveFormsModule,
        MatChipsModule,
        MatTabsModule,
        MatIconModule,
        MatCardModule,
        MatListModule,
        MatGridListModule,
        MatCheckboxModule,
        MatBottomSheetModule,
        MatSliderModule,
        MatSlideToggleModule,
        MatBadgeModule,
        FormsModule
    ],
  exports: [
    DashboardsRoutingModule
  ],
  providers: [
    {provide: MAT_DIALOG_DEFAULT_OPTIONS, useValue: {width: '90%'}},
    {provide: MAT_BOTTOM_SHEET_DEFAULT_OPTIONS, useValue: {hasBackdrop: true}}
  ]
})
export class DashboardsModule {
}
