import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {ClientRoutes} from "../../Shared/Routes/ClientRoutes";
import {DataService} from "../../Shared/Server/Data Service/data.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  readonly clientRoutes: ClientRoutes;

  // User registration form
  registerUserForm: FormGroup;

  constructor(private _router: Router, private _dataService: DataService) {

    this.clientRoutes = new ClientRoutes(this._router);

    this.registerUserForm = new FormGroup({
      username: new FormControl(null, [Validators.minLength(3), Validators.maxLength(10)]),
      password: new FormControl(null, [Validators.minLength(8), Validators.maxLength(20)]),
      confirmPassword: new FormControl(null, [Validators.minLength(8), Validators.maxLength(20)])
    });
  }

  ngOnInit(): void {

    let spanElements: HTMLCollectionOf<HTMLSpanElement> = document.getElementsByTagName('span');

    let usernameControl: AbstractControl = this.registerUserForm.controls['username'];
    let passwordControl: AbstractControl = this.registerUserForm.controls['password'];
    let confirmPasswordControl: AbstractControl = this.registerUserForm.controls['confirmPassword'];

    usernameControl.valueChanges.subscribe((value: string): void => {

      let message: string = '';

      if (this.containsForbiddenChars(value)) {
        message = "Unallowed letters";
      } else {
        if (usernameControl.errors?.['minlength']) {
          message = `Username must be at least ${usernameControl.errors?.['minlength']['requiredLength']} long`;
        } else if (usernameControl.errors?.['maxlength']) {
          message = `Username must be max ${usernameControl.errors?.['maxlength']['requiredLength']} long`;
        }
      }

      spanElements[0].innerText = message;
    });

    passwordControl.valueChanges.subscribe((value: string): void => {
      let message: string = '';

      if (passwordControl.value !== confirmPasswordControl.value && passwordControl.errors === null && passwordControl.value?.length > 0) {
        spanElements[2].innerText = "Passwords must be same";
      } else {
        spanElements[2].innerText = message;
      }

      if (this.containsForbiddenChars(value)) {
        message = "Unallowed letters";
      } else {
        if (passwordControl.value === '' || passwordControl.errors?.['minlength']) {
          let requiredLength = passwordControl.errors?.['minlength']['requiredLength'] ? passwordControl.errors?.['minlength']['requiredLength'] : 0;
          message = `Password must be at least ${requiredLength} long`;
        } else if (passwordControl.errors?.['maxlength']) {
          message = `Password must be max ${passwordControl.errors?.['maxlength']['requiredLength']} long`;
        }
      }

      spanElements[1].innerText = message;
    });

    confirmPasswordControl.valueChanges.subscribe((value: string): void => {
      let message: string = '';

      if (confirmPasswordControl.value !== passwordControl.value) {
        message = "Passwords must be same";
      } else {
        if (confirmPasswordControl.errors?.['minlength']) {
          message = `Password must be at least ${confirmPasswordControl.errors?.['minlength']['requiredLength']} long`;
        } else if (confirmPasswordControl.errors?.['maxlength']) {
          message = `Password must be max ${confirmPasswordControl.errors?.['maxlength']['requiredLength']} long`;
        }
      }

      spanElements[2].innerText = message;
    });
  }

  onSubmit() : void{

    let formData = this.registerUserForm.getRawValue();

    let username = formData.username;
    let password = formData.password;
    let confirmedPassword = formData.confirmPassword;

    if(this.registerUserForm.status == "VALID" && username && password && confirmedPassword){

      if(!(this.containsForbiddenChars(username) || this.containsForbiddenChars(password) || this.containsForbiddenChars(confirmedPassword))){

        this._dataService.registerUser(formData);
      }
    }
  }

  private containsForbiddenChars(word: string): boolean {
    let specChars: RegExp = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]/;
    return specChars.test(word);
  }
}
