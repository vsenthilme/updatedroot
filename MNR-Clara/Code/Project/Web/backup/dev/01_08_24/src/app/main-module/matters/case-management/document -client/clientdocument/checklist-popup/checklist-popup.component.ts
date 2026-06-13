import { SelectionModel } from '@angular/cdk/collections';
import { Component, Inject, OnInit } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Validators } from 'ngx-editor';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { BehaviorSubject, Subscription } from 'rxjs';
import { CommonApiService } from 'src/app/common-service/common-api.service';
import { CommonService } from 'src/app/common-service/common-service.service';
import { documentTemplateService } from 'src/app/main-module/setting/business/document-template/document-template.service';
import { GeneralMatterService } from '../../../General/general-matter.service';
import { CustomChecklistComponent } from '../custom-checklist/custom-checklist.component';

interface SelectItem {
  id: string;
  itemName: string;
}


@Component({
  selector: 'app-checklist-popup',
  templateUrl: './checklist-popup.component.html',
  styleUrls: ['./checklist-popup.component.scss']
})
export class ChecklistPopupComponent implements OnInit {

  code: any = this.cs.decrypt(sessionStorage.getItem('matter')).code;

  sub = new Subscription();

  MatterDocLists = this.fb.group({
    caseCategoryId: [{ value: '', disabled: true }],
    caseSubCategoryId: [{ value: '', disabled: true }],
    checkListNo: [{ value: '', disabled: true }],
    classId: [{ value: '', disabled: true }],
    clientId: [{ value: '', disabled: true }],
    deletionIndicator: [{ value: '', disabled: true }],
    documentName: [{ value: '', disabled: true }],
    documentUrl: [{ value: '', disabled: true }],
    languageId: [{ value: '', disabled: true }],
    matterNumber: [{ value: '', disabled: true }],
    matterText: [{ value: '', disabled: true }],
    referenceField1: [{ value: '', disabled: true }],
    referenceField2: [{ value: '', disabled: true }],
    referenceField3: [{ value: '', disabled: true }],
    referenceField4: [{ value: '', disabled: true }],
    referenceField5: [{ value: '', disabled: true }],
    sequenceNumber: [{ value: '', disabled: true }],
    statusId: [{ value: '', disabled: true }],
  });

  rows: FormArray = new FormArray([]);

  form = this.fb.group({

    // checkListNo: [{ value: '', disabled: true }],
    // checkListNoFE: [{ value: '', disabled: false }],
    // caseCategoryId: [{ value: '', disabled: true }],
    // caseSubCategoryId: [{ value: '', disabled: true }],
    // caseCategoryName: [{ value: '', disabled: true }],
    // caseSubCategoryName: [{ value: '', disabled: true }],
    // matterNumber: [{ value: '', disabled: true }],
    // matterDescription: [{ value: '', disabled: true }],
    // classId: [{ value: '', disabled: true }],
    // clientId: [{ value: '', disabled: true }],
    // statusId: [{ value: '', disabled: true }],
    // languageId:['EN']

    approvedDate: [{ value: '', disabled: true }],
    caseCategoryId: [{ value: '', disabled: true }],
    caseSubCategoryId: [{ value: '', disabled: true }],
    caseCategoryName: [{ value: '', disabled: true }],
    caseSubCategoryName: [{ value: '', disabled: true }],
    checkListNo: [{ value: '', disabled: true }],
    classId: [{ value: '', disabled: true }],
    clientId: [{ value: '', disabled: true }],
    createdBy: [{ value: '', disabled: true }],
    createdOn: [{ value: '', disabled: true }],
    deletionIndicator: [{ value: '', disabled: true }],
    languageId: [{ value: '', disabled: true }],
    matterDocLists: this.rows,
    matterNumber: [{ value: '', disabled: true }],
    matterText: [{ value: '', disabled: true }],
    receivedDate: [{ value: '', disabled: true }],
    receivedOn: [{ value: '', disabled: true }],
    referenceField1: [{ value: '', disabled: true }],
    referenceField10: [{ value: '', disabled: true }],
    referenceField2: [{ value: '', disabled: true }],
    referenceField3: [{ value: '', disabled: true }],
    referenceField4: [{ value: '', disabled: true }],
    referenceField5: [{ value: '', disabled: true }],
    referenceField6: [{ value: '', disabled: true }],
    referenceField7: [{ value: '', disabled: true }],
    referenceField8: [{ value: '', disabled: true }],
    referenceField9: [{ value: '', disabled: true }],
    resentDate: [{ value: '', disabled: true }],
    sentBy: [{ value: '', disabled: true }],
    sentDate: [{ value: '', disabled: true }],
    // statusId: [{ value: '', disabled: true }],
    updatedBy: [{ value: '', disabled: true }],
    updatedOn: [{ value: '', disabled: true }],

  });
  matter: string;

