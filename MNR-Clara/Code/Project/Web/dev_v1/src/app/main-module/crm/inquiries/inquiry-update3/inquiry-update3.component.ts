
import { Component, OnInit, Inject } from "@angular/core";
import { FormBuilder, FormControl, Validators } from "@angular/forms";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { Router } from "@angular/router";
import { NgxSpinnerService } from "ngx-spinner";
import { ToastrService } from "ngx-toastr";
import { Subscription } from "rxjs";
import { CommonApiService } from "src/app/common-service/common-api.service";
import { CommonService } from "src/app/common-service/common-service.service";
import { AuthService } from "src/app/core/core";
import { InquiresService } from "../inquires.service";
import {ErrorStateMatcher} from '@angular/material/core';
import { StatusService } from "src/app/main-module/setting/configuration/status-id/status.service";

interface SelectItem {
  id: string;
  itemName: string;
}


@Component({
  selector: 'app-inquiry-update3',
  templateUrl: './inquiry-update3.component.html',
  styleUrls: ['./inquiry-update3.component.scss']
})
export class InquiryUpdate3Component implements OnInit {
  public myModel = ''
    public mask = [/\d/, /\d/, /\d/, '-', /\d/, /\d/, /\d/, '-', /\d/, /\d/, /\d/, /\d/]
 // public mask = [ '+','1',  ' ', /\d/, /\d/, /\d/, '-',/\d/, /\d/, /\d/, '-', /\d/, /\d/, /\d/, /\d/]
  //public mask = [ '+','(', /[1-9]/,')', ' ', /\d/, /\d/, /\d/, '-', /\d/, /\d/, /\d/, /\d/]
  today = this.cs.today();
  maxDate = new Date();
  screenid: 1065 | undefined;
  showFiller = false;
  code: string = '';
  title: string = 'New';
  btntxt = 'Save';
  assigif = true;
  validationif = false;
  refereal:any[]=[];
  sub = new Subscription();
  form = this.fb.group({
    assignedUserId: [],
    howDidYouHearAboutUs: [, [Validators.required]],
    others:[],
    assignedUserIdFE: [],
    classId: [],
    //contactNumber: [, [Validators.required,Validators.minLength(12),Validators.maxLength(12),Validators.pattern('[0-9 -]+$')]],
    contactNumber: [, [Validators.required,Validators.pattern('[0-9 -]+$')]],
    deletionIndicator: [0],
    email: [, [Validators.required, Validators.email ]],
    firstName: [, [Validators.required,Validators.maxLength(20), Validators.pattern('[a-zA-Z \S.-]+')]],
    inquiryDate: [this.cs.today()],//meed 
    inquiryModeId: [, [Validators.required]],
    //inquiryNotesNumber: [],
    inquiryNumber: [],
    intakeFormId: [],
    //intakeNotesNumber: [],
    // languageId: [, [Validators.required]],
    lastName: [, [Validators.required,Validators.maxLength(20), Validators.pattern('[a-zA-Z \S]+')]],
    // /referenceField9: [, Validators.pattern('[A-Za-z0-9]+$')],
    referenceField9: [],
    statusId: [],
    referredBy:[],
    othersReferred:[],
    // transactionId: [],
    createdOn: [this.today],
    createdBy: [this.auth.userID, [Validators.required]],
    updatedBy: [this.auth.userID, [Validators.required]],
    updatedOn: [this.today],
    referenceField4: [],
    // assindby and assindon check with raj
  });

  email = new FormControl('', [Validators.required, Validators.email]);
  submitted = false;
  public errorHandling = (control: string, error: string = "required") => {
    return this.form.controls[control].hasError(error) && this.submitted;
  }
  getErrorMessage() {
    // if (this.email.hasError('required')) {
    //   return ' Field should not be blank';
    // }
    return this.email.hasError('required') ? ' Field should not be blank' : '';
      
  }

