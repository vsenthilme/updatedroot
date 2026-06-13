import { Component, OnInit, Inject } from "@angular/core";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { MatTableDataSource } from "@angular/material/table";
import { UserProfileService } from "src/app/main-module/setting/admin/user-profile/user-profile.service";

@Component({
  selector: 'app-add-string-array-popup',
  templateUrl: './add-string-array-popup.component.html',
  styleUrls: ['./add-string-array-popup.component.scss']
})
export class AddStringArrayPopupComponent implements OnInit {
  constructor(
    public dialogRef: MatDialogRef<AddStringArrayPopupComponent>,
    private service : UserProfileService,
    @Inject(MAT_DIALOG_DATA) public data: any) { }

    displayedColumns: string[] = ['sino', 'email'];;
    dataSource = new MatTableDataSource<any>([]);

  ngOnInit(): void {
    console.log(this.data)
    this.getEmail();
  }

  selectEmail: any[ ] = [];
getEmail(){
  this.service.Getall().subscribe(res =>{
    res.forEach((x: { emailId: string; fullName: string; }) => this.selectEmail.push({ value: x.emailId, label: x.fullName + '-' + x.emailId }));
    this.dataSource = new MatTableDataSource<any>(this.data);
  })
}

  onNoClick(): void {
    this.dialogRef.close(this.dataSource.data);
  }

  addEmail() {
    this.dataSource.data.push({email:''});
    this.dataSource._updateChangeSubscription();
  }
  removeEmail(index:any,element:any) {
    console.log(element)
    this.dataSource.data.splice(0, -1);
  }

}
