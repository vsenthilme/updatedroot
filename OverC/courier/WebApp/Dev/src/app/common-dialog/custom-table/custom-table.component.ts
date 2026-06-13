import { ChangeDetectorRef, Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-custom-table',
  templateUrl: './custom-table.component.html',
  styleUrl: './custom-table.component.scss'
})
export class CustomTableComponent {

  sourceProducts!: any[];

  targetProducts!: any[];

  constructor(private cdr: ChangeDetectorRef, public dialogRef: MatDialogRef<CustomTableComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) {}
  
  ngOnInit() {    
    this.sourceProducts = this.data.source;
    this.targetProducts = this.data.target;
}
}
