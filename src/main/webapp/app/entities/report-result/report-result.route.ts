import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ReportResult } from 'app/shared/model/report-result.model';
import { ReportResultService } from './report-result.service';
import { ReportResultComponent } from './report-result.component';
import { ReportResultDetailComponent } from './report-result-detail.component';
import { ReportResultUpdateComponent } from './report-result-update.component';
import { ReportResultDeletePopupComponent } from './report-result-delete-dialog.component';
import { IReportResult } from 'app/shared/model/report-result.model';

@Injectable({ providedIn: 'root' })
export class ReportResultResolve implements Resolve<IReportResult> {
  constructor(private service: ReportResultService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IReportResult> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ReportResult>) => response.ok),
        map((reportResult: HttpResponse<ReportResult>) => reportResult.body)
      );
    }
    return of(new ReportResult());
  }
}

export const reportResultRoute: Routes = [
  {
    path: '',
    component: ReportResultComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'reportsApp.reportResult.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ReportResultDetailComponent,
    resolve: {
      reportResult: ReportResultResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'reportsApp.reportResult.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ReportResultUpdateComponent,
    resolve: {
      reportResult: ReportResultResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'reportsApp.reportResult.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ReportResultUpdateComponent,
    resolve: {
      reportResult: ReportResultResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'reportsApp.reportResult.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const reportResultPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ReportResultDeletePopupComponent,
    resolve: {
      reportResult: ReportResultResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'reportsApp.reportResult.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
