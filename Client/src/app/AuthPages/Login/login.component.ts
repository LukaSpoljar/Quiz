import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {DataService} from "../../Shared/Server/Data Service/data.service";
import {ClientRoutes} from "../../Shared/Routes/ClientRoutes";
import {AbstractControl, FormControl, FormGroup} from "@angular/forms";
import {ValidationRules} from "../Shared/Classes/ValidationRules";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['../Shared/Style/Auth.scss', './login.component.scss']
})
export class LoginComponent implements OnInit {

  readonly clientRoutes: ClientRoutes;

  readonly loginForm: FormGroup;
  readonly usernameFormControl: AbstractControl;
  readonly passwordFormControl: AbstractControl;

  hidePassword: boolean = true;
  errorMessages: string[] = [];

  constructor(private _router: Router, private _dataService: DataService) {
    this.clientRoutes = new ClientRoutes(this._router);

    this.loginForm = new FormGroup({
      username: new FormControl('', ValidationRules.createValidators()),
      password: new FormControl('', ValidationRules.createValidators()),
      email: new FormControl('')
    });

    this.usernameFormControl = this.loginForm.controls['username'];
    this.passwordFormControl = this.loginForm.controls['password'];

    this.setDefaultFormControlsValidators();
  }

  whenFormFocusIn(event: Event): void { /*this.setDefaultFormControlsValidators() */}

  ngOnInit(): void {
    this.loginForm.valueChanges.subscribe((value: any): void => {
      this.setDefaultFormControlsValidators();
      /* this.errorMessages = [ValidationRules.printErrorMessage('Username',this.usernameFormControl), ValidationRules.printErrorMessage('Password', this.passwordFormControl), ValidationRules.messageEmpty]; */
    });
  }

  onSubmit(): void {

    this.loginForm.markAllAsTouched();

    if (this.loginForm.valid) {

      this._dataService.login(this.loginForm.getRawValue());

      /*Async waiting for SUCCESSFUL response from Login API*/
      this._dataService.getBehaviorSubjectSuccess().subscribe({
        next: (response: any): void => {

          if(!response){
            /* Login data is WRONG */
            this.setWrongLoginValidators();
            this._dataService.getBehaviorSubjectSuccess().complete();
          }
        },
        error: (error: any): void => { console.error(error); },
        complete: (): void => { console.log("Observable is COMPLETED"); }
      });

      /*Async waiting for ERROR response from Login API*/
      this._dataService.getBehaviorSubjectError().subscribe({
        next: (errorResponse: any): void => {

          if(!errorResponse?.loginSuccess){

            /* If server fails to respond */
            this.errorMessages = [
              ValidationRules.messageEmpty,
              ValidationRules.messageEmpty,
              ValidationRules.messageNoServer
            ];

            this._dataService.getBehaviorSubjectError().complete();
          }
        },
        error: (error: any): void => { console.error(error); },
        complete: (): void => { console.log("Observable is COMPLETED") }
      });
    }
  }


  private setWrongLoginValidators(): void{
    this.usernameFormControl.clearValidators();
    this.usernameFormControl.addValidators(ValidationRules.createValidators({wrongLogin: true}));
    this.usernameFormControl.updateValueAndValidity({emitEvent: false});

    this.passwordFormControl.clearValidators();
    this.passwordFormControl.addValidators(ValidationRules.createValidators({wrongLogin: true}));
    this.passwordFormControl.updateValueAndValidity({emitEvent: false});

    this.errorMessages = [
      ValidationRules.printErrorMessage('Username',this.usernameFormControl),
      ValidationRules.printErrorMessage('Password', this.passwordFormControl),
      ValidationRules.messageWrongLoginData
    ];
  }

  private setDefaultFormControlsValidators(): void{
    this.usernameFormControl.clearValidators();
    this.usernameFormControl.addValidators(ValidationRules.createValidators());
    this.usernameFormControl.updateValueAndValidity({emitEvent: false});

    this.passwordFormControl.clearValidators();
    this.passwordFormControl.addValidators(ValidationRules.createValidators());
    this.passwordFormControl.updateValueAndValidity({emitEvent: false});

    this.errorMessages = [
      ValidationRules.printErrorMessage('Username',this.usernameFormControl),
      ValidationRules.printErrorMessage('Password', this.passwordFormControl),
      ValidationRules.messageEmpty
    ];
  }
}
