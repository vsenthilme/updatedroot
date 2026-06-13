import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OtherMastersRoutingModule } from './other-masters-routing.module';
import { HandlingEquipmentComponent } from './handling-equipment/handling-equipment.component';
import { CommonFieldModule } from 'src/app/common-field/common-field.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { EquipmentNewComponent } from './handling-equipment/equipment-new/equipment-new.component';
import { HandlingUnitComponent } from './handling-unit/handling-unit.component';
import { UnitNewComponent } from './handling-unit/unit-new/unit-new.component';
import { BomComponent } from './bom/bom.component';
import { BomNewComponent } from './bom/bom-new/bom-new.component';
import { BusinessPartnerComponent } from './business-partner/business-partner.component';
import { BusinessNewComponent } from './business-partner/business-new/business-new.component';
import { PackingMaterialComponent } from './packing-material/packing-material.component';
import { PackingNewComponent } from './packing-material/packing-new/packing-new.component';
import { NgMultiSelectDropDownModule } from 'ng-multiselect-dropdown';
import { ChilditemcodeComponent } from './bom/bom-new/childitemcode/childitemcode.component';
import { AngularMultiSelectModule } from 'angular2-multiselect-dropdown';
import { OthermasterTabComponent } from './othermaster-tab/othermaster-tab.component';
import { DockComponent } from './dock/dock.component';
import { DockNewComponent } from './dock/dock-new/dock-new.component';
import { WorkcenterComponent } from './workcenter/workcenter.component';
import { WorkcenterNewComponent } from './workcenter/workcenter-new/workcenter-new.component';
import { NumberrangeitemComponent } from './numberrangeitem/numberrangeitem.component';
import { NumberrangeitemNewComponent } from './numberrangeitem/numberrangeitem-new/numberrangeitem-new.component';
import { CyclecountschedularComponent } from './cyclecountschedular/cyclecountschedular.component';
import { CyclecountschedularNewComponent } from './cyclecountschedular/cyclecountschedular-new/cyclecountschedular-new.component';
import { NumberrangestoragebinComponent } from './numberrangestoragebin/numberrangestoragebin.component';
import { NumberrangestoragebinNewComponent } from './numberrangestoragebin/numberrangestoragebin-new/numberrangestoragebin-new.component';
import { BomTableComponent } from './bom/bom-table/bom-table.component';
import { MasterrecipeComponent } from './masterrecipe/masterrecipe.component';
import { MasterrecipeNewComponent } from './masterrecipe/masterrecipe-new/masterrecipe-new.component';
import { MasterchildComponent } from './masterrecipe/masterrecipe-new/masterchild/masterchild.component';
import { MasterrecipeTableComponent } from './masterrecipe/masterrecipe-table/masterrecipe-table.component';
import { MasteroperationsComponent } from './masteroperations/masteroperations.component';
import { MasteroperationNewComponent } from './masteroperations/masteroperation-new/masteroperation-new.component';
import { MasteropoerationLinesComponent } from './masteroperations/masteroperation-new/masteropoeration-lines/masteropoeration-lines.component';
import { ReceipeComponent } from './receipe/receipe.component';
import { RecipeNewComponent } from './receipe/recipe-new/recipe-new.component';
import { RecipeChildComponent } from './receipe/recipe-new/recipe-child/recipe-child.component';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from 'ng-pick-datetime';
import { ProcessComponent } from './indus/process/process.component';
import { ProcessNewComponent } from './indus/process/process-new/process-new.component';
// import { CompanyComponent } from './company/company.component';
// import { CompanyNewComponent } from './company/company-new/company-new.component';


@NgModule({
  declarations: [
    HandlingEquipmentComponent,
    EquipmentNewComponent,
    HandlingUnitComponent,
    UnitNewComponent,
    BomComponent,
    BomNewComponent,
    BusinessPartnerComponent,
    BusinessNewComponent,
    PackingMaterialComponent,
    PackingNewComponent,
    ChilditemcodeComponent,
    OthermasterTabComponent,
    DockComponent,
    DockNewComponent,
    WorkcenterComponent,
    WorkcenterNewComponent,
    NumberrangeitemComponent,
    NumberrangeitemNewComponent,
    CyclecountschedularComponent,
    CyclecountschedularNewComponent,
    NumberrangestoragebinComponent,
    NumberrangestoragebinNewComponent,
    BomTableComponent,
    MasterrecipeComponent,
    MasterrecipeNewComponent,
    MasterchildComponent,
    MasterrecipeTableComponent,
    MasteroperationsComponent,
    MasteroperationNewComponent,
    MasteropoerationLinesComponent,
    ReceipeComponent,
    RecipeNewComponent,
    RecipeChildComponent,
    ProcessComponent,
    ProcessNewComponent,
    // CompanyComponent,
    // CompanyNewComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    OtherMastersRoutingModule,
    CommonFieldModule,
    NgMultiSelectDropDownModule.forRoot(),
    AngularMultiSelectModule,
    OwlDateTimeModule, 
    OwlNativeDateTimeModule,
  ],

  exports: [
    HandlingEquipmentComponent,
    EquipmentNewComponent,
    HandlingUnitComponent,
    UnitNewComponent,
    BomComponent,
    BomNewComponent,
    BusinessPartnerComponent,
    BusinessNewComponent,
    PackingMaterialComponent,
    PackingNewComponent,
    OthermasterTabComponent,
    //  CompanyComponent
  ]
})
export class OtherMastersModule { }
