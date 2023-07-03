import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {MatButtonToggleChange} from "@angular/material/button-toggle";
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {FilterDialogComponent} from "./Filter Dialog/filter-dialog.component";
import {ClientRoutesService} from "../../../Shared/Services/Client Routes/client-routes.service";
import {MainComponent} from "../../Router Main/main.component";
import {MatTable} from "@angular/material/table";

export interface FilterQuizzesData {
  name: string;
  category: string;
  ratings: string;
  property: string;
}

export interface PeriodicElement {
  name: string | null;
  position: string | null | number;
  weight: number | null;
  symbol: string | null;
}


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['../../Navigation/navigation.component.scss', './home.component.scss']
})
export class HomeComponent implements OnInit, AfterViewInit {

  readonly all: string = MainComponent.all;
  readonly my: string = MainComponent.my;

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
  dataSource: PeriodicElement[] = this.ELEMENT_DATA;

  // @ts-ignore
  @ViewChild(MatTable) table: MatTable<PeriodicElement>;


  constructor(private _elementRef: ElementRef, private clientRoutes: ClientRoutesService, private _dialog: MatDialog) {
  }

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
    MainComponent.setCSSVariablesOnResize();
  }


  public toggle(matButtonToggleChange: MatButtonToggleChange): void {
    if (matButtonToggleChange.value == MainComponent.all) {
      MainComponent.filteringQuizzesData.property = MainComponent.all;
    } else if (matButtonToggleChange.value == MainComponent.my) {
      MainComponent.filteringQuizzesData.property = MainComponent.my;
    }
  }

  public defaultToggled(value: string): boolean {
    return value == MainComponent.filteringQuizzesData.property;
  }

  public openDialog(): void {

    const dialogRef: MatDialogRef<FilterDialogComponent> = this._dialog.open(FilterDialogComponent, {
      maxWidth: '20cm',
      data: MainComponent.filteringQuizzesData
    });

    dialogRef.afterClosed().subscribe((result: FilterQuizzesData): void => {

      console.log('The dialog was closed with result: ');
      if (result) {
        result.property = MainComponent.filteringQuizzesData.property
        MainComponent.filteringQuizzesData = result;
      }
      console.dir(result);
    });
  }


  public deleteQuiz(event: any, element: any): void {

    console.log("Delete quiz clicked");
    console.dir(event);
    console.dir(element);

    this.ELEMENT_DATA.splice(this.ELEMENT_DATA.indexOf(element), 1);

    this.table.renderRows();
  }

  public solveQuiz(event: any, element: any): void {
    console.log("Solve quiz clicked");
    console.dir(event);
    console.dir(element);

    this.clientRoutes.navigateToDashboardSolveQuiz('123e4567-e89b-12d3-a456-426614174000');
  }
}