 // maxLength = 10;
  separateDialCode = true;
  constructor(public dialogRef: MatDialogRef<InquiryUpdate3Component>,
    @Inject(MAT_DIALOG_DATA,) public data: any,
    private fb: FormBuilder, private auth: AuthService,
    private service: InquiresService,
    private statusservice:StatusService,
    private router: Router,
    private spin: NgxSpinnerService, public toastr: ToastrService,
    private cas: CommonApiService,
    private cs: CommonService,

  ) {   this.refereal = [
    {value: "Alfonso Kennard - Kennard Law" , label: 'Alfonso Kennard - Kennard Law '},
    {value: "Gregg M. Rosenberg - Rosenberg & Sprovach Attorneys", label: 'Gregg M. Rosenberg - Rosenberg & Sprovach Attorneys'},
    {value: "Samantha Martinez ", label: 'Samantha Martinez '},
    {value: "Buzbee Law Firm ", label: 'Buzbee Law Firm '},
    {value: "Johnny Garza - Adame Garza LLP", label: 'Johnny Garza - Adame Garza LLP'},
    {value: "Andy Drumheller - DHM Law", label: 'Andy Drumheller - DHM Law'},
    {value: "Jacklyn Varela ", label: 'Jacklyn Varela '},
    {value: "Jesus C. 'Jesse' Rios", label: "Jesus C. 'Jesse' Rios"},
    {value: "Law Offices of Clyde W. Purleson, P.C.", label: 'Law Offices of Clyde W. Purleson, P.C.'},
    {value: "Meg Murphy", label: 'Meg Murphy'},
    {value: "Dallas Deandra Grant ", label: 'Dallas Deandra Grant '},
    {value: "Anthony R. Magdaleno", label: 'Anthony R. Magdaleno'},
    {value: "Lorie Cevallos - Cevallos Law Firm ", label: 'Lorie Cevallos - Cevallos Law Firm '},
    {value: "Sio Ramirez Pitre - Kalish Law Texas ", label: 'Sio Ramirez Pitre - Kalish Law Texas '},
    {value: "Benjamin K. Sanchez - Sanchez Law Firm ", label: 'Benjamin K. Sanchez - Sanchez Law Firm '},
    {value: "Jill Willard Young - Boyar Biller", label: 'Jill Willard Young - Boyar Biller'},
    {value: "Glen Eichelberger - Gray Reed", label: 'Glen Eichelberger - Gray Reed'},
    {value: "Michael Galligan - Galligan & Manning", label: 'Michael Galligan - Galligan & Manning'},
    {value: "Terri Lacy - Hunton Andrews Kurth", label: 'Terri Lacy - Hunton Andrews Kurth'},
    {value: "Olivia Carbajal de Garcia - Ytterberg Deery Knull LLP", label: 'Olivia Carbajal de Garcia - Ytterberg Deery Knull LLP'},
    {value: "Nikki Pielop - FizerBeck ", label: 'Nikki Pielop - FizerBeck '},
    {value: "Blakeman & Associates ", label: 'Blakeman & Associates '},
    {value: "Achilles Group ", label: 'Achilles Group '},
    {value: "Joseph G. Soliz - The Solis Law Firm ", label: 'Joseph G. Soliz - The Solis Law Firm '},
    {value: "Monica Lira Bravo - Lira Bravo Law", label: 'Monica Lira Bravo - Lira Bravo Law '},
    {value: "Charles Stanfield", label: 'Charles Stanfield'},
    {value: "Ruben J Rodriguez JR. CPA - SMS US LLP ", label: 'Ruben J Rodriguez JR. CPA - SMS US LLP'},
    {value: "Bill Hayes", label: 'Bill Hayes '},
    {value: "Diane C. Teich ", label: 'Diane C. Teich '},
    {value: "S. Omar Izfar - Wilson Cribbs & Goren ", label: 'S. Omar Izfar - Wilson Cribbs & Goren '},
    {value: "Evan J. Green ", label: 'Evan J. Green '},
    {value: "Mauro Ramirez - Ramirez Law PLLC", label: 'Mauro Ramirez - Ramirez Law PLLC'},
    {value: "Clyde Burleson ", label: 'Clyde Burleson '},
    {value: "Lucio Montes ", label: 'Lucio Montes '},
    {value: "Nathaniel Tarlow - The Tarlow Law Office ", label: 'Nathaniel Tarlow - The Tarlow Law Office '},
    {value: "Rosa M. Cantu  ", label: 'Rosa M. Cantu '},
    {value: "Brian Green, Esq - Law office of Brian Green", label: 'Brian Green, Esq - Law office of Brian Green'},
    {value: "Lawrence Moscoso - Kalish Law  ", label: 'Lawrence Moscoso - Kalish Law '},
    {value: "Juan Rivera - Kalish Law ", label: 'Juan Rivera - Kalish Law '},
    {value: "Eliud Zavala - Kalish Law  ", label: 'Eliud Zavala - Kalish Law  '},
    {value: "Tony Parada - Kalish Law  ", label: 'Tony Parada - Kalish Law '},
    {value: "Sandoval James Law Firm  ", label: 'Sandoval James Law Firm'},
    {value: "Other", label: 'Other'}
];}
seeother=false;
  ngOnInit(): void {
    this.form.controls.createdBy.disable();
    this.form.controls.createdOn.disable();
    this.form.controls.updatedBy.disable();
    this.form.controls.updatedOn.disable();
    this.form.controls.inquiryDate.disable();
    this.form.controls.assignedUserId.setValidators(Validators.required);
      this.form.controls.classId.setValidators(Validators.required);// n
    this.auth.isuserdata();
    this.title = this.data.pageflow.replace(' - H', '');
    this.dropdownlist();
    this.getstatusDropdown();

    if (this.data.code) {
      this.title = this.data.pageflow + ' - ' + this.data.code;
      this.fill();
      this.assigif = true;
      this.form.controls.classId.setValidators([Validators.required]);

      this.form.controls.assignedUserId.setValidators([Validators.required]);
      this.btntxt = 'Update';
    }

    if (this.data.pageflow == 'Inquiry Assign') {
      this.assigif = true;
      this.form.controls.assignedUserId.setValidators(Validators.required);
      this.form.controls.classId.setValidators(Validators.required);// not working
      this.btntxt = 'Assign';
    }

    else if (this.data.pageflow == 'Inquiry Validation') {
      this.assigif = true;
      this.validationif = true;
      this.form.controls.classId.setValidators([Validators.required]);

      this.form.controls.assignedUserId.setValidators([Validators.required]);
      this.form.controls.statusId.setValidators([Validators.required]);
      this.btntxt = 'Update & Validation';

    }
    this.form.updateValueAndValidity();

  }

