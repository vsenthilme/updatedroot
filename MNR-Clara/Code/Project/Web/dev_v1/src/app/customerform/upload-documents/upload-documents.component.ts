import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { CommonApiService } from 'src/app/common-service/common-api.service';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { InquiresService } from 'src/app/main-module/crm/inquiries/inquires.service';
import { IntakeService } from 'src/app/main-module/crm/intake-snap-main/intake.service';
import { environment } from 'src/environments/environment';
import { EnglishService } from '../english/english.service';
import { FormService } from '../form.service';
import { BehaviorSubject } from 'rxjs';
import { MatterExpensesService } from 'src/app/main-module/matters/case-management/expenses/matter-expenses.service';
import { Location } from '@angular/common';
import { DomSanitizer } from '@angular/platform-browser';
import { MatterDocumetService } from 'src/app/main-module/matters/case-management/matter-document/matter-documet.service';
@Component({
  selector: 'app-upload-documents',
  templateUrl: './upload-documents.component.html',
  styleUrls: ['./upload-documents.component.scss'],
})
export class UploadDocumentsComponent implements OnInit {
  constructor(
    private fb: FormBuilder,
    private cs: CommonService,
    private route: ActivatedRoute,
    private service: InquiresService,
    public downloadService: MatterDocumetService,
    private uploadService: MatterExpensesService,
    private router: Router,
    private spin: NgxSpinnerService,
    public toastr: ToastrService,
    private http: HttpClient,
    private sanitizer: DomSanitizer,
  ) {}

  FormLine = this.fb.group({
    inquiryId: [],
    id: [],
    fileName: [],
    statusId: [],
    uploadedBy: [],
    uploadedOn: [],
  });

  rows: FormArray = this.fb.array([]);

  form = this.fb.group({
    formLine: this.rows,
  });

  displayedColumns = ['chooseFile', 'status'];
  dataSource = new BehaviorSubject<AbstractControl[]>([]);
  code: any;

  enablescreen = false;
  ngOnInit(): void {
    let code = this.route.snapshot.params.code;
    let js = this.cs.decrypt(code);
    this.code = this.cs.decrypt(code);
    this.spin.show();
    this.http
      .get<any>(
        `/mnr-setup-service/login?userId=` +
          environment.hyperlink_userid +
          `&password=` +
          environment.hyperlink_pasword
      )
      .subscribe(
        (res) => {
          this.spin.hide();
          this.enablescreen = true;
          let menu = [1000, 1001, 1004, 2101, 2102, 2202, 2203];
          sessionStorage.setItem('menu', menu.toString());
          sessionStorage.setItem('user', JSON.stringify(res));
          this.onload(js);
        },
        (rej) => {
          this.spin.hide();
          this.toastr.error('', rej);
        }
      );
  }

  onload(js: any) {}

  delete(element, index) {
    this.service.DeleteuploadInquiry(element.controls.inquiryId.value, element.controls.id.value).subscribe((res) => {
      this.toastr.success('File deleted successfully.', 'Notification', {
        timeOut: 2000,
        progressBar: false,
      });
      this.removerow(index);
    });
  }

  removerow(x: any) {
    this.rows.removeAt(x);
    this.reset();
    this.dataSource.next(this.rows.controls);
  }
  reset() {
    let i = 0;

    this.rows.value.forEach((ro: any) => {
      ro.id = ++i;
    });
    this.rows.patchValue(this.rows.value);
  }

  fileUpload: any[] = [];
  file: File;
  onFilechange(event: any, element: any) {
    this.file = event.target.files[0];
    element.controls.fileName.patchValue(this.file.name);
    element.controls.statusId.patchValue(1);
    element.controls.inquiryId.patchValue(this.code.inquiryNumber);
    this.fileUpload.push(this.file);
    this.spin.show();
    this.uploadService.uploadfile(this.file, `inquiry/${this.code.inquiryNumber}`).subscribe(
      (resp: any) => {
        this.toastr.success('File uploaded successfully.', 'Notification', {
          timeOut: 2000,
          progressBar: false,
        });
        element.controls.fileName.patchValue(resp.file);
        element.controls.statusId.patchValue(1);
        element.controls.inquiryId.patchValue(this.code.inquiryNumber);

        this.spin.hide();
      },
      (err) => {
        this.cs.commonerror(err);
        this.spin.hide();
      }
    );
  }

  addRow(d?: any, noUpdate?: boolean) {
    const row = this.fb.group({
      inquiryId: [],
      file: [],
      id: [0],
      fileName: [],
      statusId: [],
      uploadedBy: [],
      uploadedOn: [],
    });
    if (d) row.patchValue(d);

    this.rows.push(row);
    if (!noUpdate) {
      this.updateView();
    }
  }
  updateView() {
    this.dataSource.next(this.rows.controls);
  }

  submit(){
    this.spin.show();
    this.service.CreateuploadInquiry(this.form.getRawValue().formLine).subscribe(res => {
      this.toastr.success("File deleted successfully.", "Notification", {
        timeOut: 2000,
        progressBar: false,
      });
      this.router.navigate(['/mr/uploadThanks']);
      this.spin.hide();
    })
  }

  fileUrldownload: any;
  docurl: any;
  async download(element) {
    this.spin.show()
    const blob = await this.downloadService.getfile(`inquiry/${this.code.inquiryNumber}`, element.controls.fileName.value)
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
      a.download = element.controls.fileName.value;
      a.click();
      URL.revokeObjectURL(this.docurl);

    }
    this.spin.hide();
  }
}
