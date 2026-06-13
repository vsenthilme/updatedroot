import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, FormArray } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { Subscription, forkJoin, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { CommonApiService } from 'src/app/common-service/common-api.service';
import { CommonService } from 'src/app/common-service/common-service.service';
import { AuthService } from 'src/app/core/core';
import { ReportsService } from 'src/app/main-module/reports/reports.service';
import { MasterService } from 'src/app/shared/master.service';
import { BOMService } from '../../bom/bom.service';
import { MatTabChangeEvent } from '@angular/material/tabs';
import { MasteropoerationLinesComponent } from '../../masteroperations/masteroperation-new/masteropoeration-lines/masteropoeration-lines.component';
import { RecipeChildComponent } from './recipe-child/recipe-child.component';

@Component({
  selector: 'app-recipe-new',
  templateUrl: './recipe-new.component.html',
  styleUrls: ['./recipe-new.component.scss'],
})
export class RecipeNewComponent implements OnInit {
  operationDescription: any;
  warehouseidDropdown: any;
  isLinear = false;
  constructor(
    private fb: FormBuilder,
    public auth: AuthService,
    public dialog: MatDialog,
    public toastr: ToastrService,
    // private cas: CommonApiService,
    private spin: NgxSpinnerService,
    private route: ActivatedRoute,
    private router: Router,
    private cs: CommonService,
    private cas: CommonApiService,
    private reportService: ReportsService,
    private service: BOMService,
    private masterService: MasterService
  ) {}

  filterpartnercodeList: any[] = [];
  isShowDiv = false;
  showFloatingButtons: any;
  toggle = true;
  public icon = 'expand_more';

  toggleFloat() {
    this.isShowDiv = !this.isShowDiv;
    this.toggle = !this.toggle;

    if (this.icon === 'expand_more') {
      this.icon = 'chevron_left';
    } else {
      this.icon = 'expand_more';
    }
    this.showFloatingButtons = !this.showFloatingButtons;
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
  submitted = false;
  referenceField8: any;
  plantSelection: any;
  statusId: any;
  companySelection: any;
  description: any;
  itemTypeList: any[] = [];
  routingList: any[] = [];
  js: any = {};
  ngOnInit(): void {
    let code = this.route.snapshot.params.code;
    this.js = this.cs.decrypt(code);
    this.referenceField9 = 1;
    let obj1: any = {};

    obj1.companyCodeId = [this.auth.companyId];
    obj1.plantId = [this.auth.plantId];
    obj1.languageId = [this.auth.languageId];
    obj1.warehouseId = [this.auth.warehouseId];

    console.log(this.js);
    this.plantSelection = this.auth.plantId;
    this.companySelection = this.auth.companyId;
    this.parentItemQuantity = 1;
    this.service.searchMasterOperation(obj1).subscribe((res: any[]) => {
      res.forEach((element) => {
        this.routingList.push({
          value: element.operationNumber,
          label: element.operationNumber + '-' + element.operationDescription,
        });
      });
      this.routingList = this.cs.removeDuplicateInArray(this.routingList);
      this.spin.hide();
    });

    //this.dropdownlist();

    this.createForm();

    if (this.js.pageflow != 'New') {
      if (this.js.pageflow == 'Display') this.form.disable();
      this.fill();
    }
  }
  itemCode: any;
  resultTable1: any[] = [];
  resultTable2: any;
  bomNumber: any;
  sub = new Subscription();
  createList3: any[] = [];
  showOperation = false;
  itemCodechanged(event) {
    console.log(event);
    this.service
      .search({
        companyCode: [this.auth.companyId],
        plantId: [this.auth.plantId],
        warehouseId: [this.auth.warehouseId],
        languageId: [this.auth.languageId],
        parentItemCode: [event.value],
      })
      .subscribe((res) => {
        this.bomNumber = res[0].bomNumber;
        this.showBomTable = true;
        this.resultTable = res[0].bomLines;
      });
  }
  routingchanged(event) {
    console.log(event);
    let obj: any = {};
    obj.companyCode = [this.auth.companyId];
    obj.plantId = [this.auth.plantId];
    obj.languageId = [this.auth.languageId];
    obj.warehouseId = [this.auth.warehouseId];
    obj.operationNumber = [event.value];
    this.sub.add(
      this.service.searchMasterOperation(obj).subscribe((res: any) => {
        console.log(res);
        this.showOperation = true;
        console.log(res[0]);
        this.resultTable1 = res;
        console.log(this.resultTable1);
        for (let i = 0; i < this.resultTable1.length; i++) {
          for (let j = 0; j < this.resultTable.length; j++) {
            // Update resultTable[j] with values from resultTable1[i]
            this.resultTable[j].phaseNumber = this.resultTable1[i].phaseNumber;
            this.resultTable[j].phaseDescription =
              this.resultTable1[i].phaseDescription;

            // Log updated resultTable[j]
            console.log(this.resultTable[j]);

            // Push updated resultTable[j] to createList3
            this.createList3.push({ ...this.resultTable[j] });
          }
        }
        console.log(this.createList3);
      })
    );
  }
  operationNumber: any;
  receipeId: any;
  resultTable3: any[] = [];
  fill() {
    this.spin.show();
    this.dropdownlist();
    let obj: any = {};
    obj.companyCodeId = [this.auth.companyId];
    obj.plantId = [this.auth.plantId];
    obj.languageId = [this.auth.languageId];
    obj.warehouseId = [this.auth.warehouseId];
    obj.receipeId = [this.js.code];
    this.spin.show();
    this.sub.add(
      this.service.searchMasterReceipe(obj).subscribe(
        (res: any) => {
          console.log(res);
          this.resultTable3 = res;
          this.operationNumber = res[0].operationNumber;
          this.bomNumber = res[0].bomNumber;
          this.itemCode = res[0].itemCode;
          this.receipeId = res[0].receipeId;
          this.operationNumber = res[0].operationNumber;
          this.referenceField8 = res[0].referenceField8;
          this.referenceField6 = res[0].referenceField6;
          this.referenceField10 = res[0].remarks;
          this.referenceField9 = res[0].referenceField9;
          this.statusId = res[0].statusId.toString();
          res.forEach((element, index) => {
            if (index != res.length - 1) {
              this.addNewRow();
            }

            this.service
              .search({
                companyCode: [this.auth.companyId],
                plantId: [this.auth.plantId],
                warehouseId: [this.auth.warehouseId],
                languageId: [this.auth.languageId],
                bomNumber: [res[0].bomNumber],
              })
              .subscribe((res) => {
                this.showBomTable = true;
                this.resultTable = res[0].bomLines;
              });
            let obj1: any = {};
            obj1.companyCode = [this.auth.companyId];
            obj1.plantId = [this.auth.plantId];
            obj1.languageId = [this.auth.languageId];
            obj1.warehouseId = [this.auth.warehouseId];
            obj1.operationNumber = [res[0].operationNumber];
            this.sub.add(
              this.service.searchMasterOperation(obj1).subscribe((res: any) => {
                console.log(res);
                this.showOperation = true;
                console.log(res[0]);
                this.resultTable1 = res;
                this.showBomTable = true;
              })
            );
          });

          // this.tableRowArray.patchValue(res);
          this.resultTable = res[0].bomLines;
          console.log(this.resultTable);

          this.spin.hide();
        },
        (err) => {
          this.cs.commonerrorNew(err);
          this.spin.hide();
        }
      )
    );

    this.spin.hide();
  }
  flagChanged = false;
  createList2: any[] = [];
  createdOpen = false;
  openDialog(data: any = 'New', rowIndex: any, data1): void {
    console.log(data1);
    const dialogRef = this.dialog.open(RecipeChildComponent, {
      disableClose: true,
      width: '70%',
      maxWidth: '80%',
      height: '60%',
      data: {
        pageflow: data,
        code: data1,
        code2: this.resultTable,
        code3: this.resultTable3,
        rowIndex,
      },
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        
        if (result[0].openClose === true) {
          this.flagChanged = true; // Set flag to indicate change

          // Create a set of phaseNumbers from the result for filtering
          const phaseNumbers = new Set(result.map((item) => item.phaseNumber));
          if (this.createList2 && this.resultTable3.length == 0) {
            if (this.createList2.length == 0) {
              // Filter createList3 to remove items where phaseNumber is in the result
              this.createList2 = this.createList3.filter(
                (item) => !phaseNumbers.has(item.phaseNumber)
              );
            } else {
              this.createList2 = this.createList2.filter(
                (item) => !phaseNumbers.has(item.phaseNumber)
              );
            }

            // Add the result items to createList2
            this.createList2.push(...result);
            console.log('createList2 after push:', this.createList2);
          }
          if (this.resultTable3.length != 0) {
            if (this.createList2.length == 0) {
              this.createList2 = this.resultTable3.filter(
                (item) => !phaseNumbers.has(item.phaseNumber)
              );
            } else {
              this.resultTable3 = this.resultTable3.filter(
                (item) => !phaseNumbers.has(item.phaseNumber)
              );
              this.createList2 = this.resultTable3;
            }

            this.createList2.push(...result);
          }
        } else {
          console.log('openClose is not true');
        }

        console.log('Final createList2:', this.createList2);
      } else {
        this.flagChanged = false; // Flag remains unchanged if dialog is closed without result
      }
    });
  }
  receieptIdno: any;
  referenceField6: any;
  form: FormGroup;
  pricelist: any;
  parentItemCode: any;
  parentItemQuantity: any;
  private createTableRow(): FormGroup {
    return this.fb.group({
      bomNumber: new FormControl(),
      childItemCode: new FormControl(),
      childItemQuantity: new FormControl(),
      companyCode: new FormControl(),
      createdBy: new FormControl(),
      createdOn: new FormControl(),
      deletionIndicator: new FormControl(),
      languageId: new FormControl(),
      plantId: new FormControl(),
      referenceField1: new FormControl(),
      referenceField10: new FormControl(),
      referenceField2: new FormControl(),
      referenceField3: new FormControl(),
      referenceField4: new FormControl(),
      referenceField5: new FormControl(),
      referenceField6: new FormControl(),
      referenceField7: new FormControl(),
      referenceField8: new FormControl(),
      referenceField9: new FormControl(),
      sequenceNo: new FormControl(),
      statusId: new FormControl(),
      updatedBy: new FormControl(),
      updatedOn: new FormControl(),
      warehouseId: new FormControl(),
    });
  }

  get tableRowArray(): FormArray {
    return this.form.get('tableRowArray') as FormArray;
  }

  onDeleteRow(rowIndex: number): void {
    this.tableRowArray.removeAt(rowIndex);
  }
  openClose = false;
  createList: any[] = [];
  add() {
    const dialogRef = this.dialog.open(MasteropoerationLinesComponent, {
      disableClose: true,
      width: '50%',
      maxWidth: '80%',
      data: this.resultTable.length + 1,
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log(result.length);
      if (result) {
        this.resultTable;
        if (result.length > 0) {
          this.resultTable.push(result);
        }
        this.openClose = true;
        this.resultTable.push(result);
      }
      this.resultTable = [...this.resultTable];
    });
  }
  delete(i) {
    //this.resultTable = this.resultTable.filter(val => val.description !== i.description );
    this.resultTable.splice(i, 1);
    //(this.js.deletionIndicator)=1;
  }

  addNewRow(): void {
    this.tableRowArray.push(this.createTableRow());
  }

  createForm(): void {
    this.form = this.fb.group({
      tableRowArray: this.fb.array([this.createTableRow()]),
    });
  }
  referenceField10: any;
  referenceField9: any;
  search = true;
  resultTable: any[] = [];
  floorList: any[] = [];
  moduleList: any[] = [];
  paymenttermList: any[] = [];
  itemgroupList: any[] = [];
  warehouseIdList: any[] = [];
  partnercodeList: any[] = [];
  billingmodeList: any[] = [];
  billingfrequencyList: any[] = [];
  serviceTypeList: any[] = [];
  companyList: any[] = [];
  currencyList: any[] = [];
  plantList: any[] = [];
  languageList: any[] = [];
  uomList: any[] = [];
  dropdownlist() {
    this.spin.show();
    let obj1: any = {};
    obj1.companyCode = [this.auth.companyId];
    obj1.plantId = [this.auth.plantId];
    obj1.languageId = [this.auth.languageId];
    obj1.warehouseId = [this.auth.warehouseId];
    this.service.searchMasterOperation(obj1).subscribe((res: any[]) => {
      res.forEach((element) => {
        this.routingList.push({
          value: element.operationNumber,
          label: element.operationNumber + '-' + element.operationDescription,
        });
      });
      this.spin.hide();
    });

    this.spin.hide();
  }
  onserviceTypeChange(value) {
    this.service
      .search({
        companyCodeId: this.form.controls.companyCodeId.value,
        plantId: this.form.controls.plantId.value,
        warehouseId: this.form.controls.warehouseId.value,
        languageId: [this.form.controls.languageId.value],
        moduleId: [this.form.controls.moduleId.value],
        serviceTypeId: [this.form.controls.serviceTypeId.value],
      })
      .subscribe((res) => {
        console.log(res.length);
        if (res.length > 0) {
          this.pricelist = res.length + 1;
          this.form.controls.priceListId.patchValue(this.pricelist);
        } else {
          this.pricelist = 1;
          this.form.controls.priceListId.patchValue(this.pricelist);
        }
      });
  }
  deleteDialog() {
    console.log('Hello');
  }
  onWarehouseChange(value) {
    console.log(value);
    this.form.controls.companyCodeId.patchValue(value.companyCodeId);
    this.form.controls.languageId.patchValue(value.languageId);
    this.form.controls.plantId.patchValue(value.plantId);

    this.form.controls.languageId.patchValue(this.auth.languageId);
    this.form.controls.plantId.patchValue(this.auth.plantId);
    this.form.controls.companyCodeId.patchValue(this.auth.companyId);

    this.masterService
      .searchModule({
        companyCodeId: this.form.controls.companyCodeId.value,
        plantId: this.form.controls.plantId.value,
        warehouseId: value.value,
        languageId: [this.form.controls.languageId.value],
      })
      .subscribe((res) => {
        this.moduleList = [];
        res.forEach((element) => {
          if (element.moduleDescription != null) {
            this.moduleList.push({
              value: element.moduleId,
              label: element.moduleId + '-' + element.moduleDescription,
            });
          }
        });
        this.moduleList = this.cs.removeDuplicatesFromArrayNewstatus(
          this.moduleList
        );
      });
    this.masterService
      .searchserviceType({
        companyCodeId: this.form.controls.companyCodeId.value,
        plantId: this.form.controls.plantId.value,
        warehouseId: value.value,
        languageId: [this.form.controls.languageId.value],
        moduleId: [this.form.controls.moduleId.value],
      })
      .subscribe((res) => {
        this.moduleList = [];
        res.forEach((element) => {
          if (element.moduleDescription != null) {
            this.moduleList.push({
              value: element.serviceTypeId,
              label:
                element.serviceTypeId + '-' + element.serviceTypeDescription,
            });
          }
        });
        this.moduleList = this.cs.removeDuplicatesFromArrayNewstatus(
          this.moduleList
        );
      });
  }
  onmoduleChange(value) {
    this.masterService
      .searchserviceType({
        companyCodeId: this.form.controls.companyCodeId.value,
        plantId: this.form.controls.plantId.value,
        warehouseId: this.form.controls.warehouseId.value,
        languageId: [this.form.controls.languageId.value],
        moduleId: [value.value],
      })
      .subscribe((res) => {
        this.serviceTypeList = [];
        res.forEach((element) => {
          this.serviceTypeList.push({
            value: element.serviceTypeId,
            label: element.serviceTypeId + '-' + element.serviceTypeDescription,
          });
        });
      });
  }
  showBomTable = false;
  multiselectItemCodeList: any[] = [];
  itemCodeList: any[] = [];
  multiPartnerList: any[] = [];
  onItemType(searchKey) {
    let searchVal = searchKey?.filter;
    if (searchVal !== '' && searchVal !== null) {
      forkJoin({
        itemList: this.reportService
          .getItemCodeDropDown2(
            searchVal.trim(),
            this.auth.companyId,
            this.auth.plantId,
            this.auth.warehouseId,
            this.auth.languageId
          )
          .pipe(catchError((err) => of(err))),
      }).subscribe(({ itemList }) => {
        if (itemList != null && itemList.length > 0) {
          this.multiselectItemCodeList = [];
          this.itemCodeList = itemList;
          this.itemCodeList.forEach((x) =>
            this.multiselectItemCodeList.push({
              value: x.itemCode,
              label:
                x.itemCode + ' - ' + x.manufacturerName + ' - ' + x.description,
              manufacturingName: x.manufacturerName,
              description: x.description,
              uomId: x.uomId,
            })
          );
        }
      });
    }
  }
  resultTable4: any;
  createDuplicate: any[] = [];
  submit() {
    console.log(this.createList2);
    this.createDuplicate = this.createList2;
    this.createList2 = this.createList2;
    this.form.controls.tableRowArray.value.forEach((element) => {
      element.warehouseId = this.auth.warehouseId;
      element.languageId = this.auth.languageId;
      element.companyCodeId = this.auth.companyId;
      element.plantId = this.auth.plantId;
      element.priceListId = this.pricelist;
      element.description = this.description;
    });

    this.cs.notifyOther(false);
    this.spin.show();

    if (this.js.code) {
      console.log(this.createList2);
      if (this.flagChanged) {
        // console.log(1)
        this.createList2.forEach((x) => {
          x.companyCodeId = this.auth.companyId;
          x.itemCode = this.itemCode;
          x.operationNumber = this.operationNumber;
          x.receipeId = this.receipeId;
          x.referenceField10 = x.referenceField10;
          x.referenceField9 = this.referenceField9;
          x.referenceField6 = x.referenceField6;
          x.statusId = 1;
          x.remarks = this.referenceField10;
        });
      } else {
        console.log(1);
        this.createList2 = this.resultTable3;
        for (let i = 0; i < this.createList2.length; i++) {
          this.createList2[i].referenceField10 =
            this.createList2[i].referenceField10;
          this.createList2[i].referenceField9 = this.referenceField9;
          this.createList2[i].remarks = this.referenceField10;
        }
      }
      this.sub.add(
        this.service
          .UpdateMasterReceipe(
            this.createList2,
            this.auth.warehouseId,
            this.auth.plantId,
            this.auth.companyId,
            this.auth.languageId
          )
          .subscribe(
            (res) => {
              this.toastr.success(
                res[0].receipeId + ' updated successfully!',
                'Notification',
                {
                  timeOut: 2000,
                  progressBar: false,
                }
              );
              this.router.navigate(['/main/other-masters/masterReceipe']);

              this.spin.hide();
            },
            (err) => {
              this.cs.commonerrorNew(err);
              this.spin.hide();
            }
          )
      );
    } else {
      this.createList2.forEach((x) => {
        x.phaseNumber = x.phaseNumber;
        x.requiredQuantity = x.requiredQuantity;
        x.operationNumber = this.operationNumber;
        x.bomNumber = this.bomNumber.toString();
        x.statusId = this.statusId;
        x.itemCode = this.itemCode;
        x.referenceField6 = x.referenceField6;
        x.companyCodeId = x.companyCode;
        x.uom = x.referenceField7;
        x.statusId = 1;
        x.referenceField10 = x.referenceField10;
        x.referenceField9 = this.referenceField9;
        x.referenceField5 = x.childItemQuantity;
        x.remarks = this.referenceField10;
      });
      console.log(this.resultTable);
      if (this.createList2.length == 0) {
        this.toastr.error(
          'Please fill required fields to continue',
          'Notification',
          {
            timeOut: 2000,
            progressBar: false,
          }
        );

        this.spin.hide();
        if (this.createList2.length == 0) {
          this.toastr.error(
            'Please fill required fields to continue',
            'Notification',
            {
              timeOut: 2000,
              progressBar: false,
            }
          );

          this.spin.hide();
        }
      } else {
        this.sub.add(
          this.service.CreateMasterReceipe(this.createList2).subscribe(
            (res) => {
              this.toastr.success(
                res[0].receipeId + ' Saved Successfully!',
                'Notification',
                {
                  timeOut: 2000,
                  progressBar: false,
                }
              );
              this.router.navigate(['/main/other-masters/masterReceipe']);
              this.spin.hide();
            },
            (err) => {
              this.cs.commonerrorNew(err);
              this.spin.hide();
            }
          )
        );
      }
    }
  }
}
