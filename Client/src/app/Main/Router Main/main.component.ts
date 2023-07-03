import {AfterViewInit, Component, ElementRef, ViewChild} from '@angular/core';
import {FilterQuizzesData} from "../Dashboards/Home/home.component";
import {ClientRoutesService} from "../../Shared/Services/Client Routes/client-routes.service";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements AfterViewInit {

  public static filteringQuizzesData: FilterQuizzesData = {
    name: '',
    category: '',
    ratings: '1',
    property: 'all',
  }

  public static readonly all: string = 'all';
  public static readonly my: string = 'my';


  @ViewChild('drawer') drawer: any;

  constructor(readonly clientRoutes: ClientRoutesService, private _elementRef: ElementRef) {
  }

  public async drawerToggle(): Promise<void> {
    await this.drawer;
    this.drawer.toggle();

    let openButton: HTMLElement | null = document.getElementById("navigation_open_button");
    let closeButton: HTMLElement | null = document.getElementById("navigation_close_button");

    let block: string = "block";
    let none: string = "none";

    if (openButton && closeButton) {
      if (this.drawer.opened) {
        openButton.style.display = none;
        closeButton.style.display = block;
      } else {
        openButton.style.display = block;
        closeButton.style.display = none;
      }
    }
  }

  async ngAfterViewInit(): Promise<void> {

    await this.drawerToggle();

    MainComponent.setCSSVariablesOnResize();
    window.onresize = MainComponent.setCSSVariablesOnResize;
  }


  public static setCSSVariablesOnResize(): void {

    let drawerContentHeight: number = getDrawerContentHeight();
    let drawerContentHeaderHeight: number = getDrawerContentHeaderHeight();

    if (drawerContentHeight >= 0 && drawerContentHeaderHeight >= 0) {
      setQuizzesListHeight();
      setSolveQuizHeight();
    }

    function setQuizzesListHeight(): void {

      let appHomeHeader: HTMLElement | null = document.getElementById("app_home_header");
      let appHomeContent: HTMLElement | null = document.getElementById("app_home_content");

      if (appHomeHeader && appHomeContent) {
        let height: number = (drawerContentHeight - drawerContentHeaderHeight - appHomeHeader.offsetHeight) || 0;
        setCssVariable('--quizzes-list-height', height + "px");
      }
    }

    function setSolveQuizHeight(): void {
      let solveQuizCard: HTMLElement | null = document.getElementById("solve_quiz");

      if (solveQuizCard) {
        let height: number = (drawerContentHeight - drawerContentHeaderHeight) || 0;
        setCssVariable('--solve-quiz-height', height + "px");
      }
    }

    function getDrawerContentHeaderHeight(): number {
      let htmlElement: HTMLElement | null = document.getElementById("drawer_content_header");
      return htmlElement ? htmlElement.offsetHeight : -1;
    }

    function getDrawerContentHeight(): number {
      let htmlElement: HTMLElement | null = document.getElementById("drawer_content");
      return htmlElement ? htmlElement.clientHeight : -1;
    }

    function setCssVariable(cssVarName: string, value: string | number): void {
      let styleVariables: any = document.querySelector(':root');
      if (styleVariables) {
        styleVariables.style.setProperty(cssVarName, value);
      }
    }
  }
}
