import {Component, OnInit} from '@angular/core';
import {ClientRoutesService} from "../../Shared/Services/Client Routes/client-routes.service";
import {DataService} from "../../Shared/Services/Server/Data Service/data.service";
import {AbstractControl, FormControl, FormGroup} from "@angular/forms";
import {ValidationService} from "../../Shared/Services/Validation/validation.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['../Shared/Style/Auth.scss', './register.component.scss']
})
export class RegisterComponent implements OnInit {

  registerUserForm: FormGroup;
  errorMessages: string[] = [];

  hidePassword: boolean = true;
  hideConfirmPassword: boolean = true;

  readonly usernameControl: AbstractControl;
  readonly passwordControl: AbstractControl;
  readonly confirmPasswordControl: AbstractControl;

  constructor(readonly clientRoutes: ClientRoutesService, private _dataService: DataService, private _validationRules: ValidationService) {

    this.registerUserForm = new FormGroup({
      username: new FormControl('', _validationRules.createValidators({minLength: 3, maxLength: 10, regex: /^[A-Za-z0-9_]*$/})),
      password: new FormControl('', _validationRules.createValidators({minLength: 8, maxLength: 20, regex: /^[^ ]*$/, hasPasswordValidation: true})),
      confirmPassword: new FormControl('', _validationRules.createValidators()),
      email: new FormControl('')
    });

    this.usernameControl = this.registerUserForm.controls['username'];
    this.passwordControl = this.registerUserForm.controls['password'];
    this.confirmPasswordControl = this.registerUserForm.controls['confirmPassword'];

    this.setDefaultFormControlsValidators();
  }

  ngOnInit(): void {
    this.registerUserForm.valueChanges.subscribe((value: any): void => {
      this.setDefaultFormControlsValidators();
    });
  }

  onSubmit(): void {

    this.registerUserForm.markAllAsTouched();

    if (this.registerUserForm.valid) {
      this._dataService.registerUser( this.registerUserForm.getRawValue());

      /*Async waiting for ERROR response from Login API*/
      this._dataService.getBehaviorSubjectError().subscribe({
        next: (errorResponse: any): void => {

          if(!errorResponse?.registrationSuccess) {

            /* If server fails to respond */
            this.errorMessages = [
              this._validationRules.messageEmpty,
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



  private setDefaultFormControlsValidators(): void{

    this.usernameControl.clearValidators();
    this.usernameControl.addValidators(this._validationRules.createValidators({minLength: 3, maxLength: 10, regex: /^[A-Za-z0-9_]*$/}));
    this.usernameControl.updateValueAndValidity({emitEvent: false});

    this.passwordControl.clearValidators();
    this.passwordControl.addValidators(this._validationRules.createValidators({minLength: 8, maxLength: 20, regex: /^[^ ]*$/, hasPasswordValidation: true}));
    this.passwordControl.updateValueAndValidity({emitEvent: false});

    this.confirmPasswordControl.clearValidators();
    this.confirmPasswordControl.addValidators(this._validationRules.createValidators({minLength: 8, maxLength: 20, regex: /^[^ ]*$/, hasPasswordValidation: true, hasPasswordMatching: {formController: this.passwordControl}}));
    this.confirmPasswordControl.updateValueAndValidity({emitEvent: false});

     this.errorMessages = [
       this._validationRules.printErrorMessage('Username', this.usernameControl),
       this._validationRules.printErrorMessage('Password', this.passwordControl),
       this._validationRules.printErrorMessage('Password', this.confirmPasswordControl),
       this._validationRules.messageEmpty
     ];
  }
}
