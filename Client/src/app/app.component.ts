import {AfterViewInit, Component, ElementRef} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements AfterViewInit{
  title = 'Quiz';

  constructor(private _elementRef:ElementRef) {
  }

  ngAfterViewInit(): void {

  }
}
