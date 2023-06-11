import { Injectable } from '@angular/core';
import {ClientRoutes} from "../../Routes/ClientRoutes";
import {Router} from "@angular/router";
import {AjaxService} from "../Ajax Service/ajax.service";

@Injectable({
  providedIn: 'root'
})
export class DataService {

  readonly clientRoutes: ClientRoutes;

  constructor(private _router: Router, private _ajaxService: AjaxService) {
    this.clientRoutes = new ClientRoutes(this._router);
  }

  /***** REGISTRATION *****/
  registerUser(credentials: {}):void {
    this._ajaxService.registerUser(credentials)
      .subscribe((response: any):void => {
          console.dir(response);

          //Navigate to home page
          this.clientRoutes.navigateToDashboardHome();
      });
  }
}
