import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./AuthPages/Login/login.component";
import {RegisterComponent} from "./AuthPages/Register/register.component";
import {MainModule} from "./Main/main.module";
import {MainComponent} from "./Main/MainRouter/main.component";

const routes: Routes = [
  {path: '', loadChildren: () => MainModule, component: MainComponent},
  {path: 'Login', component: LoginComponent},
  {path: 'Register', component: RegisterComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
