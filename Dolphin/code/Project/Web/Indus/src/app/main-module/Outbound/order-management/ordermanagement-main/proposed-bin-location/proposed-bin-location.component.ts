import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import {NgxSpinnerService } from 'ngx-spinner';



import { animate, state, style, transition, trigger } from '@angular/animations';
import { ToastrService } from 'ngx-toastr';
import { RejectedReasonComponent } from './rejected-reason/rejected-reason.component';
import { Router } from '@angular/router';
import { CommonService } from 'src/app/common-service/common-service.service';

@Component({
  selector: 'app-putaway-proposed',
  templateUrl: './proposed-bin-location.component.html',
  styleUrls: ['./proposed-bin-location.component.scss']
})
export class ProposedBinLocationComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<ProposedBinLocationComponent>, public dialog: MatDialog,
    public router: Router, private cs: CommonService,
    @Inject(MAT_DIALOG_DATA) public data: any, private spin: NgxSpinnerService, public toastr: ToastrService,) { }

  showAI = false;  

  ngOnInit() {
    
    setTimeout(() => {
      this.toastr.success("System proposed FIFO strategy", "Strategy", {
        timeOut: 1000,
         progressBar: false,
       });
    }, 4000);
    

    
    setTimeout(() => {
      this.toastr.success("System proposed A1-030-32A", "Propsed Bins", {
        timeOut: 1000,
         progressBar: false,
       });
    }, 7000);

    
    setTimeout(() => {
      this.showAI = true;
    }, 9000);

    
    setTimeout(() => {
      this.toastr.success("System proposed FIFO strategy", "Strategy", {
        timeOut: 1000,
         progressBar: false,
       });
    }, 12000);

    
    setTimeout(() => {      
      this.toastr.error("Aisle Blocked", "Overwritten", {
        timeOut: 1000,
         progressBar: false,
       });
    }, 15000);
    
    setTimeout(() => {      
      this.toastr.error("More Picking Time", "Overwritten", {
        timeOut: 1000,
         progressBar: false,
       });
    }, 18000);

        
    setTimeout(() => {
      this.toastr.success("System proposed A1-030-32A", "Propsed Bins", {
        timeOut: 1800,
         progressBar: false,
       });
    }, 20000);
  }

  aisleBlocked(){
      this.spin.show()
      setTimeout(() => {
       this.spin.hide();
        const dialogRef = this.dialog.open(RejectedReasonComponent, {
          disableClose: true,
          width: '40%',
          maxWidth: '80%',
          position: { top: '9%', left: '68%'},
        });
    
        dialogRef.afterClosed().subscribe(result => {
          console.log('The dialog was closed');
        });
      }, 2000);
  
  }


  routetoDashboard(bin, type){
    //this.dialogRef.close()
    let paramdata = this.cs.encrypt({ code: bin, type: type, line: this.data});
    const url = this.router.serializeUrl(
      this.router.createUrlTree(['/main/dashboard/warehousemonitor/' + paramdata])      
    );  
    window.open('#' + url, '_blank');
  }
  }