import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { MaterialModule } from "./material/material.module";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { PrimeModule } from "./prime/prime.module";
import { TrimDirective } from "../trim.directive";
// import { UppercaseDirective } from "../common/directives/uppercase.directive";
// import { MatSelectSearchModule } from '../mat-select-search/mat-select-search.module';
// import { SignaturePadModule } from 'angular2-signaturepad';

@NgModule({
  declarations: [
    // UppercaseDirective
    TrimDirective
  ],
  imports: [CommonModule, MaterialModule, ],
  exports: [
    MaterialModule,
    ReactiveFormsModule,
    FormsModule,
    PrimeModule,
    TrimDirective
    // UppercaseDirective,
    // AgmDirectionModule,
    // MatSelectSearchModule,
    // SignaturePadModule
  ]
})
export class SharedModule { }
