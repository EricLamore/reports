/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ReportsTestModule } from '../../../test.module';
import { GroupReportUpdateComponent } from 'app/entities/group-report/group-report-update.component';
import { GroupReportService } from 'app/entities/group-report/group-report.service';
import { GroupReport } from 'app/shared/model/group-report.model';

describe('Component Tests', () => {
  describe('GroupReport Management Update Component', () => {
    let comp: GroupReportUpdateComponent;
    let fixture: ComponentFixture<GroupReportUpdateComponent>;
    let service: GroupReportService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ReportsTestModule],
        declarations: [GroupReportUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(GroupReportUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GroupReportUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GroupReportService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GroupReport('123');
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
        const entity = new GroupReport();
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
