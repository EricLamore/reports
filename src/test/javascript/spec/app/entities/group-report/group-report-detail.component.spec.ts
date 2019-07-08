/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ReportsTestModule } from '../../../test.module';
import { GroupReportDetailComponent } from 'app/entities/group-report/group-report-detail.component';
import { GroupReport } from 'app/shared/model/group-report.model';

describe('Component Tests', () => {
  describe('GroupReport Management Detail Component', () => {
    let comp: GroupReportDetailComponent;
    let fixture: ComponentFixture<GroupReportDetailComponent>;
    const route = ({ data: of({ groupReport: new GroupReport('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ReportsTestModule],
        declarations: [GroupReportDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(GroupReportDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GroupReportDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.groupReport).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
