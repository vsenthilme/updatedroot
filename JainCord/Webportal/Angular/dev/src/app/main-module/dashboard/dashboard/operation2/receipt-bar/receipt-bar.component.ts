



  import { Component, OnInit } from '@angular/core';
  import * as Highcharts from 'highcharts';// import highcharts3d from "highcharts/highcharts-3d";
  import cylinder from "highcharts/modules/cylinder";
  import highcharts3d from "highcharts/highcharts-3d";
  highcharts3d(Highcharts);
  cylinder(Highcharts);
  import lollipop from 'highcharts/modules/lollipop.js';
import { color } from 'd3';
  //lollipop(Highcharts);
  
  
  //dumbbell(Highcharts)
  
  //let indicators = require('highcharts/modules/lollipop');
  
  // import item from 'highcharts/modules/item-series.js';
  // item(Highcharts);
  
  
  // import highcharts3d from "highcharts/highcharts-3d";
  // import cylinder from "highcharts/modules/cylinder";
  
  // highcharts3d(Highcharts);
  // cylinder(Highcharts);


  @Component({
    selector: 'app-receipt-bar',
    templateUrl: './receipt-bar.component.html',
    styleUrls: ['./receipt-bar.component.scss']
  })
  export class ReceiptBarComponent implements OnInit {
  
    Highcharts = Highcharts;
    chartOptions = {};
  
    constructor() { }
  
    ngOnInit() {
      this.chartOptions = {
        colors: ['#1E90FF', '#FF6347'],
        chart: {
          type: 'column',
          inverted: true,
          polar: true,
          backgroundColor: 'transparent',
          animation: {
            duration: 1500,
            easing: 'easeOutBounce'
          }
        },
        title: {
          text: 'Receipts',
          align: 'left'
        },
        tooltip: {
          outside: true
        },
        pane: {
          size: '85%',
          innerSize: '20%',
          endAngle: 270
        },
        xAxis: {
          tickInterval: 1,
          labels: {
            align: 'right',
            useHTML: true,
            allowOverlap: true,
            step: 1,
            y: 3,
            style: {
              fontSize: '13px',
              color: "black",
              font:'bold'
            }
          },
          lineWidth: 0,
          gridLineWidth: 0,
          categories: [
            'Container Received',
            'Awaiting for ASN'
          ]
        },
        yAxis: {
          lineWidth: 0,
          tickInterval: 1,
          reversedStacks: false,
          endOnTick: true,
          showLastLabel: true,
          gridLineWidth: 0
        },
        plotOptions: {
          column: {
            stacking: 'normal',
            borderWidth: 0,
            pointPadding: 0,
            groupPadding: 0.15,
            borderRadius: '50%'
          }
        },
        series: [{
          name: 'Container Received',
          data: [4, 0], // Ensure it starts from 0
          color: '#1E90FF' // Contrasting color
        }, {
          name: 'Awaiting for ASN',
          data: [0, 1], // Ensure it starts from 0
          color: '#FF6347' // Contrasting color
        }]
      };
      
    }
    }
  
  