import {Component} from '@angular/core';
import {MatButtonToggleChange} from "@angular/material/button-toggle";

export interface PeriodicElement {
  name: string;
  position: number;
  weight: number;
  symbol: string;
}

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['../../../Navigation/navigation.component.scss', './home.component.scss']
})
export class HomeComponent {


  public ELEMENT_DATA: PeriodicElement[] = [
    {position: 1, name: 'Hydrogen', weight: 1.0079, symbol: 'H'},
    {position: 2, name: 'Helium', weight: 4.0026, symbol: 'He'},
    {position: 3, name: 'Lithium', weight: 6.941, symbol: 'Li'},
    {position: 4, name: 'Beryllium', weight: 9.0122, symbol: 'Be'},
    {position: 5, name: 'Boron', weight: 10.811, symbol: 'B'},
    {position: 6, name: 'Carbon', weight: 12.0107, symbol: 'C'},
    {position: 7, name: 'Nitrogen', weight: 14.0067, symbol: 'N'},
    {position: 8, name: 'Oxygen', weight: 15.9994, symbol: 'O'},
    {position: 9, name: 'Fluorine', weight: 18.9984, symbol: 'F'},
    {position: 10, name: 'Neon', weight: 20.1797, symbol: 'Ne'},
  ];

  displayedColumns: string[] = ['position', 'name', 'category', 'ratings', 'delete', 'solve'];
  dataSource = this.ELEMENT_DATA;


  constructor() {
  }

  public toggle(matButtonToggleChange: MatButtonToggleChange): void {

    if (matButtonToggleChange.value == "all") {

    } else if (matButtonToggleChange.value == "my") {

    } else if (matButtonToggleChange.value == "filter") {

    }

  }
}
