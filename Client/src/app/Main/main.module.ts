import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MainRoutingModule } from './main-routing.module';
import { MainComponent } from './MainRouter/main.component';
import { NavigationComponent } from './Navigation/navigation.component';
import {DashboardsModule} from "./MainRouter/Dashboards/dashboards.module";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatButtonToggleModule} from "@angular/material/button-toggle";



@NgModule({
  declarations: [
    MainComponent,
    NavigationComponent
  ],
  imports: [
    CommonModule,
    MainRoutingModule,
    DashboardsModule,
    MatSidenavModule,
    MatButtonToggleModule
  ]
})
export class MainModule { }
