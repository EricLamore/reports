import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReportTemplate } from 'app/shared/model/report-template.model';

@Component({
  selector: 'jhi-report-template-detail',
  templateUrl: './report-template-detail.component.html'
})
export class ReportTemplateDetailComponent implements OnInit {
  reportTemplate: IReportTemplate;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ reportTemplate }) => {
      this.reportTemplate = reportTemplate;
    });
  }

  previousState() {
    window.history.back();
  }
}
