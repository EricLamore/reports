import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReportTemplate } from 'app/shared/model/report-template.model';
import { ReportTemplateService } from './report-template.service';

@Component({
  selector: 'jhi-report-template-delete-dialog',
  templateUrl: './report-template-delete-dialog.component.html'
})
export class ReportTemplateDeleteDialogComponent {
  reportTemplate: IReportTemplate;

  constructor(
    protected reportTemplateService: ReportTemplateService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.reportTemplateService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'reportTemplateListModification',
        content: 'Deleted an reportTemplate'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-report-template-delete-popup',
  template: ''
})
export class ReportTemplateDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ reportTemplate }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ReportTemplateDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.reportTemplate = reportTemplate;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/report-template', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/report-template', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
