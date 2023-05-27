import { Component } from '@angular/core';
import {ClientRoutes} from "../../Shared/Routes/ClientRoutes";
import {Router} from "@angular/router";

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss']
})
export class NavigationComponent {

  readonly clientRoutes : ClientRoutes;

  constructor(private _router:Router) {
    this.clientRoutes = new ClientRoutes(this._router);
  }
}
