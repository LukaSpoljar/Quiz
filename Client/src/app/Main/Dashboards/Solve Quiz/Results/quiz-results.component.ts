import {AfterViewInit, Component, ElementRef, Input, OnDestroy, OnInit} from '@angular/core';
import {ClientRoutesService} from "../../../../Shared/Services/Client Routes/client-routes.service";
import {MainComponent} from "../../../Router Main/main.component";

@Component({
  selector: 'app-quiz-results',
  templateUrl: './quiz-results.component.html',
  styleUrls: ['./quiz-results.component.scss']
})
export class QuizResultsComponent implements OnInit, AfterViewInit,OnDestroy{
  @Input() data: any;

  private readonly className: string ;
  private readonly currentUser: string = "Name_E";

  readonly displayedColumns: string[] = ['position', 'name', 'completion'];
  readonly usersResults: {}[] = [
    {position: 1, name: "Name_A", completion: 89},
    {position: 2, name: "Name_B", completion: 71},
    {position: 3, name: "Name_C", completion: 70},
    {position: 4, name: "Name_D", completion: 50},
    {position: 5, name: "Name_E", completion: 30}
  ];

  tabTitle: string = '';

  constructor(private _elementRef: ElementRef, readonly clientRoutes:ClientRoutesService) {
    this.className = this.constructor.name;
  }

  ngOnInit(): void {
    console.log(`${this.className} initialized`);
    console.log("Data is: " + this.data);

    this.tabTitle = this.data.tabTitle;
    console.log(this.tabTitle)
  }

  ngAfterViewInit(): void {
    MainComponent.setCSSVariablesOnResize();
  }

  ngOnDestroy(): void {
    console.log(`${this.className} destroyed`);
  }

  paintCurrentUser(data: any): {}{

    let styleObject: {} = {};

    if(data.name == this.currentUser){
      styleObject = {
        'background-color': 'rgb(63, 81, 181)',
        'color': 'white',
        'font-weight': 'bold'
      }
    }

    return styleObject
  }
}
