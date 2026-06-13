import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PrimengModule } from './prime/primeng/primeng.module';
import { MaterialModule } from './material/material/material.module';
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { TrimDirective } from '../trim.directive';
import { TrimFilterDirective } from '../trim-filter.directive';


@NgModule({
  declarations: [
    TrimDirective,
    TrimFilterDirective
  ],
  imports: [
    CommonModule,
    PrimengModule,
    MaterialModule,
  ],
  exports: [
    PrimengModule,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule,
    TrimDirective,
    TrimFilterDirective
  ]
})
export class SharedModule { }
