import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IGroupReport, GroupReport } from 'app/shared/model/group-report.model';
import { GroupReportService } from './group-report.service';

@Component({
  selector: 'jhi-group-report-update',
  templateUrl: './group-report-update.component.html'
})
export class GroupReportUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [],
    description: [],
    universignOrganizationId: []
  });

  constructor(protected groupReportService: GroupReportService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ groupReport }) => {
      this.updateForm(groupReport);
    });
  }

  updateForm(groupReport: IGroupReport) {
    this.editForm.patchValue({
      id: groupReport.id,
      name: groupReport.name,
      description: groupReport.description,
      universignOrganizationId: groupReport.universignOrganizationId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const groupReport = this.createFromForm();
    if (groupReport.id !== undefined) {
      this.subscribeToSaveResponse(this.groupReportService.update(groupReport));
    } else {
      this.subscribeToSaveResponse(this.groupReportService.create(groupReport));
    }
  }

  private createFromForm(): IGroupReport {
    return {
      ...new GroupReport(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      description: this.editForm.get(['description']).value,
      universignOrganizationId: this.editForm.get(['universignOrganizationId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGroupReport>>) {
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
