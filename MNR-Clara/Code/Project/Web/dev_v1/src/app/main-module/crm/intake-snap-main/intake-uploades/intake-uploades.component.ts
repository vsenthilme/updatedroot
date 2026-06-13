import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { InquiresService } from '../../inquiries/inquires.service';
import { Router } from '@angular/router';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatterDocumetService } from 'src/app/main-module/matters/case-management/matter-document/matter-documet.service';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { AbstractControl } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { DomSanitizer } from '@angular/platform-browser';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { BehaviorSubject } from 'rxjs';
import { CommonApiService } from 'src/app/common-service/common-api.service';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-intake-uploades',
  templateUrl: './intake-uploades.component.html',
  styleUrls: ['./intake-uploades.component.scss']
})
export class IntakeUploadesComponent implements OnInit {

 
  ELEMENT_DATA: any[] = [];
  constructor(public dialogRef: MatDialogRef<any>, public dialog: MatDialog, private cas: CommonApiService, private auth: AuthService, private cs: CommonService,
    private router: Router, private service: InquiresService, private sanitizer: DomSanitizer,
    private spin: NgxSpinnerService, public downloadService: MatterDocumetService, public toastr: ToastrService, @Inject(MAT_DIALOG_DATA) public data: any,
    private http: HttpClient,) { }






  ngOnInit(): void {
        this.service.GetUploadInquiry(this.data.code.inquiryNumber).subscribe(res =>{
          this.dataSource = new MatTableDataSource<any>(res);
        })
  }
  @ViewChild(MatSort)
  sort: MatSort;
  @ViewChild(MatPaginator)
  paginator: MatPaginator; // Pagination



  displayedColumns = [ 'fileName', 'status'];
  dataSource = new MatTableDataSource<any>([]);

  fileUrldownload: any;
  docurl: any;
  async download(element) {
    this.spin.show()
    const blob = await this.downloadService.getfile(`inquiry/${element.inquiryId}`, element.fileName)
      .catch((err: HttpErrorResponse) => {
        this.cs.commonerror(err);
      });
    this.spin.hide();
    if (blob) {
      const blobOb = new Blob([blob], {
        type: "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
      });
      this.fileUrldownload = this.sanitizer.bypassSecurityTrustResourceUrl(window.URL.createObjectURL(blobOb));
      this.docurl = window.URL.createObjectURL(blob);
      const a = document.createElement('a')
      a.href = this.docurl
      a.download = element.fileName;
      a.click();
      URL.revokeObjectURL(this.docurl);

    }
    this.spin.hide();
  }
}
