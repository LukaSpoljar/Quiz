import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MainRoutingModule } from './main-routing.module';
import { MainComponent } from './MainRouter/main.component';
import { NavigationComponent } from './Navigation/navigation.component';
import {DashboardsModule} from "./Dashboards/dashboards.module";


@NgModule({
  declarations: [
    MainComponent,
    NavigationComponent
  ],
  imports: [
    CommonModule,
    MainRoutingModule,
    DashboardsModule
  ]
})
export class MainModule { }
