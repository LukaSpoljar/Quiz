import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DashboardsRoutingModule} from './dashboards-routing.module';
import {HomeComponent} from "./Home/home.component";
import {SolveQuizComponent} from './SolveQuiz/solve-quiz.component';
import {MakeQuizComponent} from "./MakeQuiz/make-quiz.component";
import {MatButtonToggleModule} from "@angular/material/button-toggle";
import {MatTableModule} from "@angular/material/table";
import {HighlightDirective} from "../../../Shared/Directives/highlight.directive";

@NgModule({
  declarations: [
    HomeComponent,
    MakeQuizComponent,
    SolveQuizComponent,
    HighlightDirective
  ],
  imports: [
    CommonModule,
    DashboardsRoutingModule,
    MatButtonToggleModule,
    MatTableModule
  ],
  exports: [
    DashboardsRoutingModule
  ]
})
export class DashboardsModule {
}