  constructor(
    public dialogRef: MatDialogRef<ChecklistPopupComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private cs: CommonService,
    private service: GeneralMatterService,
    public toastr: ToastrService,
    private spin: NgxSpinnerService,
    public fb: FormBuilder,
    private documentTemplateService: documentTemplateService,
    private cas: CommonApiService,
    public dialog: MatDialog,
  ) { }

  ngOnInit(): void {

    if (this.data.matterNumber) {
      this.matter = '' + this.data.matterNumber + ' / ' + this.data.matterdesc + ' - ';
      this.form.controls.matterNumber.patchValue(this.data.matter);
      this.form.controls.matterText.patchValue(this.data.matterdesc);
    }

    if (this.data.pageflow == 'New') {
      this.getMatterDetails();
    } else {
      this.fill()
    }
  }
  disabled = false;
  step = 0;

  setStep(index: number) {
    this.step = index;
  }

  nextStep() {
    this.step++;
  }

  prevStep() {
    this.step--;
  }

  panelOpenState = false;
  displayedColumns: string[] = ['sino', 'documentName', 'checkListTemplate', 'delete'];
  dataSource = new MatTableDataSource<any>([]);
  selection = new SelectionModel<any>(true, []);


  selectedItems: SelectItem[] = [];
  multiselectchecklistnoList: SelectItem[] = [];
  multichecklistnoList: SelectItem[] = [];


  dropdownSettings = {
    singleSelection: true,
    text: "Select",
    selectAllText: 'Select All',
    unSelectAllText: 'UnSelect All',
    enableSearchFilter: true,
    badgeShowLimit: 2,
    disabled: false
  };


  fill() {

    let caseCategoryIdList: any[] = [];
    let caseSubCategoryIdList: any[] = [];
    let checklistnoList: any[] = [];
    this.spin.show();
    this.cas.getalldropdownlist([
      this.cas.dropdownlist.setup.caseCategoryId.url,
      this.cas.dropdownlist.setup.caseSubcategoryId.url,
      this.cas.dropdownlist.setup.checklist.url
    ]).subscribe((results) => {
      caseCategoryIdList = this.cas.foreachlist_searchpage(results[0], this.cas.dropdownlist.setup.caseCategoryId.key);
      caseSubCategoryIdList = this.cas.foreachlist_searchpage(results[1], this.cas.dropdownlist.setup.caseSubcategoryId.key);
      checklistnoList = this.cas.foreachlist_searchpage(results[2], this.cas.dropdownlist.setup.checklist.key);
      checklistnoList.forEach((x: { key: string; value: string; }) => this.multichecklistnoList.push({ id: x.key, itemName: x.value }))
      this.multiselectchecklistnoList = this.multichecklistnoList;
      this.spin.hide();
      this.documentTemplateService.SearchCheckListHeader({ matterHeaderId: [this.data.data.matterHeaderId] }).subscribe(res => {
        if (res.length > 0) {
          this.form.patchValue(res[0], { emitEvent: false });

          this.form.controls.caseCategoryName.setValue(caseCategoryIdList.find(y => y.key == this.form.controls.caseCategoryId.value)?.value);
          this.form.controls.caseSubCategoryName.setValue(caseSubCategoryIdList.find(y => y.key == this.form.controls.caseSubCategoryId.value)?.value);


          if (this.data.matterNumber) {
            this.form.controls.matterText.patchValue(this.data.matterdesc);
          }


          this.dataSource = new MatTableDataSource<any>(res[0].matterDocLists);
        }
      })

    })

  }


  getMatterDetails() {
    let caseCategoryIdList: any[] = [];
    let caseSubCategoryIdList: any[] = [];
    let checklistnoList: any[] = [];
    this.spin.show();
    this.cas.getalldropdownlist([
      this.cas.dropdownlist.setup.caseCategoryId.url,
      this.cas.dropdownlist.setup.caseSubcategoryId.url,
      this.cas.dropdownlist.setup.checklist.url
    ]).subscribe((results) => {
      caseCategoryIdList = this.cas.foreachlist_searchpage(results[0], this.cas.dropdownlist.setup.caseCategoryId.key);
      caseSubCategoryIdList = this.cas.foreachlist_searchpage(results[1], this.cas.dropdownlist.setup.caseSubcategoryId.key);
      checklistnoList = this.cas.foreachlist_searchpage(results[2], this.cas.dropdownlist.setup.checklist.key);
      checklistnoList.forEach((x: { key: string; value: string; }) => this.multichecklistnoList.push({ id: x.key, itemName: x.value }))
      this.multiselectchecklistnoList = this.multichecklistnoList;

      this.spin.hide();
      this.sub.add(this.service.Get(this.code).subscribe(res => {
        this.form.patchValue(res, { emitEvent: false });


        let docArray: any[] = [];
        this.sub.add(this.documentTemplateService.getChecklistDocument().subscribe(docData => {
          if (docData.length > 0) {
            docData.forEach((element: any) => {
              if (element.caseCategoryId == this.form.controls.caseCategoryId.value && element.caseSubCategoryId == this.form.controls.caseSubCategoryId.value) {
                element.statusId = 22;
                docArray.push(element);
              }
            })
          }
          if (docArray.length > 0) {
            this.form.controls.checkListNo.setValue(docArray[0].checkListNo);
            //   this.form.controls.checkListNoFE.patchValue([this.form.controls.checkListNo.value]);
            this.multichecklistnoList.forEach(element => {
              if (element.id == docArray[0].checkListNo) {
                this.form.controls.checkListNo.patchValue(element.id);
              }
            });
            this.dataSource = new MatTableDataSource<any>(docArray);
          }
          this.spin.hide();
          this.form.controls.caseCategoryName.setValue(caseCategoryIdList.find(y => y.key == this.form.controls.caseCategoryId.value)?.value);
          this.form.controls.caseSubCategoryName.setValue(caseSubCategoryIdList.find(y => y.key == this.form.controls.caseSubCategoryId.value)?.value);
        }));
        this.spin.hide();

      }, err => {
        this.cs.commonerror(err);
        this.spin.hide();
      }));
    }, (err) => {
      this.toastr.error(err, "");
    });
    this.spin.hide();
  }
  statusId = 22

