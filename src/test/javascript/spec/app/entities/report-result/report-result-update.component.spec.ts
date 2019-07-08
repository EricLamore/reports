/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ReportsTestModule } from '../../../test.module';
import { ReportResultUpdateComponent } from 'app/entities/report-result/report-result-update.component';
import { ReportResultService } from 'app/entities/report-result/report-result.service';
import { ReportResult } from 'app/shared/model/report-result.model';

describe('Component Tests', () => {
  describe('ReportResult Management Update Component', () => {
    let comp: ReportResultUpdateComponent;
    let fixture: ComponentFixture<ReportResultUpdateComponent>;
    let service: ReportResultService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ReportsTestModule],
        declarations: [ReportResultUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ReportResultUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReportResultUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReportResultService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ReportResult('123');
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ReportResult();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
