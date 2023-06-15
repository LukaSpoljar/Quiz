import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {ClientRoutes} from "../../Shared/Routes/ClientRoutes";
import {DataService} from "../../Shared/Server/Data Service/data.service";
import {AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators} from "@angular/forms";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  readonly clientRoutes: ClientRoutes;

  registerUserForm: FormGroup;
  errorMessages: string[] = ['Required', 'Required', 'Required'];

  hidePassword: boolean = true;
  hideConfirmPassword: boolean = true;

  readonly usernameControl: AbstractControl;
  readonly passwordControl: AbstractControl;
  readonly confirmPasswordControl: AbstractControl;

  constructor(private _router: Router, private _dataService: DataService) {

    this.clientRoutes = new ClientRoutes(this._router);

    this.registerUserForm = new FormGroup({
      username: new FormControl('', this.createValidators({minLength: 3, maxLength: 10, regex: /^[A-Za-z0-9_]*$/})),
      password: new FormControl('', this.createValidators({minLength: 8, maxLength: 20, regex: /^[^ ]*$/, hasPasswordValidation: true})),
      confirmPassword: new FormControl('', this.createValidators())
    });

    this.usernameControl = this.registerUserForm.controls['username'];
    this.passwordControl = this.registerUserForm.controls['password'];
    this.confirmPasswordControl = this.registerUserForm.controls['confirmPassword'];
  }

  ngOnInit(): void {
    this.usernameControl.valueChanges.subscribe((value: any): void => { this.errorMessages[0] = this.printErrorMessage('Username', this.usernameControl); });
    this.passwordControl.valueChanges.subscribe((value: any): void => {
      this.refreshConfirmPasswordValidators();
      this.errorMessages[1] = this.printErrorMessage('Password', this.passwordControl);
    });
    this.confirmPasswordControl.valueChanges.subscribe((value: any): void => {
      this.refreshConfirmPasswordValidators();
      this.errorMessages[2] = this.printErrorMessage('ConfirmPassword', this.confirmPasswordControl);
    });
  }

  onSubmit(): void {

    this.refreshConfirmPasswordValidators();
    this.registerUserForm.markAllAsTouched();

    if (this.registerUserForm.valid) {
      let formData = this.registerUserForm.getRawValue();

      formData.email = document.getElementsByName('email')[0];
      formData.email = formData.email.value;

      this._dataService.registerUser(formData);
    }
  }

  private createValidators(properties: { minLength?: number, maxLength?: number, regex?: RegExp, hasPasswordValidation?: boolean, hasPasswordMatching?: { word: string } } = {}): ValidatorFn[] {

    let array: ValidatorFn[] = [];

    array.push(Validators.required);

    if (properties.minLength !== undefined && properties.minLength > 0) { array.push(Validators.minLength(properties.minLength)); }
    if (properties.maxLength !== undefined && properties.maxLength > 0) { array.push(Validators.maxLength(properties.maxLength)); }
    if (properties.regex) { array.push(Validators.pattern(properties.regex)); }
    if (properties.hasPasswordValidation) { array.push(createPasswordValidator()); }
    if (properties.hasPasswordMatching?.word || properties.hasPasswordMatching?.word == '') { array.push(createPasswordsMatcherValidator()); }

    return array;

    function createPasswordValidator(): ValidatorFn {
      return (control: AbstractControl): ValidationErrors | null => {
        if (!control.value) { return null; }

        let hasUpperCase: boolean = /[A-Z]/.test(control?.value);
        let hasLowerCase: boolean = /[a-z]/.test(control?.value);
        let hasNumber: boolean = /[0-9]/.test(control?.value);
        let hasSpecialChar: boolean = /[^A-Za-z0-9]/.test(control?.value);

        let passwordValid: boolean = hasUpperCase && hasLowerCase && hasNumber && hasSpecialChar;
        return passwordValid ? null : { missingConditions: true };
      }
    }

    function createPasswordsMatcherValidator(): ValidatorFn {
      return (control: AbstractControl): ValidationErrors | null => {
        if (!control.value) { return null; }
        return control.value === properties.hasPasswordMatching?.word ? null : { passwordsNotMatching: true };
      }
    }
  }

  private printErrorMessage(formControlName: string, formControl: AbstractControl): string {
    let message: string = '';
    if (formControl.hasError('pattern')) {
      if (formControlName === 'Username') { message = "Only letters, numbers and underscores"; }
      else if (formControlName === 'Password') { message = "Space not allowed"; }
    } else if (formControl.hasError('required')) { message = "Required"; }
    else if (formControl.hasError('minlength')) { message = `${formControlName} must be at least ${formControl.getError('minlength').requiredLength} long`; }
    else if (formControl.hasError('maxlength')) { message = `${formControlName} must be max ${formControl.getError('maxlength').requiredLength} long`; }
    else if (formControl.hasError('missingConditions')) { message = `${formControlName} must contain every char type`; }
    else if (formControl.hasError('passwordsNotMatching')) { message = `Passwords don't match`; }
    return message;
  }

  private refreshConfirmPasswordValidators():void {
    this.confirmPasswordControl.clearValidators();
    this.confirmPasswordControl.addValidators(this.createValidators({minLength: 8, maxLength: 20, regex: /^[^ ]*$/, hasPasswordValidation: true, hasPasswordMatching: {word: this.passwordControl.value}}));
    this.confirmPasswordControl.updateValueAndValidity({emitEvent: false});
  }
}
