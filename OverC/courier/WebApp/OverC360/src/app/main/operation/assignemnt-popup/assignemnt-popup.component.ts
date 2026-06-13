import { Component } from '@angular/core';

@Component({
  selector: 'app-assignemnt-popup',
  templateUrl: './assignemnt-popup.component.html',
  styleUrl: './assignemnt-popup.component.scss'
})
export class AssignemntPopupComponent {
  raiders = [
    {
      name: 'Marvin McKinney',
      details: 'Experienced rider with 5 years on the road.',
      phone:'(229) 555-0109',
      delivered:12,
      assigned:17,
      eta:'2.5 ',
      currentLocation:' Salmiya',
    },
    {
      name: 'Mark',
      details: 'Skilled rider specializing in long-distance deliveries.',
      phone:'(229) 555-0109',
      delivered:12,
      assigned:17,
      eta:'2.5 ',
      currentLocation:'Salmiya',
    },
    {
      name: 'Mark',
      details: 'Skilled rider specializing in long-distance deliveries.',
      phone:'(229) 555-0109',
      delivered:12,
      assigned:17,
      eta:'2.5 ',
      currentLocation:'Salmiya',
    }
    // Add more riders as needed
  ];

}
