import {Component, OnInit} from '@angular/core';
import {DataService} from "../../Shared/Services/Server/Data Service/data.service";
import {ClientRoutesService} from "../../Shared/Services/Client Routes/client-routes.service";
import {AbstractControl, FormControl, FormGroup} from "@angular/forms";
import {ValidationService} from "../../Shared/Services/Validation/validation.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['../Shared/Style/Auth.scss', './login.component.scss']
})
export class LoginComponent implements OnInit {

  readonly loginForm: FormGroup;
  readonly usernameFormControl: AbstractControl;
  readonly passwordFormControl: AbstractControl;

  hidePassword: boolean = true;
  errorMessages: string[] = [];

  constructor( readonly clientRoutes: ClientRoutesService, private _dataService: DataService, private _validationRules: ValidationService) {

    this.loginForm = new FormGroup({
      username: new FormControl('', _validationRules.createValidators()),
      password: new FormControl('', _validationRules.createValidators()),
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
              this._validationRules.messageEmpty,
              this._validationRules.messageEmpty,
              this._validationRules.messageNoServer
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
    this.usernameFormControl.addValidators(this._validationRules.createValidators({wrongLogin: true}));
    this.usernameFormControl.updateValueAndValidity({emitEvent: false});

    this.passwordFormControl.clearValidators();
    this.passwordFormControl.addValidators(this._validationRules.createValidators({wrongLogin: true}));
    this.passwordFormControl.updateValueAndValidity({emitEvent: false});

    this.errorMessages = [
      this._validationRules.printErrorMessage('Username',this.usernameFormControl),
      this._validationRules.printErrorMessage('Password', this.passwordFormControl),
      this._validationRules.messageWrongLoginData
    ];
  }

  private setDefaultFormControlsValidators(): void{
    this.usernameFormControl.clearValidators();
    this.usernameFormControl.addValidators(this._validationRules.createValidators());
    this.usernameFormControl.updateValueAndValidity({emitEvent: false});

    this.passwordFormControl.clearValidators();
    this.passwordFormControl.addValidators(this._validationRules.createValidators());
    this.passwordFormControl.updateValueAndValidity({emitEvent: false});

    this.errorMessages = [
      this._validationRules.printErrorMessage('Username',this.usernameFormControl),
      this._validationRules.printErrorMessage('Password', this.passwordFormControl),
      this._validationRules.messageEmpty
    ];
  }
}
