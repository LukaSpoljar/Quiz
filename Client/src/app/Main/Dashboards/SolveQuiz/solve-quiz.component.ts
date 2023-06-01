import { Component } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ClientRoutes} from "../../../Shared/Routes/ClientRoutes";

@Component({
  selector: 'app-solve-quiz',
  templateUrl: './solve-quiz.component.html',
  styleUrls: ['./solve-quiz.component.scss']
})
export class SolveQuizComponent {

  readonly clientRoutes : ClientRoutes;
  constructor(private _router: Router, private _activatedRoute:ActivatedRoute) {
    this.clientRoutes = new ClientRoutes(_router, this._activatedRoute)
    console.log("Parametar rute je: " + this.clientRoutes.getRouteParameter('uuid'));
  }

}
