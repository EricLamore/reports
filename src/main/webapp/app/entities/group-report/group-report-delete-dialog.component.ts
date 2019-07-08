import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGroupReport } from 'app/shared/model/group-report.model';
import { GroupReportService } from './group-report.service';

@Component({
  selector: 'jhi-group-report-delete-dialog',
  templateUrl: './group-report-delete-dialog.component.html'
})
export class GroupReportDeleteDialogComponent {
  groupReport: IGroupReport;

  constructor(
    protected groupReportService: GroupReportService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: string) {
    this.groupReportService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'groupReportListModification',
        content: 'Deleted an groupReport'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-group-report-delete-popup',
  template: ''
})
export class GroupReportDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ groupReport }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(GroupReportDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.groupReport = groupReport;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/group-report', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/group-report', { outlets: { popup: null } }]);
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
