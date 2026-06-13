import { Component, OnInit } from '@angular/core';
import Highcharts from 'highcharts';

@Component({
  selector: 'app-month-chart',
  templateUrl: './month-chart.component.html',
  styleUrls: ['./month-chart.component.scss']
})
export class MonthChartComponent implements OnInit {

  Highcharts = Highcharts;
  chartOptions = {};

  constructor() { }

  ngOnInit() {
    this.chartOptions = {
      chart: {
          zooming: {
              type: 'xy'
          }
      },
      title: {
          text: 'Average Monthly Order Data ',
          align: 'left'
      },
    
      xAxis: [{
          categories: [
              'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
              'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'
          ],
          crosshair: true
      }],
      yAxis: [{ // Primary yAxis
          labels: {
              format: '{value}',
              style: {
                  color: '#0000ff'
              }
          },
          title: {
              text: 'Reject',
              style: {
                  color:'#ff0000'
              }
          },
          opposite: true
      }, { // Secondary yAxis
          gridLineWidth: 0,
          title: {
              text: 'Returns',
              style: {
                  color: 'rgb(109, 104, 222)'
              }
          },
          labels: {
              format: '{value} ',
              style: {
                  color: 'rgb(109, 104, 222)'
              }
          }
      }, { // Tertiary yAxis
          gridLineWidth: 0,
          title: {
              text: 'Reject',
              style: {
                  color: 'rgb(109, 104, 202)'
              }
          },
          labels: {
              format: '{value} ',
              style: {
                  color: 'rgb(109, 104, 202)'
              }
          },
          opposite: true
      }],
      tooltip: {
          shared: true
      },
      legend: {
          layout: 'vertical',
          align: 'left',
          x: 80,
          verticalAlign: 'top',
          y: 55,
          floating: true,
          backgroundColor:
             'rgba(255,255,255,0.25)'
      },
      series: [{
          name: 'Order Received',
          type: 'column',
          yAxis: 1,
          data: [
              250,280,320,350, 300, 320, 330,370, 450, 550, 600, 700,
           
          ],
          tooltip: {
              valueSuffix: ''
          }
      }, {
          name: 'Returns',
          type: 'spline',
          yAxis: 2,
          data: [
            5, 2, 7,10, 6, 12, 2, 18, 20, 12, 10,6
          ],
          marker: {
              enabled: false
          },
          dashStyle: 'shortdot',
          tooltip: {
              valueSuffix: ' '
          }
      }, {
          name: 'Reject',
          type: 'spline',
          data: [
              2, 5, 10, 8, 7, 10, 12, 10, 20, 25, 22,26
          ],
          tooltip: {
              valueSuffix: ''
          }
      }],
      responsive: {
          rules: [{
              condition: {
                  maxWidth: 500
              },
              chartOptions: {
                  legend: {
                      floating: false,
                      layout: 'horizontal',
                      align: 'center',
                      verticalAlign: 'bottom',
                      x: 0,
                      y: 0
                  },
                  yAxis: [{
                      labels: {
                          align: 'right',
                          x: 0,
                          y: -6
                      },
                      showLastLabel: false
                  }, {
                      labels: {
                          align: 'left',
                          x: 0,
                          y: -6
                      },
                      showLastLabel: false
                  }, {
                      visible: false
                  }]
              }
          }]
      }
  };
  
    
  }
  }


