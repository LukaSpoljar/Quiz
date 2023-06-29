import {AfterViewInit, Component} from '@angular/core';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements AfterViewInit{

  ngAfterViewInit(): void {

    setHeaderElementHeight();
    window.onresize = (event: UIEvent): void => { setHeaderElementHeight(); };

    function setHeaderElementHeight(): void {
      let styleVariables: any = document.querySelector(':root');
      if (styleVariables) {
        styleVariables.style.setProperty('--my-element-height', document.getElementsByTagName('header')[0].clientHeight + "px");
      }
    }
  }
}
