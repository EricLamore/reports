import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReportResult } from 'app/shared/model/report-result.model';
import { ReportResultService } from './report-result.service';

@Component({
  selector: 'jhi-report-result-delete-dialog',
  templateUrl: './report-result-delete-dialog.component.html'
})
export class ReportResultDeleteDialogComponent {
  reportResult: IReportResult;

  constructor(
    protected reportResultService: ReportResultService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.reportResultService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'reportResultListModification',
        content: 'Deleted an reportResult'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-report-result-delete-popup',
  template: ''
})
export class ReportResultDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ reportResult }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ReportResultDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.reportResult = reportResult;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/report-result', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/report-result', { outlets: { popup: null } }]);
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
