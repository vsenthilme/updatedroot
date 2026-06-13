import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS, provideHttpClient, withInterceptors } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LocationStrategy, HashLocationStrategy, CommonModule, PathLocationStrategy, DatePipe, DecimalPipe } from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SharedModule } from './shared/shared.module';
import { httpInterceptorProviders } from './core/httpInterceptorProviders';
import { NgxBarcode6Module } from 'ngx-barcode6';
import { MessageService } from 'primeng/api';
import { DeleteComponent } from './common-dialog/delete/delete.component';
import { NgxSpinnerModule } from "ngx-spinner";
import { CustomTableComponent } from './common-dialog/custom-table/custom-table.component';


import { initializeApp } from 'firebase/app';
import { environment } from '../environments/environment';
initializeApp(environment.firebase);

@NgModule({
  declarations: [
    AppComponent,
    DeleteComponent,
    CustomTableComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    SharedModule,
    BrowserAnimationsModule,
    HttpClientModule,
    NgxBarcode6Module,
    NgxSpinnerModule
  ],
  providers: [
    MessageService, DatePipe, DecimalPipe,
    { provide: LocationStrategy, useClass: HashLocationStrategy },
    httpInterceptorProviders
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
