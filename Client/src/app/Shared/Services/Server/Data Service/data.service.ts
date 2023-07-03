import { Injectable } from '@angular/core';
import { ClientRoutesService } from "../../Client Routes/client-routes.service";
import { Router } from "@angular/router";
import { AjaxService } from "../Ajax Service/ajax.service";
import { BehaviorSubject } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class DataService {

  readonly clientRoutes: ClientRoutesService;

  private readonly behaviorSubjects: { success: any, error: any };

  constructor(private _router: Router, private _ajaxService: AjaxService) {
    this.clientRoutes = new ClientRoutesService(this._router);
    this.behaviorSubjects = {
      success: new BehaviorSubject({}),
      error: new BehaviorSubject({}),
    }
  }

  /***** Registration *****/
  registerUser(credentials: any = {}): void {

    this.resetBehaviorSubjects();

    this._ajaxService.registerUser(credentials).subscribe({
      next: (response: any): void => {
        console.log("Registration data received");
      },
      error: (error: any): void => {
        console.error("Registration failed", error);
        this.behaviorSubjects.error.next(error);
      },
      complete: (): void => {
        this.clientRoutes.navigateToDashboardHome();
        console.log("Registration COMPLETED");
      }
    });
  }

  /***** Login *****/
  login(credentials: any = {}): void {

    this.resetBehaviorSubjects();

    this._ajaxService.login(credentials).subscribe({
      next: (response: any): void => {
        console.log("Login data received & sending it as observable");
        this.behaviorSubjects.success.next(response);

        if (response) {
          /* If login data is CORRECT */
          this.clientRoutes.navigateToDashboardHome();
        }
      },
      error: (error: any): void => {
        console.error("Login failed", error);
        this.behaviorSubjects.error.next(error);
      },
      complete: (): void => {
        console.log("Login COMPLETED");
      }
    });
  }

  public getBehaviorSubjectSuccess(): BehaviorSubject<any> { return this.behaviorSubjects?.success; }
  public getBehaviorSubjectError(): BehaviorSubject<any> { return this.behaviorSubjects?.error; }


  private resetBehaviorSubjects(properties: { successOrError: number, startValue: any } = { successOrError: 0, startValue: {} }): void {


    switch (properties.successOrError) {

      case 1:
        this.behaviorSubjects.success.complete();
        this.behaviorSubjects.success = new BehaviorSubject<typeof properties.startValue>(properties.startValue);

        break;

      case -1:
        this.behaviorSubjects.error.complete();
        this.behaviorSubjects.error = new BehaviorSubject<typeof properties.startValue>(properties.startValue);

        break;

      default:

        this.behaviorSubjects.success.complete();
        this.behaviorSubjects.success = new BehaviorSubject<typeof properties.startValue>(properties.startValue);

        this.behaviorSubjects.error.complete();
        this.behaviorSubjects.error = new BehaviorSubject<typeof properties.startValue>(properties.startValue);
    }
  }
}