  inquiryModeIdList: any[] = [];
  classIdList: any[] = [];
  useridList: any[] = [];
  statusIdList: any[] = [];

  selectedItems: SelectItem[] = [];
  multiselecttimekeeperList: SelectItem[] = [];
  multitimekeeperList: SelectItem[] = [];


  
  dropdownSettings = {
    singleSelection: true,
    text:"Select",
    selectAllText:'Select All',
    unSelectAllText:'UnSelect All',
    enableSearchFilter: true,
    badgeShowLimit: 2
  };

  getstatusDropdown() {
    this.sub.add(this.statusservice.Getall().subscribe(res => {
      const filteredRecords = res.filter((item: any) => Object.keys(item).length === 9);
      if (filteredRecords.length > 0) {
        console.log(filteredRecords);
        this.statusIdList = filteredRecords
        .filter((item: any) => ![1, 2, 3, 7, 25].includes(item.statusId)) // Exclude statusIds
          .map((item: any) => ({
            key: item.statusId,
            value: item.statusId + ' - ' + item.status,
            referencefield1: item.referencefield1
          }));
      } else {
        console.error('No records found with exactly 9 fields');
      }
    }));
  }
  
  

  dropdownlist() {
   
    this.spin.show();
    this.cas.getalldropdownlist([this.cas.dropdownlist.setup.inquiryModeId.url,
    this.cas.dropdownlist.setup.classId.url,
    this.cas.dropdownlist.setup.userId.url
   ]).subscribe((results) => { // this.cas.dropdownlist.setup.statusId.url
      if (this.data.pageflow == "Inquiry New - H") {
        this.inquiryModeIdList = this.cas.foreachlist(results[0], this.cas.dropdownlist.setup.inquiryModeId.key)
            .filter(s => s.key != 1);
    } else {
        this.inquiryModeIdList = this.cas.foreachlist(results[0], this.cas.dropdownlist.setup.inquiryModeId.key);
    }
     // this.inquiryModeIdList = this.cas.foreachlist(results[0], this.cas.dropdownlist.setup.inquiryModeId.key).filter(s => s.key != 1);
      this.classIdList = this.cas.foreachlist(results[1], this.cas.dropdownlist.setup.classId.key).filter(s => s.key != 3);
      this.useridList = this.cas.foreachlist(results[2], this.cas.dropdownlist.setup.userId.key);
      this.useridList.forEach((x: { key: string; value: string; }) => this.multitimekeeperList.push({id: x.key, itemName:  x.value}))
      this.multiselecttimekeeperList = this.multitimekeeperList;
      //this.statusIdList = this.cas.foreachlist(results[3], this.cas.dropdownlist.setup.statusId.key).filter(s => [4, 5, 6, 63, 64, 65, this.form.controls.statusId.value].includes(s.key));

    }, (err) => {
      this.toastr.error(err, "");
    });
    this.spin.hide();

  }
  fill() {
    this.spin.show();
    this.sub.add(this.service.Get(this.data.code).subscribe(res => {
      res.inquiryDate = this.cs.dateapi(res.inquiryDate);
      this.form.patchValue(res, { emitEvent: false });
      // if (this.selectedItems != null && this.selectedItems != undefined && this.selectedItems.length > 0) {
      //   this.form.controls.assignedUserId.patchValue([{id: res.assignedUserId,itemName: res.assignedUserId}]);
      // }
      if (this.selectedItems != null && this.selectedItems != undefined && this.selectedItems.length > 0) {
        this.form.controls.assignedUserId.patchValue([{id: res.assignedUserId,itemName: res.assignedUserId}]);
      }
      // console.log(this.multitimekeeperList)
      // this.multitimekeeperList.forEach(element => {
      //   if(element.id == res.assignedUserId){
      //     console.log(this.form)
      //     this.form.controls.assignedUserId.patchValue([element]);
      //   }
      // });
      this.form.controls.createdOn.patchValue(this.cs.dateapiWithTimeCS(this.form.controls.createdOn.value));
      this.form.controls.updatedOn.patchValue(this.cs.dateapiWithTimeCS(this.form.controls.updatedOn.value));

      this.spin.hide();
    }, err => {
      this.cs.commonerror(err);
      this.spin.hide();
    }));


  }
  submit() {
    this.submitted = true;
    if (this.form.invalid) {
         this.toastr.error(
        "Please fill the required fields to continue",
        "Notification",{
          timeOut: 2000,
          progressBar: false,
        }
      );

      this.cs.notifyOther(true);
      return;
    }

    this.cs.notifyOther(false);
    this.spin.show();
    
    this.form.removeControl('updatedOn');
    this.form.removeControl('createdOn');
    this.form.removeControl('inquiryDate');
    if (this.selectedItems != null && this.selectedItems != undefined && this.selectedItems.length > 0) {
      this.form.patchValue({assignedUserId: this.selectedItems[0].id });
    }

    // this.form.patchValue({ updatedby: this.auth.userID, updatedOn: this.cs.todayapi() });
    if (this.data.code) {
      if (this.data.pageflow == 'Inquiry Assign')
        this.sub.add(this.service.Assign(this.form.getRawValue(), this.data.code).subscribe(res => {
          this.toastr.success(this.data.code + ' ' + this.btntxt + " successfully","Notification",{
              timeOut: 2000,
              progressBar: false,
            });
          this.spin.hide();
          this.dialogRef.close();

        }, err => {

          this.cs.commonerror(err);
          this.spin.hide();

        }));
      else if (this.data.pageflow == 'Inquiry Validation') {
        this.sub.add(this.service.updateValiationStatus
          (this.form.getRawValue(), this.data.code, this.form.controls.statusId.value).subscribe(res => {
            this.toastr.success(this.data.code + ' ' + this.btntxt + " successfully","Notification",{
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
      else
        this.sub.add(this.service.Update(this.form.getRawValue(), this.data.code).subscribe(res => {
          this.toastr.success(res.inquiryNumber + ' ' + this.btntxt + " successfully","Notification",{
              timeOut: 2000,
              progressBar: false,
            });


          // window.reload();

          this.spin.hide();
          this.dialogRef.close();
        }, err => {

          this.cs.commonerror(err);
          this.spin.hide();

        }));


    }
    else {
      // this.form.patchValue({ createdOn: this.cs.todayapi(), inquiryDate: this.cs.todayapi() });
      this.sub.add(this.service.Create(this.form.getRawValue()).subscribe(res => {

        this.toastr.success(res.inquiryNumber + " saved successfully!", "Notification", {
          timeOut: 2000,
          progressBar: false,
        });
        this.sub.add(this.service.Assign(this.form.getRawValue(),res.inquiryNumber).subscribe(result => {
          this.toastr.success(result.inquiryNumber + ' ' + this.btntxt + " successfully","Notification",{
              timeOut: 2000,
              progressBar: false,
            });
          this.spin.hide();
          this.dialogRef.close();

        }, err => {

          this.cs.commonerror(err);
          this.spin.hide();

        }));
        this.spin.hide();
        this.dialogRef.close();

        if (res.referenceField7 == 'true')
          this.toastr.success("Text Message Sent Successfully to the client", "Notification", {
            timeOut: 2000,
            progressBar: false,
          });
        else
          this.toastr.error("Text Message failed to sent", "Notification", {
            timeOut: 2000,
            progressBar: false,
          });


        let url: string = this.router.url;
        if (this.data.pageflow == 'Inquiry New - H')
          this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
            this.router.navigate([url]);
          })
      }, err => {


        this.cs.commonerror(err);
        this.spin.hide();

      }));
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

}

