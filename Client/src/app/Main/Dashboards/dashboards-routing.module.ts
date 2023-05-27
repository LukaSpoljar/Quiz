import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from "./Home/home.component";
import {MakeQuizComponent} from "./MakeQuiz/make-quiz.component";

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'MakeQuiz', component: MakeQuizComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DashboardsRoutingModule {
}
