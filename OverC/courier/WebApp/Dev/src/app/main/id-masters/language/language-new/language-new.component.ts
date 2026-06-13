import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonServiceService } from '../../../../common-service/common-service.service';
import { PathNameService } from '../../../../common-service/path-name.service';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { LanguageService } from '../language.service';
import { MessageService } from 'primeng/api';
import { NgxSpinnerService } from 'ngx-spinner';
import { NumberrangeService } from '../../numberrange/numberrange.service';

@Component({
  selector: 'app-language-new',
  templateUrl: './language-new.component.html',
  styleUrl: './language-new.component.scss',
})
export class LanguageNewComponent {
  active: number | undefined = 0;

  constructor(private cs: CommonServiceService, 
    private spin: NgxSpinnerService,
    private route: ActivatedRoute, 
    private router: Router, 
    private path: PathNameService, 
    private fb: FormBuilder,
    private service: LanguageService,
    private numberRangeService: NumberrangeService, 
    private messageService: MessageService,) { }

  pageToken: any;

  //form builder initialize
  form = this.fb.group({
    languageId: [, [Validators.required, Validators.maxLength(50)]],
    languageDescription: [, [Validators.required, Validators.maxLength(100)]],
    referenceField1: [],
    referenceField2: [],
    referenceField3: [],
    referenceField4: [],
    referenceField5: [],
    referenceField6: [],
    referenceField7: [],
    referenceField8: [],
    referenceField9: [],
    referenceField10: [],
    createdOn: ['',],
    createdBy: [],
    updatedBy: [],
    updatedOn: ['',],
  });

  numCondition: any;
  submitted = false;
  email = new FormControl('', [Validators.required, Validators.email]);
  errorHandling(control: string, error: string = "required") {
    const controlInstance = this.form.get(control);
    return controlInstance && controlInstance.hasError(error) && this.submitted;
  }
  getErrorMessage() {
    if (this.email.hasError('required')) {
      return ' Field should not be blank';
    }
    return this.email.hasError('email') ? 'Not a valid email' : '';
  }

  btnText = 'Save'
  nextNumber: any;

  ngOnInit() {


    let code = this.route.snapshot.params['code'];
    this.pageToken = this.cs.decrypt(code);
    const dataToSend = ['Setup', 'Language', this.pageToken.pageflow];
    this.path.setData(dataToSend);

    if (this.pageToken.pageflow != 'New') {
      this.btnText = 'Update';
      this.fill(this.pageToken.line)
    } else {
      this.spin.show();
      let obj: any = {};
      obj.numberRangeObject = ['LANGUAGE'];
      this.numberRangeService.search(obj).subscribe({
        next: (res: any) => {
          if (res.length > 0) {
            this.nextNumber = Number(res[0].numberRangeCurrent) + 1;
            this.form.controls.languageId.patchValue(this.nextNumber);
            this.numCondition = 'true';
            this.form.controls.referenceField10.patchValue(this.numCondition);
            this.form.controls.languageId.disable();
          }

          this.spin.hide();
        },
        error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        },
      });
    }
  }

  fill(line: any) {
    this.form.patchValue(line);
    this.form.controls.updatedOn.patchValue(this.cs.dateExcel(this.form.controls.updatedOn.value));
    this.form.controls.createdOn.patchValue(this.cs.dateExcel(this.form.controls.createdOn.value));
    this.form.controls.languageId.disable();
  }

  save() {
    this.submitted = true;
    if (this.form.invalid) {
      this.messageService.add({ severity: 'error', summary: 'Error', key: 'br', detail: 'Please fill required fields to continue' });
      return;
    }

    if (this.pageToken.pageflow != 'New') {
      this.spin.show()
      this.service.Update(this.form.getRawValue()).subscribe({
        next: (res) => {
          this.messageService.add({ severity: 'success', summary: 'Updated', key: 'br', detail: res.languageId + ' has been updated successfully' });
          this.router.navigate(['/main/idMaster/language']);
          this.spin.hide();
        }, error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      })
    } else {
      this.spin.show()
      this.service.Create(this.form.getRawValue()).subscribe({
        next: (res) => {
          if (res) {
            this.messageService.add({ severity: 'success', summary: 'Created', key: 'br', detail: res.languageId + ' has been created successfully' });
            this.router.navigate(['/main/idMaster/language']);
            this.spin.hide();
          }
        }, error: (err) => {
          this.spin.hide();
          this.cs.commonerrorNew(err);
        }
      })
    }
  }
}
