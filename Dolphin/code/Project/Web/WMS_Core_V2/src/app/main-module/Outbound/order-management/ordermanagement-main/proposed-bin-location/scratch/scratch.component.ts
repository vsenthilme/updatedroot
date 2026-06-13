import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-scratch',
  templateUrl: './scratch.component.html',
  styleUrls: ['./scratch.component.scss']
})
export class ScratchComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
    this.runPreset = false;
  }

runPreset: boolean;
  run(){
    this.runPreset = !this.runPreset;
  }

  pauseOrPlay(video){
    video.pause();
}
}
