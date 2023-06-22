import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {ClientRoutes} from "../../Shared/Routes/ClientRoutes";
import {DataService} from "../../Shared/Server/Data Service/data.service";
import {AbstractControl, FormControl, FormGroup} from "@angular/forms";
import {ValidationRules} from "../Shared/Classes/ValidationRules";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['../Shared/Style/Auth.scss', './register.component.scss']
})
export class RegisterComponent implements OnInit {

  readonly clientRoutes: ClientRoutes;

  registerUserForm: FormGroup;
  errorMessages: string[] = [ValidationRules.messageRequired, ValidationRules.messageRequired, ValidationRules.messageRequired, ValidationRules.messageEmpty];

  hidePassword: boolean = true;
  hideConfirmPassword: boolean = true;

  readonly usernameControl: AbstractControl;
  readonly passwordControl: AbstractControl;
  readonly confirmPasswordControl: AbstractControl;

  constructor(private _router: Router, private _dataService: DataService) {

    this.clientRoutes = new ClientRoutes(this._router);

    this.registerUserForm = new FormGroup({
      username: new FormControl('', ValidationRules.createValidators({minLength: 3, maxLength: 10, regex: /^[A-Za-z0-9_]*$/})),
      password: new FormControl('', ValidationRules.createValidators({minLength: 8, maxLength: 20, regex: /^[^ ]*$/, hasPasswordValidation: true})),
      confirmPassword: new FormControl('', ValidationRules.createValidators()),
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
              ValidationRules.messageEmpty,
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



  private setDefaultFormControlsValidators(): void{

    this.usernameControl.clearValidators();
    this.usernameControl.addValidators(ValidationRules.createValidators({minLength: 3, maxLength: 10, regex: /^[A-Za-z0-9_]*$/}));
    this.usernameControl.updateValueAndValidity({emitEvent: false});

    this.passwordControl.clearValidators();
    this.passwordControl.addValidators(ValidationRules.createValidators({minLength: 8, maxLength: 20, regex: /^[^ ]*$/, hasPasswordValidation: true}));
    this.passwordControl.updateValueAndValidity({emitEvent: false});

    this.confirmPasswordControl.clearValidators();
    this.confirmPasswordControl.addValidators(ValidationRules.createValidators({minLength: 8, maxLength: 20, regex: /^[^ ]*$/, hasPasswordValidation: true, hasPasswordMatching: {formController: this.passwordControl}}));
    this.confirmPasswordControl.updateValueAndValidity({emitEvent: false});

     this.errorMessages = [
       ValidationRules.printErrorMessage('Username', this.usernameControl),
       ValidationRules.printErrorMessage('Password', this.passwordControl),
       ValidationRules.printErrorMessage('Password', this.confirmPasswordControl),
       ValidationRules.messageEmpty
     ];
  }
}
