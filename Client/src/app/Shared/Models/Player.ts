export class Player {
  id: string;
  username:string;
  uuid: string;

  constructor(private _id: string, private _username: string, private _uuid: string) {
    this.id = _id;
    this.username = _username;
    this.uuid = _uuid;
  }
}
