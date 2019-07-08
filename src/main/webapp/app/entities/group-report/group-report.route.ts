import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { GroupReport } from 'app/shared/model/group-report.model';
import { GroupReportService } from './group-report.service';
import { GroupReportComponent } from './group-report.component';
import { GroupReportDetailComponent } from './group-report-detail.component';
import { GroupReportUpdateComponent } from './group-report-update.component';
import { GroupReportDeletePopupComponent } from './group-report-delete-dialog.component';
import { IGroupReport } from 'app/shared/model/group-report.model';

@Injectable({ providedIn: 'root' })
export class GroupReportResolve implements Resolve<IGroupReport> {
  constructor(private service: GroupReportService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IGroupReport> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<GroupReport>) => response.ok),
        map((groupReport: HttpResponse<GroupReport>) => groupReport.body)
      );
    }
    return of(new GroupReport());
  }
}

export const groupReportRoute: Routes = [
  {
    path: '',
    component: GroupReportComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'reportsApp.groupReport.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: GroupReportDetailComponent,
    resolve: {
      groupReport: GroupReportResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'reportsApp.groupReport.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: GroupReportUpdateComponent,
    resolve: {
      groupReport: GroupReportResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'reportsApp.groupReport.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: GroupReportUpdateComponent,
    resolve: {
      groupReport: GroupReportResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'reportsApp.groupReport.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const groupReportPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: GroupReportDeletePopupComponent,
    resolve: {
      groupReport: GroupReportResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'reportsApp.groupReport.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
