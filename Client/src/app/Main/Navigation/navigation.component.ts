import {AfterViewInit, Component, ElementRef, OnInit} from '@angular/core';
import {MatButtonToggleChange} from "@angular/material/button-toggle";
import {ClientRoutesService} from "../../Shared/Services/Client Routes/client-routes.service";

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss']
})
export class NavigationComponent implements OnInit, AfterViewInit{

  constructor(readonly clientRoutes: ClientRoutesService, private _elementRef:ElementRef) {
  }

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {


  }

  public toggle(matButtonToggleChange: MatButtonToggleChange): void { console.dir(matButtonToggleChange); }

  public logOut(): void{

  }
}