  addRow(d?: any, noUpdate?: boolean, index?: any) {
    const row = this.fb.group({
      caseCategoryId: [{ value: '', disabled: true }],
      caseSubCategoryId: [{ value: '', disabled: true }],
      checkListNo: [{ value: '', disabled: true }],
      classId: [{ value: '', disabled: true }],
      clientId: [{ value: this.form.controls.clientId.value, disabled: true }],
      createdBy: [{ value: '', disabled: true }],
      createdOn: [{ value: '', disabled: true }],
      deletionIndicator: [{ value: '', disabled: true }],
      documentName: [{ value: '', disabled: true }],
      documentUrl: [{ value: '', disabled: true }],
      languageId: [{ value: '', disabled: true }],
      matterHeaderId: [{ value: '', disabled: true }],
      matterLineId: [{ value: '', disabled: true }],
      matterNumber: [{ value: this.form.controls.matterNumber.value, disabled: true }],
      matterText: [{ value: '', disabled: true }],
      referenceField1: [{ value: '', disabled: true }],
      referenceField2: [{ value: '', disabled: true }],
      referenceField3: [{ value: '', disabled: true }],
      referenceField4: [{ value: '', disabled: true }],
      referenceField5: [{ value: '', disabled: true }],
      sequenceNumber: [{ value: index, disabled: true }],
      statusId: [{ value: this.statusId, disabled: true }],
      updatedBy: [{ value: '', disabled: true }],
      updatedOn: [{ value: '', disabled: true }],
    });
    if (d) {
      row.patchValue(d);
    }
    this.rows.push(row);
    if (!noUpdate) {
      // this.updateView();
    }
  }



  saveMatterChecklist() {
    console.log(this.form)
    this.dataSource.data.forEach((d: any, index) => this.addRow(d, false, index));
    if (this.data.pageflow == 'New') {
      this.sub.add(this.service.saveMatterCheckListHeader(this.form.getRawValue()).subscribe(res => {
        this.toastr.success(this.form.controls.checkListNo.value + " saved successfully!", "Notification", {
          timeOut: 2000,
          progressBar: false,
        });
        this.spin.hide();
        this.dialogRef.close();
      }, err => {
        this.cs.commonerror(err);
        this.spin.hide();
      }));
    } else {
      this.sub.add(this.service.updateCheckListHeader(this.form.getRawValue(), this.data.data.matterHeaderId).subscribe(res => {
        this.toastr.success(this.form.controls.checkListNo.value + " saved successfully!", "Notification", {
          timeOut: 2000,
          progressBar: false,
        });
        this.spin.hide();
        this.dialogRef.close();
      }, err => {
        this.cs.commonerror(err);
        this.spin.hide();
      }));
    }
  }

  addChecklist() {
    const dialogRef1 = this.dialog.open(CustomChecklistComponent, {
      disableClose: true,
      width: '70%',
      maxWidth: '80%',
      position: { top: '9%', },
      data: { classId: this.form.controls.classId.value, caseCategory: this.form.controls.caseCategoryId.value, caseSubCategory: this.form.controls.caseSubCategoryId.value, checkListNo: this.form.controls.checkListNo.value }
    });

    dialogRef1.afterClosed().subscribe(result => {
      let array: any[] = [];
      array = this.dataSource.data;
      result.forEach(x => { array.push(x) });
      this.dataSource = new MatTableDataSource<any>(array);
    });
  }


  removeCart(index: number) {
    console.log(index)
    this.dataSource.data.splice(index, 1);
    this.dataSource = new MatTableDataSource<any>(this.dataSource.data);

  }
}
