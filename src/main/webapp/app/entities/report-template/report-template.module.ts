import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ReportsSharedModule } from 'app/shared';
import {
  ReportTemplateComponent,
  ReportTemplateDetailComponent,
  ReportTemplateUpdateComponent,
  ReportTemplateDeletePopupComponent,
  ReportTemplateDeleteDialogComponent,
  reportTemplateRoute,
  reportTemplatePopupRoute
} from './';

const ENTITY_STATES = [...reportTemplateRoute, ...reportTemplatePopupRoute];

@NgModule({
  imports: [ReportsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ReportTemplateComponent,
    ReportTemplateDetailComponent,
    ReportTemplateUpdateComponent,
    ReportTemplateDeleteDialogComponent,
    ReportTemplateDeletePopupComponent
  ],
  entryComponents: [
    ReportTemplateComponent,
    ReportTemplateUpdateComponent,
    ReportTemplateDeleteDialogComponent,
    ReportTemplateDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ReportsReportTemplateModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
