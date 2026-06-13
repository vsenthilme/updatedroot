import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CompanyComponent } from './company/company.component';
import { LanguageComponent } from './language/language.component';
import { LanguageNewComponent } from './language/language-new/language-new.component';
import { ConsignmentTypeComponent } from './consignment-type/consignment-type.component';
import { ConsignmentTypeNewComponent } from './consignment-type/consignment-type-new/consignment-type-new.component';
import { MenuComponent } from './menu/menu.component';
import { MenuNewComponent } from './menu/menu-new/menu-new.component';
import { StatusNewComponent } from './status/status-new/status-new.component';
import { StatusComponent } from './status/status.component';
import { CurrencyNewComponent } from './currency/currency-new/currency-new.component';
import { CurrencyComponent } from './currency/currency.component';
import { CompanyNewComponent } from './company/company-new/company-new.component';
import { SubProductComponent } from './sub-product/sub-product.component';
import { SubProductNewComponent } from './sub-product/sub-product-new/sub-product-new.component';
import { LoadTypeComponent } from './load-type/load-type.component';
import { LoadTypeNewComponent } from './load-type/load-type-new/load-type-new.component';
import { ServiceTypeComponent } from './service-type/service-type.component';
import { ServiceTypeNewComponent } from './service-type/service-type-new/service-type-new.component';
import { ModuleComponent } from './module/module.component';
import { ModuleNewComponent } from './module/module-new/module-new.component';
import { ProvinceComponent } from './province/province.component';
import { ProvinceNewComponent } from './province/province-new/province-new.component';
import { DistrictComponent } from './district/district.component';
import { DistrictNewComponent } from './district/district-new/district-new.component';
import { CityComponent } from './city/city.component';
import { CityNewComponent } from './city/city-new/city-new.component';
import { ConsignorNewComponent } from '../master/consignor/consignor-new/consignor-new.component';
import { ConsignorComponent } from '../master/consignor/consignor.component';
import { CustomerNewComponent } from '../master/customer/customer-new/customer-new.component';
import { CustomerComponent } from '../master/customer/customer.component';
import { ProductNewComponent } from './product/product-new/product-new.component';
import { ProductComponent } from './product/product.component';
import { HsCodeNewComponent } from '../master/hs-code/hs-code-new/hs-code-new.component';
import { HsCodeComponent } from '../master/hs-code/hs-code.component';
import { NumberrangeNewComponent } from './numberrange/numberrange-new/numberrange-new.component';
import { NumberrangeComponent } from './numberrange/numberrange.component';
import { OpstatusNewComponent } from '../master/opstatus/opstatus-new/opstatus-new.component';
import { OpstatusComponent } from '../master/opstatus/opstatus.component';
import { SpecialApprovalComponent } from '../master/special-approval/special-approval.component';
import { SpecialApprovalNewComponent } from '../master/special-approval/special-approval-new/special-approval-new.component';
import { CountryNewComponent } from './country/country-new/country-new.component';
import { CountryComponent } from './country/country.component';
import { EventNewComponent } from '../master/event/event-new/event-new.component';
import { EventComponent } from '../master/event/event.component';
import { NotificationNewComponent } from './notification/notification-new/notification-new.component';
import { NotificationComponent } from './notification/notification.component';
import { CurrencyExchangeRateComponent } from '../master/currency-exchange-rate/currency-exchange-rate.component';
import { CurrencyExchangeRateNewComponent } from '../master/currency-exchange-rate/currency-exchange-rate-new/currency-exchange-rate-new.component';
import { IataNewComponent } from '../master/iata/iata-new/iata-new.component';
import { IataComponent } from '../master/iata/iata.component';
import { UsersNewComponent } from './users/users-new/users-new.component';
import { UsersComponent } from './users/users.component';
import { RateParameterNewComponent } from './rate-parameter/rate-parameter-new/rate-parameter-new.component';
import { RateParameterComponent } from './rate-parameter/rate-parameter.component';
import { UserRoleComponent } from './user-role/user-role.component';
import { UserRoleNewComponent } from './user-role/user-role-new/user-role-new.component';
import { StatusEventComponent } from './status-event/status-event.component';
import { StatusEventNewComponent } from './status-event/status-event-new/status-event-new.component';
import { UomComponent } from './uom/uom.component';
import { UomNewComponent } from './uom/uom-new/uom-new.component';
import { AppUserComponent } from './app-user/app-user.component';
import { AppUserNewComponent } from './app-user/app-user-new/app-user-new.component';

