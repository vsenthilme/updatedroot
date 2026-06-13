import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { FormService } from '../form.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { NgxSpinnerService } from 'ngx-spinner';
import { CommonService } from 'src/app/common-service/common-service.service';
import { ProductionService } from '../../production.service';

@Component({
  selector: 'app-operation-fields',
  templateUrl: './operation-fields.component.html',
  styleUrls: ['./operation-fields.component.scss']
})
export class OperationFieldsComponent implements OnInit {

  constructor(private formService: FormService, public dialogRef: MatDialogRef<any>,
    @Inject(MAT_DIALOG_DATA) public data: any, private toastr: ToastrService, private spin: NgxSpinnerService, private cs: CommonService,
    private service: ProductionService, private fb: FormBuilder) { }

  form: FormGroup;
  showBomLine = false;
  bomDetails: any[] = [];
  ngOnInit(): void {

    if ((this.data.lines.phaseDescription == "Grinding into powder") || (this.data.lines.phaseDescription == "Cooking") || (this.data.lines.phaseDescription == "Chopping") || (this.data.lines.phaseDescription == "Making paste with water") || (this.data.lines.phaseDescription == "Peeling") || (this.data.lines.phaseDescription == "Powder") || (this.data.lines.phaseDescription == "Sorting") || (this.data.lines.phaseDescription == "Soaking")) {
      this.getForm();
    }
    else {
      this.showBomLine = true;
    }
    this.callTableHeader();

    this.bomDetails = this.data.bomDetails.filter(x => x.phaseNumber == this.data.lines.phaseNumber);
    this.bomDetails.forEach(x => {
      x['uiProcessConfirm'] = true;
      if(x.receipeQuantity == null){
        x.receipeQuantity = 0;
        x.consumedQuantity = 0;
        x.issuedQuantity = 0;
       }
    });

  }

  cols: any[] = [];
  callTableHeader() {
    this.cols = [
      { field: 'bomItem', header: 'Ingredient Code' },
      { field: 'referenceField1', header: 'Ingredient' },
      { field: 'consumedQuantity', header: 'Consumed Qty', format: 'extra' },
      { field: 'receipeQuantity', header: 'Required Qty' },
      { field: 'issuedQuantity', header: 'Issued Qty' },
      { field: 'loss', header: 'Loss' },
      { field: 'yield', header: 'Yield' },
    ];
  }

  getForm() {
    if (this.data.lines) {
      if (this.data.lines.phaseDescription == "Cooking") {
        this.form = this.formService.createForm('cooking');
        this.data.lines['groupName'] = 'cooking';
        this.form.patchValue(this.data.lines);
        this.form.controls.uiProcessConfirm.patchValue(true);
        this.form.controls.batchNumber.disable();
        this.form.controls.itemCode.disable();
      }
      if (this.data.lines.phaseDescription == "Chopping") {
        this.form = this.formService.createForm('chopping');
        this.data.lines['groupName'] = 'diceSliceChop';
        this.form.patchValue(this.data.lines);
        this.form.controls.uiProcessConfirm.patchValue(true);
        this.form.controls.batchNumber.disable();
        this.form.controls.itemCode.disable();
      }
      if (this.data.lines.phaseDescription == "Making paste with water") {
        this.form = this.formService.createForm('paste');
        this.data.lines['groupName'] = 'paste';
        this.form.patchValue(this.data.lines);
        this.form.controls.uiProcessConfirm.patchValue(true);
        this.form.controls.batchNumber.disable();
        this.form.controls.itemCode.disable();
      }
      if (this.data.lines.phaseDescription == "Peeling") {
        this.form = this.formService.createForm('peeling');
        this.data.lines['groupName'] = 'peeling';
        this.form.patchValue(this.data.lines);
        this.form.controls.uiProcessConfirm.patchValue(true);
        this.form.controls.batchNumber.disable();
        this.form.controls.itemCode.disable();
      }
      if (this.data.lines.phaseDescription == "Powder" || this.data.lines.phaseDescription == 'Grinding into powder') {
        this.form = this.formService.createForm('powder');
        this.data.lines['groupName'] = 'powder';
        this.form.patchValue(this.data.lines);
        this.form.controls.uiProcessConfirm.patchValue(true);
        this.form.controls.batchNumber.disable();
        this.form.controls.itemCode.disable();
      }
      if (this.data.lines.phaseDescription == "Soaking") {
        this.form = this.formService.createForm('soaking');
        this.data.lines['groupName'] = 'soaking';
        this.form.patchValue(this.data.lines);
        this.form.controls.uiProcessConfirm.patchValue(true);
        this.form.controls.batchNumber.disable();
        this.form.controls.itemCode.disable();
      }
      if (this.data.lines.phaseDescription == "Sorting") {
        this.form = this.formService.createForm('sorting');
        this.data.lines['groupName'] = 'sorting';
        this.form.patchValue(this.data.lines);
        this.form.controls.uiProcessConfirm.patchValue(true);
        this.form.controls.batchNumber.disable();
        this.form.controls.itemCode.disable();
      }
    }
  }

  disabled = false;
  step = 0;
  //dialogRef: any;

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
  submitted = false;

  submit() {
    let consumedQuantityNull = false;

    this.bomDetails.forEach((x: any) => {
      if (x.consumedQuantity == null) consumedQuantityNull = true;
    });

    if (consumedQuantityNull) {
      this.toastr.error(
        "Please fill Consumed Qty to continue",
        "Notification", {
        timeOut: 2000,
        progressBar: false,
      })
      return;
    }
    this.submitted = true;

    if (this.form.invalid) {
      this.toastr.error(
        "Please fill required fields to continue",
        "Notification", {
        timeOut: 2000,
        progressBar: false,
      }
      );

      this.cs.notifyOther(true);
      return;
    }

    const combined = this.cs.removeDuplicateObjBOM(this.data.bomDetails, this.bomDetails);

    this.cs.notifyOther(false);
    let obj: any = {};
    obj.form = this.form,
      obj.groupName = this.data.lines.groupName;
    obj.bomDetails = combined;
    this.dialogRef.close(obj);
  }
  email = new FormControl('', [Validators.required, Validators.email]);
  public errorHandling = (control: string, error: string = "required") => {
    return this.form.controls[control].hasError(error) && this.submitted;
  }
  getErrorMessage() {
    if (this.email.hasError('required')) {
      return ' Field should not be blank';
    }
    return this.email.hasError('email') ? 'Not a valid email' : '';
  }

  onInputChange(event: any, rowData: any, field: string) {
    const value = event.target.value;
    rowData[field] = value;

    rowData.loss = rowData.issuedQuantity - rowData.consumedQuantity;
    rowData.yield = (rowData.consumedQuantity/rowData.receipeQuantity) * 100;
  }

  submitBOM() {
    let consumedQuantityNull = false;

    this.bomDetails.forEach((x: any) => {
      if (x.consumedQuantity == null) consumedQuantityNull = true;
    });

    if (consumedQuantityNull) {
      this.toastr.error(
        "Please fill Consumed Qty to continue",
        "Notification", {
        timeOut: 2000,
        progressBar: false,
      })
      return;
    }

    const combined = this.cs.removeDuplicateObjBOM(this.data.bomDetails, this.bomDetails);

    this.cs.notifyOther(false);

    let obj: any = {};
    obj.form = this.form,
      obj.groupName = this.data.lines.groupName;
    obj.bomDetails = combined;
    this.dialogRef.close(obj);
  }
}











