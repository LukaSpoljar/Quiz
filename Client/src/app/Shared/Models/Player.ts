export class Player {
  username:string;
  password: string;

  constructor(private _username: string, private _password: string) {
    this.username = _username;
    this.password = _password;
  }
}
