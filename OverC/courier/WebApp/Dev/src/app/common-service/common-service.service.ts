import { DatePipe } from '@angular/common';
import { Injectable } from '@angular/core';
import * as CryptoJS from 'crypto-js';
import { MessageService } from 'primeng/api';
import * as XLSX from 'xlsx';

@Injectable({
  providedIn: 'root'
})
export class CommonServiceService {

  constructor(private messageService: MessageService, private datepipe: DatePipe) { }

  encrypt(storageData: any) {
    return encodeURIComponent(CryptoJS.AES.encrypt(JSON.stringify(storageData), 'secret key 123').toString());

  }

  decrypt(storageData: any) {
    var deData = CryptoJS.AES.decrypt(decodeURIComponent(storageData), 'secret key 123');
    return JSON.parse(deData.toString(CryptoJS.enc.Utf8));
  }


  exportAsExcel(data: any, filename: string = "Downloaded_Excel") {
    const ws: XLSX.WorkSheet = XLSX.utils.json_to_sheet(data);
    // converts a DOM TABLE element to a worksheet
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');
    /* save to file */
    XLSX.writeFile(
      wb,
      filename + `_${new Date().getDate() +
      '-' +
      (new Date().getMonth() + 1) +
      '-' +
      new Date().getFullYear()
      }.xlsx`
    );
  }

  exportAsPrealertExcel(data: any, filename: string = "Downloaded_Excel") {
    const ws: XLSX.WorkSheet = XLSX.utils.json_to_sheet(data);
    // converts a DOM TABLE element to a worksheet
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'IW Manifest');
    /* save to file */
    XLSX.writeFile(
      wb,
      filename + `_${new Date().getDate() +
      '-' +
      (new Date().getMonth() + 1) +
      '-' +
      new Date().getFullYear()
      }.xlsx`
    );
  }

  downloadExcel(data: any[], filename: string = "Downloaded_Excel", groupByField: any) {
    const workbook = XLSX.utils.book_new();

    const groupedData = this.groupByField(data, groupByField);

    Object.keys(groupedData).forEach(items => {
      const sheetName = `${items}`; //ConsoleID_
      const sheetData = groupedData[items];

      const worksheet = XLSX.utils.json_to_sheet(sheetData);

      XLSX.utils.book_append_sheet(workbook, worksheet, sheetName);
    });

    XLSX.writeFile(
      workbook,
      filename + `_${new Date().getDate() +
      '-' +
      (new Date().getMonth() + 1) +
      '-' +
      new Date().getFullYear()
      }.xlsx`
    );
  }

  private groupByField(data: any[], groupByField: any): { [key: string]: any[] } {
    const groupedData: { [key: string]: any[] } = {};
    data.forEach(item => {
      const consoleId = item[groupByField];
      if (!groupedData[consoleId]) {
        groupedData[consoleId] = [];
      }
      groupedData[consoleId].push(item);
    });

    return groupedData;
  }

  public commonerrorNew(msg: any) {
    if (msg.status != 0) {
      this.messageService.add({ severity: 'error', summary: 'Error', key: 'br', detail: msg.error.error });
    }
  }

  jsonDate(date: any) {
    const del_date = this.datepipe.transform(date, 'yyyy-MM-ddT00:00:00.000');
    return del_date;
  }
  datesearch(date: any): Date {
    return new Date(date);
  }

  pCalendar(date: any): Date {
    return new Date(String(date));
  }
  dateExcel(date: any) {
    const del_date = this.datepipe.transform(date, "dd-MM-yyyy HH:mm");
    return del_date;
  }
  timeFormat(date: any) {
    const del_date = this.datepipe.transform(date, 'HH:mm a');
    return del_date;
  }

  public filterArray(targetArray: any[], filters: any) {
    var filterKeys = Object.keys(filters);
    let data = targetArray.filter(function (eachObj: any) {
      return filterKeys.every(function (eachKey) {
        if (!filters[eachKey])
          return true;
        if (!eachObj[eachKey])
          return true;
        if (eachObj[eachKey] === undefined)
          return true;
        const fo: string = String(filters[eachKey])?.toLowerCase();
        const eo: string = String(eachObj[eachKey])?.toLowerCase();
        if (fo == "")
          return true;

        const folist = fo.split(',');

        return folist.includes(eo);
      });
    });

    if (filters.createdOn_from &&
      filters.createdOn_to) {
      data = data.filter(x =>
        this.datesearch(this.jsonDate(new Date(x.createdOn.replace("+00:00", "")))) >= this.datesearch(this.jsonDate(filters.createdOn_from))
        && this.datesearch(this.jsonDate(new Date(x.createdOn.replace("+00:00", "")))) <= this.datesearch(this.jsonDate(filters.createdOn_to))
      );

    }
    return data;
  }


  removeDuplicatesFromArrayList(list: any = [], fieldName: any) {
    let dataObj: any = {};
    for (let i = 0; i < list.length; i++) {
      dataObj[list[i][fieldName]] = list[i];
    }
    list = new Array();
    for (let key in dataObj) {
      list.push(dataObj[key]);
    }
    return list;
  }

  removeDuplicatesFromArrayListMultiple(list: any[] = [], fieldNames: string[] = []) {
    let dataObj: Record<string, any> = {}; // Explicitly define dataObj type

    // Generate a unique key for each item based on fieldNames
    for (let i = 0; i < list.length; i++) {
      let key = '';
      for (let j = 0; j < fieldNames.length; j++) {
        key += list[i][fieldNames[j]] + '|'; // Concatenate field values with a delimiter
      }
      dataObj[key] = list[i];
    }

    // Convert dataObj back to array
    let uniqueList = [];
    for (let key in dataObj) {
      uniqueList.push(dataObj[key]);
    }

    return uniqueList;
  }



  removeDuplicateObj(array1: any, array2: any) {
    const array1IDs = new Set(array1.map(({ subMenuId }: any) => subMenuId));
    const combined = [
      ...array1,
      ...array2.filter(({ subMenuId }: any) => !array1IDs.has(subMenuId))
    ];
    return combined
  }

  public findInvalidControls(form: any) {
    const invalid: any[] = [];
    const controls = form.controls;
    for (const name in controls) {
      if (controls[name].invalid) {
        invalid.push(name);
      }
    }
    return invalid;
  }


  groupBy(array: any[], key: string) {
    return array.reduce((result: { [x: string]: any[]; }, currentValue: { [x: string]: any; }) => {
      const groupKey = currentValue[key];
      if (!result[groupKey]) {
        result[groupKey] = [];
      }
      result[groupKey].push(currentValue);
      return result;
    }, {});
  }


  groupByDynamicField(data: any[], field: string): any[] {
    const groupedData: { [key: string]: any } = {};

    data.forEach(record => {
      const key = record[field]; // Access the field dynamically
      if (!groupedData[key]) {
        groupedData[key] = {
          [field]: key,
          lines: []
        };
      }
      // Add the record to the appropriate array
      groupedData[key].lines.push(record);
    });

    // Convert the groupedData object into an array of grouped records
    return Object.values(groupedData);
  }

  getUniqueMenuIds(data: any[], value: any) {
    const menuIds = data.map(item => item.value);
    return Array.from(new Set(menuIds)); // Removing duplicates
  }
}
