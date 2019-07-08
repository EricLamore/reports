import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IReportResult } from 'app/shared/model/report-result.model';

@Component({
  selector: 'jhi-report-result-detail',
  templateUrl: './report-result-detail.component.html'
})
export class ReportResultDetailComponent implements OnInit {
  reportResult: IReportResult;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ reportResult }) => {
      this.reportResult = reportResult;
    });
  }

  previousState() {
    window.history.back();
  }
}
