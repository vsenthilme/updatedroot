import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-boxdetails',
  templateUrl: './boxdetails.component.html',
  styleUrls: ['./boxdetails.component.scss']
})
export class BoxdetailsComponent implements OnInit {

  constructor(private spinner: NgxSpinnerService, @Inject(MAT_DIALOG_DATA) public data: any,) { }


    ngOnInit(): void {
      
      this.spinner.show();
      setTimeout(() => {
        this.spinner.hide();
      }, 1000);
    }


}
