import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private http: HttpClient) { }

  findConsignment(obj :any){
    return this.http.post<any>('/b2b-spark-service/consignment', obj)
  }
}
