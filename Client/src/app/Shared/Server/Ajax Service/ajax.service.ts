import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../../environments/environment";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AjaxService {

  readonly urlPrefix: string;
  readonly authPrefix: string;

  constructor(private _http:HttpClient) {
    this.urlPrefix = environment.API_URL;
    this.authPrefix = environment.API_URL + '/api/authenticate';
  }

  /***** Registration *****/
  registerUser(credentials: {}):Observable<object> {
    return this._http.post(this.urlPrefix + '/user', credentials);
  }
}
