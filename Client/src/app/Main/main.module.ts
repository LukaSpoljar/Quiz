import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MainRoutingModule} from './main-routing.module';
import {MainComponent} from './Router Main/main.component';
import {NavigationComponent} from './Navigation/navigation.component';
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatButtonToggleModule} from "@angular/material/button-toggle";
import {DashboardsModule} from "./Dashboards/dashboards.module";
import {MatButtonModule} from "@angular/material/button";
import {MatCardModule} from "@angular/material/card";
import {MatIconModule} from "@angular/material/icon";

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
        MatButtonToggleModule,
        MatButtonModule,
        MatCardModule,
        MatIconModule
    ]
})
export class MainModule {
}
