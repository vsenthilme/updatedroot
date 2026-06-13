import { animate, state, style, transition, trigger } from '@angular/animations';
import { Component, Input } from '@angular/core';
import { INavbarData } from './nav-data';
import { IsActiveMatchOptions, Router } from '@angular/router';

@Component({
  selector: 'app-sublevel-menu',
  template: `
    <ul *ngIf="collapsed && data.items && data.items.length > 0 "
      [@submenu]="expanded
      ? {value: 'visible', 
          params: {transitionParams: '400ms cubic-bezier(0.86, 0, 0.07, 1)', height: '*'}} 
        : {value: 'hidden',
          params: {transitionParams: '400ms cubic-bezier(0.86, 0, 0.07, 1)', height: '0'}}"  
      class="sublevel-nav">
      <li *ngFor="let item of data.items" class="sublevel-nav-item" [class.expanded]="item.expanded" (click)="handleClick(item)"  [routerLink]="item.routerLink">
          <div class="listSelector" [ngClass]="{'active': isActive(item.routerLink)}" ></div>
          <div class="sublevel-nav-box">
            <a class="sublevel-nav-link"
            *ngIf="item.items && item.items.length > 0"
            > 
              <span class="sublevel-link-text" *ngIf="collapsed">{{item.label}}</span>
              <mat-icon class="arrow-icon" *ngIf="item.items && item.items.length > 0">keyboard_arrow_right</mat-icon>
            </a>
          </div>
          <a class="sublevel-nav-link"
            *ngIf="!item.items || (item.items && item.items.length === 0)"
            routerLinkActive="active-sublevel"
            [routerLinkActiveOptions]="{exact: true}"
          >
            <span class="sublevel-link-text" *ngIf="collapsed">{{item.label}}</span>
          </a>
          <div class="sublevel-nav-link-container" *ngIf="item.items && item.items.length > 0">
            <app-sublevel-menu
            [collapsed]="collapsed"
            [multiple]="multiple"
            [expanded]="item.expanded"
            [data]="item"
            >
            </app-sublevel-menu>
          </div>
      </li>
    </ul>
  `,
  styleUrl: './main.component.scss',
  animations: [
    trigger('submenu', [
      state('hidden', style({
        height: '0',
        overflow: 'hidden'
      })),
      state('visible', style({
        height: '*',
      })),
      transition('visible <=> hidden', [style({ overflow: 'hidden' }),
      animate('{{transitionParams}}')
      ]),
      transition('void => *', animate(0))
    ])
  ]
})
export class SublevelMenuComponent {

  @Input() data: INavbarData = {
    routerId: 1,
    routerLink: '',
    src: '',
    srcDark: '',
    label: '',
    items: []
  }
  @Input() collapsed = false;
  @Input() animating: boolean | undefined;
  @Input() expanded: boolean | undefined;
  @Input() multiple: boolean | undefined;

  constructor(private router: Router) { }

  ngOnInit(): void {


  }

  handleClick(item: any): void {
    if (!this.multiple) {
      if (this.data.items && this.data.items.length > 0) {
        for (let modelItem of this.data.items) {
          if (item !== modelItem && modelItem.expanded) {
            modelItem.expanded = false;
          }
        }
      }
    }
    item.expanded = !item.expanded;
  }

  isActive(routerLink?: string): boolean {
    if (!routerLink) {
      return false;
    }
    const options: IsActiveMatchOptions = {
      paths: 'exact',
      queryParams: 'ignored',
      matrixParams: 'exact',
      fragment: 'exact'
    };
    return this.router.isActive(routerLink, options);
  }


}
