import {Component, OnInit} from '@angular/core';
import {ClientRoutes} from "../../Shared/Routes/ClientRoutes";
import {Router} from "@angular/router";
import {MatButtonToggleChange} from "@angular/material/button-toggle";

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss']
})
export class NavigationComponent implements OnInit{

  readonly clientRoutes: ClientRoutes;

  constructor(private _router: Router) {
    this.clientRoutes = new ClientRoutes(this._router);
  }

  ngOnInit(): void {
  }

  public toggle(matButtonToggleChange: MatButtonToggleChange): void { console.dir(matButtonToggleChange); }

  public logOut(): void{

  }
}
