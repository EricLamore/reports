import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'group-report',
        loadChildren: './group-report/group-report.module#ReportsGroupReportModule'
      },
      {
        path: 'report-result',
        loadChildren: './report-result/report-result.module#ReportsReportResultModule'
      },
      {
        path: 'report-template',
        loadChildren: './report-template/report-template.module#ReportsReportTemplateModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ReportsEntityModule {}
