import { Component, OnInit } from '@angular/core';

const dataArray: any[] = [
  { date: new Date(), time: '10:20', aisle: 'A1', id: 'AD-211', location: 'Available', remarks: '' },
  { date: new Date(), time: '10:22', aisle: 'A1', id: 'AD-211', location: 'Available', remarks: '' },
  { date: new Date(), time: '10:24', aisle: 'A1', id: 'AD-211', location: 'Blocked', remarks: 'Aisle Blocked' },
  { date: new Date(), time: '10:26', aisle: 'A1', id: 'AD-211', location: 'Blocked', remarks: 'Aisle Blocked' },
  { date: new Date(), time: '10:28', aisle: 'A1', id: 'AD-211', location: 'Blocked', remarks: 'Aisle Blocked' },
  { date: new Date(), time: '10:30', aisle: 'A1', id: 'AD-211', location: 'Blocked', remarks: 'Aisle Blocked' },
  { date: new Date(), time: '10:32', aisle: 'A1', id: 'AD-211', location: 'Blocked', remarks: 'Aisle Blocked' },
];

@Component({
  selector: 'app-putaway-rejected',
  templateUrl: './rejected-reason.component.html',
  styleUrls: ['./rejected-reason.component.scss']
})
export class RejectedReasonComponent implements OnInit {

  constructor() { }

  dataArray: any[] = [];
  ngOnInit(): void {
    let currentIndex = 0;

    const pushElementAtInterval = () => {
        this.dataArray.push(dataArray[currentIndex]);
      // Move to the next element
      currentIndex++;

      // Check if we've reached the end of the array
      if (currentIndex === dataArray.length) {
        clearInterval(intervalId); // Stop the interval when the array ends
        return;
      }
    };

    // Start pushing elements at intervals
    const intervalId = setInterval(pushElementAtInterval, 2000); // 10000 milliseconds = 10 seconds

  }




}
