import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ReportTemplate } from 'app/shared/model/report-template.model';
import { ReportTemplateService } from './report-template.service';
import { ReportTemplateComponent } from './report-template.component';
import { ReportTemplateDetailComponent } from './report-template-detail.component';
import { ReportTemplateUpdateComponent } from './report-template-update.component';
import { ReportTemplateDeletePopupComponent } from './report-template-delete-dialog.component';
import { IReportTemplate } from 'app/shared/model/report-template.model';

@Injectable({ providedIn: 'root' })
export class ReportTemplateResolve implements Resolve<IReportTemplate> {
  constructor(private service: ReportTemplateService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IReportTemplate> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ReportTemplate>) => response.ok),
        map((reportTemplate: HttpResponse<ReportTemplate>) => reportTemplate.body)
      );
    }
    return of(new ReportTemplate());
  }
}

export const reportTemplateRoute: Routes = [
  {
    path: '',
    component: ReportTemplateComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'reportsApp.reportTemplate.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ReportTemplateDetailComponent,
    resolve: {
      reportTemplate: ReportTemplateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'reportsApp.reportTemplate.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ReportTemplateUpdateComponent,
    resolve: {
      reportTemplate: ReportTemplateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'reportsApp.reportTemplate.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ReportTemplateUpdateComponent,
    resolve: {
      reportTemplate: ReportTemplateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'reportsApp.reportTemplate.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const reportTemplatePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ReportTemplateDeletePopupComponent,
    resolve: {
      reportTemplate: ReportTemplateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'reportsApp.reportTemplate.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
