import { Component, OnInit } from '@angular/core';
import Highcharts from 'highcharts';

@Component({
  selector: 'app-new-chart',
  templateUrl: './new-chart.component.html',
  styleUrls: ['./new-chart.component.scss']
})
export class NewChartComponent implements OnInit {

 
  Highcharts = Highcharts;
  chartOptions = {};

  constructor() { }

  ngOnInit() {
    this.chartOptions = {
      title: {
          text: 'Order Received'
      },
  
      accessibility: {
          point: {
              valueDescriptionFormat: '{xDescription}{separator}{value} number(s)'
          }
      },
  
      xAxis: {
          title: {
              text: 'Fiscal Quarter'
          },
          categories: ['Q1', 'Q2', 'Q3', 'Q4']
      },
  
      yAxis: {
          type: 'logarithmic',
          title: {
              text: 'Number of Orders Received'
          }
      },
  
      tooltip: {
          headerFormat: '<b>{series.name}</b><br />',
          pointFormat: '{point.y} number(s)'
      },
  
      series: [{
          name: 'Order Received',
          keys: ['y', 'color'],
          data: [
              [1200, '#0000ff'],
              [1302, '#8d0073'],
              [2300, '#ba0046'],
              [2500, '#d60028'],
             
          ],
          color: {
              linearGradient: {
                  x1: 0,
                  x2: 0,
                  y1: 1,
                  y2: 0
              },
              stops: [
                  [0, '#0000ff'],
                  [1, '#ff0000']
              ]
          }
      }]
  };
  
    
  }
  }

