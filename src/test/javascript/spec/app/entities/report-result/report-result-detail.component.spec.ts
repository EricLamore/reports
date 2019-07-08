/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ReportsTestModule } from '../../../test.module';
import { ReportResultDetailComponent } from 'app/entities/report-result/report-result-detail.component';
import { ReportResult } from 'app/shared/model/report-result.model';

describe('Component Tests', () => {
  describe('ReportResult Management Detail Component', () => {
    let comp: ReportResultDetailComponent;
    let fixture: ComponentFixture<ReportResultDetailComponent>;
    const route = ({ data: of({ reportResult: new ReportResult('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ReportsTestModule],
        declarations: [ReportResultDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ReportResultDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ReportResultDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.reportResult).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
