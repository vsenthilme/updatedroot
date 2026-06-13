import { Component, Inject } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AuthService } from '../../../../core/core';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-clearance-popup',
  templateUrl: './clearance-popup.component.html',
  styleUrl: './clearance-popup.component.scss'
})
export class ClearancePopupComponent {
  parameterList: any[] = [];
  constructor(
    public dialogRef: MatDialogRef<ClearancePopupComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private fb: FormBuilder,
    private auth: AuthService,
    private messageService: MessageService,
  ) {
    this.parameterList = [
      { value: 'noofShipments', label: 'No of Shipments' },
    ];
  }

  // form builder initialize
  form = this.fb.group({
    languageId: [this.auth.languageId],
    companyId: [this.auth.companyId],
    addMinCharge: [],
    arithmetic: [],
    parameters: [],
    clearanceCharges: [],
    partnerId: [],
    currency: [],
    deletionIndicator: [],
    formulaField1: ['',],
    formulaField10: [],
    formulaField2: [],
    formulaField3: [],
    formulaField4: [],
    formulaField5: [],
    formulaField6: [],
    formulaField7: [],
    formulaField8: [],
    formulaField9: [],
    multiplier: [],
    noOfShipmentsFrom: [],
    noOfShipmentsTo: [],
    parameter: [],
    referenceField1: [],
    referenceField10: [],
    referenceField2: [],
    referenceField3: [],
    referenceField4: [],
    referenceField5: [],
    referenceField6: [],
    referenceField7: [],
    referenceField8: [],
    referenceField9: [],
    remark: [],
    statusId: [],
    validityDateFrom: [],
    validityDateTo: [],
  });

  submitted = false;

  ngOnInit(): void {
    this.form.controls.partnerId.patchValue(this.data);
    if (this.data.pageflow == "Edit") {
      this.form.patchValue(this.data.code)
    }
  }
  save() {
    this.submitted = true;
    this.dialogRef.close(this.form.value);
  }

  calculateFormula(event: any, type: any) {
      if(this.form.controls.clearanceCharges.value){
        this.messageService.add({ severity: 'error', summary: 'Error', key: 'br', detail: 'Standard charges are already maintained'});
        this.form.controls.parameters.reset();
        return
    }
    if(event.value == 'noofShipments'){
      if(!this.form.controls.noOfShipmentsFrom.value ||  !this.form.controls.noOfShipmentsTo.value){
        this.messageService.add({ severity: 'error', summary: 'Error', key: 'br', detail: 'Kindly enter from and to shipments'});
        this.form.controls.parameters.reset();
        return
      }
    }
    let previousForumala = this.form.controls.formulaField1.value;
    let previousValue = previousForumala !== null ? previousForumala : '';
    let eventValue = event.value !== null ? event.value : '';
    let finalFormula = previousValue + eventValue;
    this.form.controls.formulaField1.patchValue(finalFormula);
    this.form.controls.parameters.reset();
  }
}

