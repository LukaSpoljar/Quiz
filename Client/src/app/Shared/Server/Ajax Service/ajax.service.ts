import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../../../environments/environment";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AjaxService {

  readonly urlPrefix: string;
  readonly authPrefix: string;

  private readonly httpOptions:{headers: HttpHeaders} = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private _http: HttpClient) {
    this.urlPrefix = environment.API_URL;
    this.authPrefix = environment.API_URL + '';
  }

  /***** Registration *****/
  registerUser(credentials: {}): Observable<object> {
    return this._http.post(this.urlPrefix + '/player/register', credentials, this.httpOptions);
  }

  /***** Login *****/
  login(credentials: {}): Observable<object> {
    return this._http.post(this.urlPrefix + '/player/login', credentials, this.httpOptions);
  }
}
