/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ReportsTestModule } from '../../../test.module';
import { GroupReportDeleteDialogComponent } from 'app/entities/group-report/group-report-delete-dialog.component';
import { GroupReportService } from 'app/entities/group-report/group-report.service';

describe('Component Tests', () => {
  describe('GroupReport Management Delete Component', () => {
    let comp: GroupReportDeleteDialogComponent;
    let fixture: ComponentFixture<GroupReportDeleteDialogComponent>;
    let service: GroupReportService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ReportsTestModule],
        declarations: [GroupReportDeleteDialogComponent]
      })
        .overrideTemplate(GroupReportDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GroupReportDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GroupReportService);
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
