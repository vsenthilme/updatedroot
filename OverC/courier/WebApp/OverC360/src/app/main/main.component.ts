import { trigger, state, style, transition, animate } from '@angular/animations';
import { BreakpointObserver } from '@angular/cdk/layout';
import { ChangeDetectorRef, Component, EventEmitter, Output, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { PathNameService } from '../common-service/path-name.service';
import { Subscription, filter } from 'rxjs';
import { INavbarData, navbarData } from './nav-data';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { Title } from '@angular/platform-browser';
import { AuthService } from '../core/core';

interface SideNavToggle {
  screenWidth: number;
  collapse: boolean;
}

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrl: './main.component.scss',
  animations: [
    trigger('fadeLater', [
      state('fade-in', style({ opacity: 1, transform: 'translateX(0)' })),
      state('fade-out', style({ opacity: 0, transform: 'translateX(-70px)' })),
      transition('fade-in <=> fade-out', animate('0.6s ease-in-out'))
    ]),
    trigger('move360', [
      state('move-left', style({ transform: 'translateX(0)' })),
      state('move-center', style({ transform: 'translateX(-70px)' })),
      transition('move-left <=> move-center', animate('0.6s ease-in-out'))
    ]),
    trigger('fadeText', [
      state('fade-in', style({ opacity: 1, transform: 'translateX(0)' })),
      state('fade-out', style({ opacity: 0, transform: 'translateX(70px)' })),
      transition('fade-in <=> fade-out', animate('0.7s ease-in-out'))
    ]),
  ]
})
export class MainComponent {

  isVisible = false;

  toggleVisibility() {
    this.isVisible = !this.isVisible;
  }
  title = 'material-responsive-sidenav';
  // @ViewChild(MatSidenav)
  // sidenav!: MatSidenav;

  isMobile = true;


  constructor(private observer: BreakpointObserver, private path: PathNameService, private router: Router, 
    private cdRef: ChangeDetectorRef, public auth: AuthService, private titleService: Title, private activatedRoute: ActivatedRoute,
  ) {

    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd),
    )
      .subscribe(() => {

        var rt = this.getChild(this.activatedRoute)

        rt.data.subscribe((data: { module: string, title: string }) => {

          this.titleService.setTitle("Overc360 - " + data.module)
        })
      });
  }

  dataArray: any[] = [];
  private dataSubscription: Subscription = new Subscription();

  getChild(activatedRoute: ActivatedRoute): any {
    if (activatedRoute.firstChild) {
      return this.getChild(activatedRoute.firstChild);
    } else {
      return activatedRoute;
    }

  }

  ngOnInit() {

    this.dataSubscription = this.path.dataArray$.subscribe(data => {
      this.dataArray = data;
      this.cdRef.detectChanges();

    });

    this.observer.observe(['(max-width: 800px)']).subscribe((screenSize) => {
      if (screenSize.matches) {
        this.isMobile = true;
      } else {
        this.isMobile = false;
      }
    });

    this.updateActiveLink();
  }
  ngOnDestroy(): void {
    this.dataSubscription.unsubscribe();
  }
  isCollapsed = true;

  // toggleMenu() {
  //   this.sidenav.open(); // On desktop/tablet, the menu can never be fully closed
  //   this.isCollapsed = !this.isCollapsed;
  // }

  expanded = false;

  toggleSidebar() {
    this.expanded = !this.expanded;
  }

  // This is the current code for the collapse menu
  @Output() onToggleSideNav: EventEmitter<SideNavToggle> = new EventEmitter();
  collapsed = false;
  screenWidth = 0;
  navData = navbarData;
  multiple: boolean = false;
  activeLinkIndex: number | null = null;
  selectedItem: INavbarData | null = null;
  setActive: boolean = false;
  activeIcons: boolean[] = [];

  toggleCollapse(): void {
    this.collapsed = !this.collapsed;
    this.onToggleSideNav.emit({ collapse: this.collapsed, screenWidth: this.screenWidth });
  }

  // This method checks if any subnav is active
  isAnySubnavActive(): boolean {
    return this.navData.some(data => data.items?.some(item => item.expanded));
  }

  // Method to close all menus
  closeAllMenus(): void {
    if (!this.collapsed) {
      this.closeSidenav();
    }
    this.collapseSublevelMenus();
  }

  // Collapse all sublevel menus
  private collapseSublevelMenus(): void {
    this.navData.forEach(item => {
      if (item.items) {
        item.expanded = false; // Collapse the parent menu
        item.items.forEach(subItem => {
          subItem.expanded = false; // Collapse sublevel menus
        });
      }
    });
  }

  closeSidenav(): void {
    this.collapsed = false;
    this.onToggleSideNav.emit({ collapse: this.collapsed, screenWidth: this.screenWidth });
  }

  showIcon1 = true;

  toggleIcon() {
    this.showIcon1 = !this.showIcon1;
  }

  // handleClick(item: INavbarData, index: number): void {
  //   if (!this.multiple) {
  //     for (let modelItem of this.navData) {
  //       if (item !== modelItem && modelItem.expanded) {
  //         modelItem.expanded = false;
  //       }
  //     }
  //   }
  //   item.expanded = !item.expanded
  //   this.selectedItem = item;
  //   this.setActiveLink(index);
  // }

  // setActiveLink(index: number) {
  //   this.activeLinkIndex = index;
  //   this.activeIcons.fill(false);
  //   this.activeIcons[index] = true;

  // }

  // isActive(index: number): boolean {
  //   return this.activeIcons[index];
  // }

  handleClick(item: INavbarData, index: number): void {
    if (!this.multiple) {
      this.navData.forEach(modelItem => {
        if (item !== modelItem && modelItem.expanded) {
          modelItem.expanded = false;
        }
      });
    }
    item.expanded = !item.expanded;
    this.setActiveLink(index);
  }

  setActiveLink(index: number) {
    this.activeLinkIndex = index;
  }

  isActive(index: number): boolean {
    return this.activeLinkIndex === index;
  }

  private updateActiveLink() {
    const currentUrl = this.router.url;
    const matchingNavItemIndex = this.navData.findIndex(navItem => {
      return navItem.routerLink && currentUrl.includes(navItem.routerLink);
    });
    if (matchingNavItemIndex !== -1) {
      this.setActiveLink(matchingNavItemIndex);
    } else {
      this.activeLinkIndex = null;
    }
  }

  logout() {
    this.auth.logout();
  }

}