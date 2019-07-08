import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ReportsSharedModule } from 'app/shared';
import {
  ReportResultComponent,
  ReportResultDetailComponent,
  ReportResultUpdateComponent,
  ReportResultDeletePopupComponent,
  ReportResultDeleteDialogComponent,
  reportResultRoute,
  reportResultPopupRoute
} from './';

const ENTITY_STATES = [...reportResultRoute, ...reportResultPopupRoute];

@NgModule({
  imports: [ReportsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ReportResultComponent,
    ReportResultDetailComponent,
    ReportResultUpdateComponent,
    ReportResultDeleteDialogComponent,
    ReportResultDeletePopupComponent
  ],
  entryComponents: [
    ReportResultComponent,
    ReportResultUpdateComponent,
    ReportResultDeleteDialogComponent,
    ReportResultDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ReportsReportResultModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
