import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PathNameService {

  private dataArraySubject = new BehaviorSubject<any[]>([]);
  dataArray$ = this.dataArraySubject.asObservable();

  constructor() {}

  setData(data: any[]): void {
    this.dataArraySubject.next(data);
  }
}