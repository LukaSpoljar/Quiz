import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {AbstractControl, FormControl, FormGroup} from "@angular/forms";
import {map, Observable, startWith} from "rxjs";
import {FilterQuizzesData} from "../home.component";

@Component({
  selector: 'app-filter-dialog',
  templateUrl: './filter-dialog.component.html',
  styleUrls: ['../../Shared/Style/Dialogs.scss', './filter-dialog.component.scss']
})
export class FilterDialogComponent implements OnInit {

  readonly filterForm: FormGroup;

  options: any[] = [{name: 'Mary'}, {name: 'Shelley'}, {name: 'Igor'}, {name: 'Iva'}];
  filteredOptions: Observable<any[]> = new Observable<any[]>();

  constructor(public dialogRef: MatDialogRef<FilterDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: FilterQuizzesData) {

    this.filterForm = new FormGroup({
      name: new FormControl(data.name),
      category: new FormControl(data.category),
      ratings: new FormControl(this.data.ratings)
    });
  }

  ngOnInit(): void {

    let categoryControl: AbstractControl = this.filterForm.controls['category'];
    let ratingsControl: AbstractControl = this.filterForm.controls['ratings'];

    /* List of categories */
    this.filteredOptions = categoryControl.valueChanges.pipe(
      startWith(ratingsControl.value),
      map((value: any): any => {
        const name = typeof value === 'string' ? value : value?.name;
        return name ? this._filter(name as string) : this.options.slice();
      })
    );

    /* Assign newest form values into data object*/
    this.filterForm.valueChanges.subscribe((value: any): void => {
      this.data = this.filterForm.getRawValue();
    });
  }

  displayFn(valueInField: any): string {
    if (typeof valueInField === "string") {
      return valueInField;
    }
    return valueInField && valueInField.name ? valueInField.name : '';
  }

  private _filter(name: string): any[] {
    const filterValue: string = name.toLowerCase();
    return this.options.filter(option => option.name.toLowerCase().includes(filterValue));
  }
}
