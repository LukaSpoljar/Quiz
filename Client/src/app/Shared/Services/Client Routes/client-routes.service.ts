import {Injectable} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class ClientRoutesService {

  private _activatedRoute?: ActivatedRoute;

  constructor(private _router: Router) {
  }

  setActivatedRoute(activatedRoute: ActivatedRoute): void {
    this._activatedRoute = activatedRoute;
  }

  /*Login route*/
  navigateToLogin(): Promise<boolean> | null {
    let path: string = 'Login';
    return this._router.navigate([path]);
  }

  /*Register route*/
  navigateToRegister(): Promise<boolean> | null {
    let path: string = 'Register';
    return this._router.navigate([path]);
  }

  /*Home page route*/
  navigateToDashboardHome(): Promise<boolean> | null {
    let path: string = '';
    return this._router.navigate([path]);
  }

  /*Make Quiz route*/
  navigateToDashboardMakeQuiz(): Promise<boolean> | null {
    let path: string = 'MakeQuiz';
    return this._router.navigate([path]);
  }

  /*Solve Quiz route*/
  navigateToDashboardSolveQuiz(uuid: string): Promise<boolean> | null {
    let path: string = 'SolveQuiz';
    return this._router.navigate([path, uuid]);
  }

  getRouteParameter(paramName: string): string | null {

    let valueToReturn: string | null = null;
    if (this._activatedRoute && paramName) {
      valueToReturn = this._activatedRoute?.snapshot?.params?.[paramName] ?? null;
    }
    return valueToReturn;
  }
}
