import {Component} from '@angular/core';
import {
  AbstractControl, FormArray,
  FormControl,
  FormGroup, FormGroupDirective, NgForm,
  ValidatorFn,
  Validators
} from "@angular/forms";
import {Router} from "@angular/router";
import {ClientRoutes} from "../../Shared/Routes/ClientRoutes";
import {DataService} from "../../Shared/Server/Data Service/data.service";
import {ErrorStateMatcher} from "@angular/material/core";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {

  readonly clientRoutes: ClientRoutes;

  registerUserForm: FormGroup;
  errorMessages: string[] = ['', '', ''];
  confirmPasswordMatcher: ConfirmPasswordMatcher;

  private usernameControl: AbstractControl;
  private passwordControl: AbstractControl;
  private confirmPasswordControl: AbstractControl;

  constructor(private _router: Router, private _dataService: DataService) {

    this.clientRoutes = new ClientRoutes(this._router);

    this.registerUserForm = new FormGroup({
      username: new FormControl(null, this.createValidators(3, 10, /^[A-Za-z0-9_ #@.]*$/)),
      password: new FormControl(null, this.createValidators(8, 20, /^[A-Za-z0-9_ #@.]*$/)),
      confirmPassword: new FormControl(null)
    });

    this.confirmPasswordMatcher = new ConfirmPasswordMatcher();

    this.usernameControl = this.registerUserForm.controls['username'];
    this.passwordControl = this.registerUserForm.controls['password'];
    this.confirmPasswordControl = this.registerUserForm.controls['confirmPassword'];
  }


  usernameChange(event: any): void {
    let message: string = '';

    if (this.usernameControl.hasError('pattern')) {
      message = "Unallowed letters";

    } else if (this.usernameControl.hasError('minlength')) {

      let errorObject = this.usernameControl.getError('minlength');
      message = `Username must be at least ${errorObject.requiredLength} long`;

    } else if (this.usernameControl.hasError('maxlength')) {

      let errorObject = this.usernameControl.getError('maxlength');
      message = `Username must be max ${errorObject.requiredLength} long`;
    }
    this.errorMessages[0] = message;
  }

  passwordChange(event: any): void {

    let message: string = '';

    if (this.passwordControl.value !== this.confirmPasswordControl.value && this.passwordControl.valid) {
      this.errorMessages[2] = "Passwords must be same";
    } else {
      this.errorMessages[2] = message
    }

    if (this.passwordControl.hasError('pattern')) {
      message = "Unallowed letters";

    } else if (this.passwordControl.hasError('minlength')) {

      let errorObject = this.passwordControl.getError('minlength');
      let requiredLength: number = 0;

      if (errorObject) {
        requiredLength = errorObject.requiredLength;
      }
      message = `Password must be at least ${requiredLength} long`;

    } else if (this.passwordControl.hasError('maxlength')) {

      let errorObject = this.passwordControl.getError('maxlength');
      let requiredLength: number = 100;

      if (errorObject) {
        requiredLength = errorObject.requiredLength;
      }
      message = `Password must be max ${requiredLength} long`;
    }

    this.errorMessages[1] = message;
  }

  confirmPasswordChange(event: any): void {
    let message: string = '';
    if (this.confirmPasswordControl.value !== this.passwordControl.value) {
      message = "Passwords must be same";
    }
    this.errorMessages[2] = message;
  }


  onSubmit(): void {
    if (this.registerUserForm.valid) {
      this._dataService.registerUser(this.registerUserForm.getRawValue());
    }
  }

  private createValidators(minLength?: number, maxLength?: number, regex?: RegExp): ValidatorFn[] {

    let array: any[] = [];

    array.push(Validators.required);

    if (minLength && minLength > 0) {
      array.push(Validators.minLength(minLength));
    }

    if (maxLength && maxLength > 0) {
      array.push(Validators.maxLength(maxLength));
    }

    if (regex) {
      array.push(Validators.pattern(regex));
    }

    return array;
  }
}


export class ConfirmPasswordMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {

    let parent: FormGroup | FormArray | null | undefined = control?.parent;

    // @ts-ignore
    let passwordControl: AbstractControl = parent?.controls['password'];

    if (control?.value !== passwordControl.value) {
      control?.setErrors({
        equal: {
          value: false,
          confirmPassword: control.value,
          password: passwordControl.value
        }
      });
      return true;
    }

    return false;
  }
}
