import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';



@Component({
  selector: 'app-putaway-rejected',
  templateUrl: './putaway-rejected.component.html',
  styleUrls: ['./putaway-rejected.component.scss']
})
export class PutawayRejectedComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any,) { }

  dataArray: any[] = [];
  ngOnInit(): void {
    
    const dataArray: any[] = [
      {date: (new Date(this.data.createdOn).setDate(new Date(this.data.createdOn).getDate() - 10)), qty: 20, class: 'B'},
      {date: (new Date(this.data.createdOn).setDate(new Date(this.data.createdOn).getDate() - 9)), qty: 10, class: 'B'},
      {date: (new Date(this.data.createdOn).setDate(new Date(this.data.createdOn).getDate() - 8)), qty: 0, class: 'B'},
      {date: (new Date(this.data.createdOn).setDate(new Date(this.data.createdOn).getDate() - 7)), qty: 30, class: 'B'},
      {date: (new Date(this.data.createdOn).setDate(new Date(this.data.createdOn).getDate() - 6)), qty: 0, class: 'B'},
      {date: (new Date(this.data.createdOn).setDate(new Date(this.data.createdOn).getDate() - 5)), qty: 10, class: 'B'},
      {date: (new Date(this.data.createdOn).setDate(new Date(this.data.createdOn).getDate() - 4)), qty: 50, class: 'B'},
      {date: (new Date(this.data.createdOn).setDate(new Date(this.data.createdOn).getDate() - 3)), qty: 70, class: 'B'},
      {date: (new Date(this.data.createdOn).setDate(new Date(this.data.createdOn).getDate() - 2)), qty: 90, class: 'B'},
      {date: (new Date(this.data.createdOn).setDate(new Date(this.data.createdOn).getDate() - 1)), qty: 120, class: 'A'},
      {date: this.data.createdOn, qty: 140, class: 'A'}
    ];

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
