import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';
import { AuthService } from 'src/app/core/core';

@Component({
  selector: 'app-invoice-tab',
  templateUrl: './invoice-tab.component.html',
  styleUrls: ['./invoice-tab.component.scss']
})
export class InvoiceTabComponent implements OnInit {

  link = [
    { url: "/main/threePL/profoma", title: "Proforma Invoice", screenid:3206,class: "mx-1 d-flex justify-content-center align-items-center" },
    { url: "/main/threePL/invoice", title: "Invoice", screenid:3205, class: "mx-1 d-flex justify-content-center align-items-center" }
 ];
 activeLink = this.link[0].url;
 constructor(private router: Router, private auth: AuthService) { 
  this.router.events.pipe(
    filter((event: any) => event instanceof NavigationEnd),
  ).subscribe(x => {
    this.activeLink = this.router.url;
  });
}

activeUrl1: any;
ngOnInit(): void {
  this.activeLink = this.router.url;
}


isdiabled(id: any) {
 this.auth.isMenudata();
 let fileterdata = this.auth.MenuData.filter((x: any) => x.subMenuId == id && (x.view || x.delete || x.createUpdate));
 if (fileterdata.length > 0) {
   return false;
 }
 return true;
}
}