import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IReportTemplate, ReportTemplate } from 'app/shared/model/report-template.model';
import { ReportTemplateService } from './report-template.service';

@Component({
  selector: 'jhi-report-template-update',
  templateUrl: './report-template-update.component.html'
})
export class ReportTemplateUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    groupReportId: [],
    reportId: []
  });

  constructor(protected reportTemplateService: ReportTemplateService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ reportTemplate }) => {
      this.updateForm(reportTemplate);
    });
  }

  updateForm(reportTemplate: IReportTemplate) {
    this.editForm.patchValue({
      id: reportTemplate.id,
      groupReportId: reportTemplate.groupReportId,
      reportId: reportTemplate.reportId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const reportTemplate = this.createFromForm();
    if (reportTemplate.id !== undefined) {
      this.subscribeToSaveResponse(this.reportTemplateService.update(reportTemplate));
    } else {
      this.subscribeToSaveResponse(this.reportTemplateService.create(reportTemplate));
    }
  }

  private createFromForm(): IReportTemplate {
    return {
      ...new ReportTemplate(),
      id: this.editForm.get(['id']).value,
      groupReportId: this.editForm.get(['groupReportId']).value,
      reportId: this.editForm.get(['reportId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReportTemplate>>) {
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
