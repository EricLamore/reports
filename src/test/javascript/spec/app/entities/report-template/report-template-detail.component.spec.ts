/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ReportsTestModule } from '../../../test.module';
import { ReportTemplateDetailComponent } from 'app/entities/report-template/report-template-detail.component';
import { ReportTemplate } from 'app/shared/model/report-template.model';

describe('Component Tests', () => {
  describe('ReportTemplate Management Detail Component', () => {
    let comp: ReportTemplateDetailComponent;
    let fixture: ComponentFixture<ReportTemplateDetailComponent>;
    const route = ({ data: of({ reportTemplate: new ReportTemplate('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ReportsTestModule],
        declarations: [ReportTemplateDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ReportTemplateDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReportTemplateDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.reportTemplate).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
