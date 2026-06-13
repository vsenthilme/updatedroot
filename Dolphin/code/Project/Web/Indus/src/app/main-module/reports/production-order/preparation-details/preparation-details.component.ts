import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { FormService } from 'src/app/main-module/manufacturing/indusFoods/production-order-confirm/form.service';
import { ProductionService } from 'src/app/main-module/manufacturing/indusFoods/production.service';
import { HandlingUnitService } from 'src/app/main-module/Masters -1/other-masters/handling-unit/handling-unit.service';

@Component({
  selector: 'app-preparation-details',
  templateUrl: './preparation-details.component.html',
  styleUrls: ['./preparation-details.component.scss']
})
export class PreparationDetailsComponent implements OnInit {

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
    }
    this.callTableHeader();
  }

  cols: any[] = [];
  callTableHeader() {
    this.cols = [
      { field: 'bomItem', header: 'Ingredient Code' },
      { field: 'referenceField1', header: 'Ingredient' },
      { field: 'consumedQuantity', header: 'Consumed Qty', format: 'extra' },
      { field: 'receipeQuantity', header: 'Required Qty' },
      { field: 'issuedQuantity', header: 'Issued Qty' },
    ];
  }

  getForm() {
    if (this.data.lines) {
      if (this.data.lines.phaseDescription == "Cooking") {
        this.form = this.formService.createForm('cooking');
        this.data.lines['groupName'] = 'cooking';
        this.form.patchValue(this.data.code);
        this.form.controls.uiProcessConfirm.patchValue(true);
        this.form.disable();
        
      }
      if (this.data.lines.phaseDescription == "Chopping") {
        this.form = this.formService.createForm('chopping');
        this.data.lines['groupName'] = 'diceSliceChop';
        this.form.patchValue(this.data.code);
        this.form.controls.uiProcessConfirm.patchValue(true);
        this.form.disable();
        
      }
      if (this.data.lines.phaseDescription == "Making paste with water") {
        this.form = this.formService.createForm('paste');
        this.data.lines['groupName'] = 'paste';
        this.form.patchValue(this.data.code);
        this.form.controls.uiProcessConfirm.patchValue(true);
        this.form.disable();
        
      }
      if (this.data.lines.phaseDescription == "Peeling") {
        this.form = this.formService.createForm('peeling');
        this.data.lines['groupName'] = 'peeling';
        this.form.patchValue(this.data.code);
        this.form.controls.uiProcessConfirm.patchValue(true);
        this.form.disable();
        
      }
      if (this.data.lines.phaseDescription == "Powder" || this.data.lines.phaseDescription == 'Grinding into powder') {
        this.form = this.formService.createForm('powder');
        this.data.lines['groupName'] = 'powder';
        this.form.patchValue(this.data.code);
        this.form.controls.uiProcessConfirm.patchValue(true);
        this.form.disable();
        
      }
      if (this.data.lines.phaseDescription == "Soaking") {
        this.form = this.formService.createForm('soaking');
        this.data.lines['groupName'] = 'soaking';
        this.form.patchValue(this.data.code);
        this.form.controls.uiProcessConfirm.patchValue(true);
        this.form.disable();
        
      }
      if (this.data.lines.phaseDescription == "Sorting") {
        this.form = this.formService.createForm('sorting');
        this.data.lines['groupName'] = 'sorting';
        this.form.patchValue(this.data.code);
        this.form.controls.uiProcessConfirm.patchValue(true);
        this.form.disable();
        
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
}