const routes: Routes = [

  { path: 'company', component: CompanyComponent, data: { title: 'Setup', module: 'Company' } },
  { path: 'company-new/:code', component: CompanyNewComponent, data: { title: 'Setup', module: 'Company - Add New' } },

  { path: 'language', component: LanguageComponent, data: { title: 'Setup', module: 'Language' } },
  { path: 'language-new/:code', component: LanguageNewComponent, data: { title: 'Setup', module: 'Language - Add New' } },

  { path: 'consignmentType', component: ConsignmentTypeComponent, data: { title: 'Setup', module: 'ConsignmentType' } },
  { path: 'consignmentType-new/:code', component: ConsignmentTypeNewComponent, data: { title: 'Setup', module: 'ConsignmentType - Add New' } },

  { path: 'serviceType', component: ServiceTypeComponent, data: { title: 'Setup', module: 'ServiceType' } },
  { path: 'serviceType-new/:code', component: ServiceTypeNewComponent, data: { title: 'Setup', module: 'ServiceType - Add New' } },

  { path: 'menu', component: MenuComponent, data: { title: 'Setup', module: 'Menu' } },
  { path: 'menu-new/:code', component: MenuNewComponent, data: { title: 'Setup', module: 'Menu - Add New' } },

  { path: 'status', component: StatusComponent, data: { title: 'Setup', module: 'Status' } },
  { path: 'status-new/:code', component: StatusNewComponent, data: { title: 'Setup', module: 'Status - Add New' } },

  { path: 'currency', component: CurrencyComponent, data: { title: 'Setup', module: 'Currency' } },
  { path: 'currency-new/:code', component: CurrencyNewComponent, data: { title: 'Setup', module: 'Currency - Add New' } },

  { path: 'loadType', component: LoadTypeComponent, data: { title: 'Setup', module: 'LoadType' } },
  { path: 'loadType-new/:code', component: LoadTypeNewComponent, data: { title: 'Setup', module: 'LoadType - Add New' } },

  { path: 'subProduct', component: SubProductComponent, data: { title: 'Setup', module: 'Sub Product' } },
  { path: 'subProduct-new/:code', component: SubProductNewComponent, data: { title: 'Setup', module: 'Sub Product - Add New' } },

  { path: 'module', component: ModuleComponent, data: { title: 'Setup', module: 'Module' } },
  { path: 'module-new/:code', component: ModuleNewComponent, data: { title: 'Setup', module: 'Module - Add New' } },

  { path: 'province', component: ProvinceComponent, data: { title: 'Setup', module: 'Province' } },
  { path: 'province-new/:code', component: ProvinceNewComponent, data: { title: 'Setup', module: 'Province - Add New' } },

  { path: 'district', component: DistrictComponent, data: { title: 'Setup', module: 'District' } },
  { path: 'district-new/:code', component: DistrictNewComponent, data: { title: 'Setup', module: 'District - Add New' } },

  { path: 'city', component: CityComponent, data: { title: 'Setup', module: 'City' } },
  { path: 'city-new/:code', component: CityNewComponent, data: { title: 'Setup', module: 'City - Add New' } },

  { path: 'product', component: ProductComponent, data: { title: 'Setup', module: 'Product' } },
  { path: 'product-new/:code', component: ProductNewComponent, data: { title: 'Setup', module: 'Product - Add New' } },
 
  { path: 'country', component: CountryComponent, data: { title: 'Setup', module: 'Country' } },
  { path: 'country-new/:code', component: CountryNewComponent, data: { title: 'Setup', module: 'Country - Add New' } },

  { path: 'notification', component: NotificationComponent, data: { title: 'Setup', module: 'Notification' } },
  { path: 'notification-new/:code', component: NotificationNewComponent, data: { title: 'Setup', module: 'Notification - Add New' } },
 
  { path: 'users', component: UsersComponent, data: { title: 'Master', module: 'Users' } },
  { path: 'users-new/:code', component: UsersNewComponent, data: { title: 'Master', module: 'Users - Add New' } },

  { path: 'rateParameter', component: RateParameterComponent, data: { title: 'Setup', module: 'Rate Parameter' } },
  { path: 'rateParameter-new/:code', component: RateParameterNewComponent, data: { title: 'Setup', module: 'Rate Parameter - Add New' } },

  { path: 'userrole', component: UserRoleComponent, data: { title: 'Master', module: 'UserRole' } },
  { path: 'userrole-new/:code', component: UserRoleNewComponent, data: { title: 'Master', module: 'UserRole - Add New' } },

  { path: 'uom', component: UomComponent, data: { title: 'Setup', module: 'UOM' } },
  { path: 'uom-new/:code', component: UomNewComponent, data: { title: 'Setup', module: 'UOM - Add New' } },

  { path: 'appUser', component: AppUserComponent, data: { title: 'Master', module: 'AppUser' } },
  { path: 'appUser-new/:code', component: AppUserNewComponent, data: { title: 'Master', module: 'AppUser - Add New' } },

  { path: 'statusevent', component: StatusEventComponent, data: { title: 'Master', module: 'Status Event' } },
  { path: 'statusevent-new/:code', component: StatusEventNewComponent, data: { title: 'Master', module: 'Status Event - Add New' } },
  
  { path: 'numberrange', component: NumberrangeComponent, data: { title: 'Setup', module: 'NumberRange' } },
  { path: 'numberrange-new/:code', component: NumberrangeNewComponent, data: { title: 'Setup', module: 'NumberRange - Add New' } },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class IdMastersRoutingModule { }
