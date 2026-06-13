import { Component, Inject } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { MessageService } from 'primeng/api';
import { CommonAPIService } from '../../../../../common-service/common-api.service';
import { CommonServiceService } from '../../../../../common-service/common-service.service';
import { PathNameService } from '../../../../../common-service/path-name.service';
import { AuthService } from '../../../../../core/core';
import { ConsoleBulkComponent } from '../../console-bulk/console-bulk.component';
import { ConsoleService } from '../../console.service';

@Component({
  selector: 'app-dynamic-field-selection',
  templateUrl: './dynamic-field-selection.component.html',
  styleUrl: './dynamic-field-selection.component.scss'
})
export class DynamicFieldSelectionComponent {

  status: any[] = [];
  incoTerms: any[] = [];
  constructor(
    public dialogRef: MatDialogRef<ConsoleBulkComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private cs: CommonServiceService,
    private spin: NgxSpinnerService,
    private route: ActivatedRoute,
    private router: Router,
    private path: PathNameService,
    private fb: FormBuilder,
    private service: ConsoleService,
    private messageService: MessageService,
    private cas: CommonAPIService,
    private auth: AuthService,
  ) {
    this.incoTerms = [
      { value: 'DDU', label: 'DDU' },
      { value: 'DDU', label: 'DDU' }
    ];

  }

  // form builder initialize
  form = this.fb.group({
    hubCode: [],
    location: [],
  });

  hubList: any[] = [];
  dropdownlist() {
    this.spin.show();
    this.cas.getalldropdownlist([
      this.cas.dropdownlist.setup.hub.url,
    ]).subscribe({
      next: (results: any) => {
        this.hubList = this.cas.forLanguageFilter(results[0], this.cas.dropdownlist.setup.hub.key);
        this.spin.hide();
      },
      error: (err: any) => {
        this.spin.hide();
        this.cs.commonerrorNew(err);
      },
    });

  }
  ngOnInit(): void {
    this.dropdownlist();
  }
  showHub = false;

  saveHubCode() {
    this.dialogRef.close(this.form.controls.hubCode.value);
  }

  saveLocation() {
    this.dialogRef.close(this.form.controls.location.value);
  }


}