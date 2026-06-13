import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';
import { AuthService } from 'src/app/core/core';

@Component({
  selector: 'app-manufactur-tab',
  templateUrl: './manufactur-tab.component.html',
  styleUrls: ['./manufactur-tab.component.scss']
})
export class ManufacturTabComponent implements OnInit {

  
  link = [
    { url: "/main/manufacturing/orderDetails", title: "Order Details",  screenid: 3195, class: "mx-1 d-flex justify-content-center align-items-center" },
    { url: "/main/manufacturing/cutting", title: "Operation 1",  screenid: 3196, class: "mx-1 d-flex justify-content-center align-items-center" },
    { url: "/main/manufacturing/modling", title: "Operation 2",  screenid: 3197, class: "mx-1 d-flex justify-content-center align-items-center" },
    { url: "/main/manufacturing/fabrication", title: "Operation 3",  screenid: 3198, class: "mx-1 d-flex justify-content-center align-items-center" },
    { url: "/main/manufacturing/assembly", title: "Operation 4",  screenid: 3199, class: "mx-1 d-flex justify-content-center align-items-center" },
    { url: "/main/manufacturing/delivery", title: "Delivery",  screenid: 3200, class: "mx-1 d-flex justify-content-center align-items-center" },
    { url: "/main/manufacturing/productionOrderSfg", title: "Production Order - SFG",  screenid: 3228, class: "mx-1 d-flex justify-content-center align-items-center" },
    { url: "/main/manufacturing/productionOrderFg", title: "Production Order - FG",  screenid: 3228, class: "mx-1 d-flex justify-content-center align-items-center" },

 ];
 activeLink = this.link[0].url;
 
 constructor(private router: Router, private auth: AuthService) { 
   this.router.events.pipe(
     filter((event: any) => event instanceof NavigationEnd),
   ).subscribe(x => {
     this.activeLink = this.router.url;
   });
 }

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
