import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./Home/home.component";
import {MakeQuizComponent} from "./MakeQuiz/make-quiz.component";
import {SolveQuizComponent} from "./SolveQuiz/solve-quiz.component";

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'MakeQuiz', component: MakeQuizComponent},
  {path: 'SolveQuiz/:uuid', component: SolveQuizComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DashboardsRoutingModule {
}
