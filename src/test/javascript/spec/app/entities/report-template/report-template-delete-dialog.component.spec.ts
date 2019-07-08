/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ReportsTestModule } from '../../../test.module';
import { ReportTemplateDeleteDialogComponent } from 'app/entities/report-template/report-template-delete-dialog.component';
import { ReportTemplateService } from 'app/entities/report-template/report-template.service';

describe('Component Tests', () => {
  describe('ReportTemplate Management Delete Component', () => {
    let comp: ReportTemplateDeleteDialogComponent;
    let fixture: ComponentFixture<ReportTemplateDeleteDialogComponent>;
    let service: ReportTemplateService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ReportsTestModule],
        declarations: [ReportTemplateDeleteDialogComponent]
      })
        .overrideTemplate(ReportTemplateDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReportTemplateDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReportTemplateService);
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
