import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGroupReport } from 'app/shared/model/group-report.model';

@Component({
  selector: 'jhi-group-report-detail',
  templateUrl: './group-report-detail.component.html'
})
export class GroupReportDetailComponent implements OnInit {
  groupReport: IGroupReport;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ groupReport }) => {
      this.groupReport = groupReport;
    });
  }

  previousState() {
    window.history.back();
  }
}
