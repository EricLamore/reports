import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ReportsSharedModule } from 'app/shared';
import {
  GroupReportComponent,
  GroupReportDetailComponent,
  GroupReportUpdateComponent,
  GroupReportDeletePopupComponent,
  GroupReportDeleteDialogComponent,
  groupReportRoute,
  groupReportPopupRoute
} from './';

const ENTITY_STATES = [...groupReportRoute, ...groupReportPopupRoute];

@NgModule({
  imports: [ReportsSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    GroupReportComponent,
    GroupReportDetailComponent,
    GroupReportUpdateComponent,
    GroupReportDeleteDialogComponent,
    GroupReportDeletePopupComponent
  ],
  entryComponents: [GroupReportComponent, GroupReportUpdateComponent, GroupReportDeleteDialogComponent, GroupReportDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ReportsGroupReportModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
