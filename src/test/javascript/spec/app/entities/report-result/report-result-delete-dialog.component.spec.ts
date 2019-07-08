/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ReportsTestModule } from '../../../test.module';
import { ReportResultDeleteDialogComponent } from 'app/entities/report-result/report-result-delete-dialog.component';
import { ReportResultService } from 'app/entities/report-result/report-result.service';

describe('Component Tests', () => {
  describe('ReportResult Management Delete Component', () => {
    let comp: ReportResultDeleteDialogComponent;
    let fixture: ComponentFixture<ReportResultDeleteDialogComponent>;
    let service: ReportResultService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ReportsTestModule],
        declarations: [ReportResultDeleteDialogComponent]
      })
        .overrideTemplate(ReportResultDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReportResultDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReportResultService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete('123');
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith('123');
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
