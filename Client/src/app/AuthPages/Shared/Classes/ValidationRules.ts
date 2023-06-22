import {AbstractControl, ValidationErrors, ValidatorFn, Validators} from "@angular/forms";

export class ValidationRules {

  public static readonly messageEmpty: string = "";
  public static readonly messageRequired: string = "Required";
  public static readonly messageNoServer: string = "Cannot communicate with server";
  public static readonly messageWrongLoginData: string = "Wrong username or password";

  public static createValidators(properties: { minLength?: number, maxLength?: number, regex?: RegExp, hasPasswordValidation?: boolean, hasPasswordMatching?: { formController: AbstractControl }, email?: boolean, serverProblems?: boolean, wrongLogin?: boolean} = {}): ValidatorFn[] {

    let array: ValidatorFn[] = [];

    array.push(Validators.required);

    if (properties.minLength !== undefined && properties.minLength > 0) { array.push(Validators.minLength(properties.minLength)); }
    if (properties.maxLength !== undefined && properties.maxLength > 0) { array.push(Validators.maxLength(properties.maxLength)); }
    if (properties.regex) { array.push(Validators.pattern(properties.regex)); }
    if (properties.hasPasswordValidation) { array.push(createPasswordValidator()); }
    if (properties.hasPasswordMatching?.formController) { array.push(createPasswordsMatcherValidator()); }
    if (properties.wrongLogin){array.push(createErrorBoxValidator('login'))}

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
        return control.value === properties.hasPasswordMatching?.formController.value ? null : { passwordsNotMatching: true };
      }
    }

    function createErrorBoxValidator(word: string) : ValidatorFn {
      return (control: AbstractControl): ValidationErrors | null => {

        let errorObject: {} | null = null;

        if(word == 'login'){ errorObject = { wrongLogin: true } }

        return errorObject;
      }
    }
  }


  public static printErrorMessage(formControlDisplayName: string, formControl: AbstractControl): string {
    let message: string = '';

    if (formControl.hasError('wrongLogin')){ message = ValidationRules.messageEmpty; }
    else if (formControl.hasError('required')) { message = ValidationRules.messageRequired; }
    else if (formControl.hasError('maxlength')) { message = `${formControlDisplayName} must be max ${formControl.getError('maxlength').requiredLength} long`; }
    else if (formControl.hasError('pattern')) {
      if (formControlDisplayName === 'Username') { message = "Only letters, numbers and underscores"; }
      else if (formControlDisplayName === 'Password') { message = "Space not allowed"; }
    }
    else if (formControl.hasError('passwordsNotMatching')) { message = `Passwords don't match`; }
    else if (formControl.hasError('missingConditions')) { message = `${formControlDisplayName} must contain every char type`; }
    else if (formControl.hasError('minlength')) { message = `${formControlDisplayName} must be at least ${formControl.getError('minlength').requiredLength} long`; }

    return message;
  }

}
