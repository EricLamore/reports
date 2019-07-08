import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ReportsSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [ReportsSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [ReportsSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ReportsSharedModule {
  static forRoot() {
    return {
      ngModule: ReportsSharedModule
    };
  }
}
