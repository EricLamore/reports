import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IReportResult, ReportResult } from 'app/shared/model/report-result.model';
import { ReportResultService } from './report-result.service';

@Component({
  selector: 'jhi-report-result-update',
  templateUrl: './report-result-update.component.html'
})
export class ReportResultUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    groupReportId: [],
    reportId: []
  });

  constructor(protected reportResultService: ReportResultService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ reportResult }) => {
      this.updateForm(reportResult);
    });
  }

  updateForm(reportResult: IReportResult) {
    this.editForm.patchValue({
      id: reportResult.id,
      groupReportId: reportResult.groupReportId,
      reportId: reportResult.reportId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const reportResult = this.createFromForm();
    if (reportResult.id !== undefined) {
      this.subscribeToSaveResponse(this.reportResultService.update(reportResult));
    } else {
      this.subscribeToSaveResponse(this.reportResultService.create(reportResult));
    }
  }

  private createFromForm(): IReportResult {
    return {
      ...new ReportResult(),
      id: this.editForm.get(['id']).value,
      groupReportId: this.editForm.get(['groupReportId']).value,
      reportId: this.editForm.get(['reportId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReportResult>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
