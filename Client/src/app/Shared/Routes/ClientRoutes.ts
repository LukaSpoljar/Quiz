import {Router} from "@angular/router";

export class ClientRoutes{

  constructor(private _router: Router) {}

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
}
