import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {DashboardsRoutingModule} from './dashboards-routing.module';
import {HomeComponent} from "./Home/home.component";
import {SolveQuizComponent} from './SolveQuiz/solve-quiz.component';
import {MakeQuizComponent} from "./MakeQuiz/make-quiz.component";


@NgModule({
  declarations: [
    HomeComponent,
    MakeQuizComponent,
    SolveQuizComponent
  ],
  imports: [
    CommonModule,
    DashboardsRoutingModule
  ],
  exports: [
    DashboardsRoutingModule
  ]
})
export class DashboardsModule {
}
