import {Component, ElementRef, ViewChild} from '@angular/core';
import {Router} from "@angular/router";
import {DataService} from "../../Shared/Server/Data Service/data.service";
import {ClientRoutes} from "../../Shared/Routes/ClientRoutes";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['../../Shared/Style/AuthPages.scss', './login.component.scss']
})
export class LoginComponent {

  readonly clientRoutes: ClientRoutes;

  loginForm: FormGroup;
  errorMessages: string[] = ['Required', 'Required'];

  hidePassword: boolean = true;

  @ViewChild('emptyOne') botEmailInput: ElementRef | null = null;

  constructor(private _router: Router, private _dataService: DataService) {
    this.clientRoutes = new ClientRoutes(this._router);

    this.loginForm = new FormGroup({
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),
    });
  }

  onSubmit(): void {

    this.loginForm.markAllAsTouched();

    if (this.loginForm.valid) {
      let formData = this.loginForm.getRawValue();
      formData.email = this.botEmailInput?.nativeElement.value;

      this._dataService.login(formData);

      /*Async waiting for response from Login API*/
      this._dataService.getLoggedUserSubject().subscribe((response: any): void => {
        console.log("Response from Login API has come in Login component " + response?.value);
      });
    }
  }

}
