/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ReportsTestModule } from '../../../test.module';
import { ReportTemplateUpdateComponent } from 'app/entities/report-template/report-template-update.component';
import { ReportTemplateService } from 'app/entities/report-template/report-template.service';
import { ReportTemplate } from 'app/shared/model/report-template.model';

describe('Component Tests', () => {
  describe('ReportTemplate Management Update Component', () => {
    let comp: ReportTemplateUpdateComponent;
    let fixture: ComponentFixture<ReportTemplateUpdateComponent>;
    let service: ReportTemplateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ReportsTestModule],
        declarations: [ReportTemplateUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ReportTemplateUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ReportTemplateUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ReportTemplateService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ReportTemplate('123');
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
        const entity = new ReportTemplate();
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
