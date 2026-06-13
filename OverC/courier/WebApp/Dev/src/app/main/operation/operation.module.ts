import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OperationRoutingModule } from './operation-routing.module';
import { SharedModule } from '../../shared/shared.module';
import { ConsignmentComponent } from './consignment/consignment.component';
import { ConsignmentNewComponent } from './consignment/consignment-new/consignment-new.component';
import { ItemDetailsComponent } from './consignment/consignment-new/item-details/item-details.component';
import { DimensionComponent } from './consignment/consignment-new/dimension/dimension.component';
import { ImageUploadComponent } from './consignment/consignment-new/image-upload/image-upload.component';
import { ConsignmentUpdatebulkComponent } from './consignment/consignment-updatebulk/consignment-updatebulk.component';
import { ConsignmentStatusPopupComponent } from './consignment/consignment-new/consignment-status-popup/consignment-status-popup.component';
import { DownloadTemplateComponent } from './consignment/download-template/download-template.component';
import { AssignmentComponent } from './assignment/assignment.component';
import { AssignemntPopupComponent } from './assignemnt-popup/assignemnt-popup.component';


@NgModule({
  declarations: [
    ConsignmentComponent,
    ConsignmentNewComponent,
    ItemDetailsComponent,
    DimensionComponent,
    ImageUploadComponent,
    ConsignmentUpdatebulkComponent,
    ConsignmentStatusPopupComponent,
    DownloadTemplateComponent,
    AssignmentComponent,
    AssignemntPopupComponent,
  ],
  imports: [
    CommonModule,
    OperationRoutingModule,
    SharedModule
  ]
})
export class OperationModule { }
