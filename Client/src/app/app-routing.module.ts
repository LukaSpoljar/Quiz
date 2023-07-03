import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./Auth Pages/Login/login.component";
import {RegisterComponent} from "./Auth Pages/Register/register.component";
import {MainModule} from "./Main/main.module";
import {MainComponent} from "./Main/Router Main/main.component";

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
