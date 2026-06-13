import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-fast-moving-panel',
  templateUrl: './fast-moving-panel.component.html',
  styleUrls: ['./fast-moving-panel.component.scss']
})
export class FastMovingPanelComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
    const squares = Array.from({ length: 10 }, (_, i) => i + 1);
  }

}
