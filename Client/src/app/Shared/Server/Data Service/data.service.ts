import {Injectable} from '@angular/core';
import {ClientRoutes} from "../../Routes/ClientRoutes";
import {Router} from "@angular/router";
import {AjaxService} from "../Ajax Service/ajax.service";
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class DataService {

  readonly clientRoutes: ClientRoutes;

  private readonly loggedUserSubject:BehaviorSubject<any>;

  constructor(private _router: Router, private _ajaxService: AjaxService) {
    this.clientRoutes = new ClientRoutes(this._router);
    this.loggedUserSubject = new BehaviorSubject<any>({});
  }

  /***** Registration *****/
  registerUser(credentials: any = {}): void {

    console.dir(credentials);

    this._ajaxService.registerUser(credentials)
      .subscribe((response: any): void => {
        console.log("DOŠLO")
        console.dir(response);

        //Navigate to home page
        this.clientRoutes.navigateToDashboardHome();
      });
  }

  /***** Login *****/
  login(credentials: any = {}): void {

    console.dir(credentials);

    this._ajaxService.login(credentials)
      .subscribe((response: any): void => {
        console.dir(response);

        this.loggedUserSubject.next(response);

        //Navigate to home page
        this.clientRoutes.navigateToDashboardHome();
      });
  }

  public getLoggedUserSubject():BehaviorSubject<any>{
    return this.loggedUserSubject;
  }
}
