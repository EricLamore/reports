import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGroupReport } from 'app/shared/model/group-report.model';

type EntityResponseType = HttpResponse<IGroupReport>;
type EntityArrayResponseType = HttpResponse<IGroupReport[]>;

@Injectable({ providedIn: 'root' })
export class GroupReportService {
  public resourceUrl = SERVER_API_URL + 'api/group-reports';

  constructor(protected http: HttpClient) {}

  create(groupReport: IGroupReport): Observable<EntityResponseType> {
    return this.http.post<IGroupReport>(this.resourceUrl, groupReport, { observe: 'response' });
  }

  update(groupReport: IGroupReport): Observable<EntityResponseType> {
    return this.http.put<IGroupReport>(this.resourceUrl, groupReport, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IGroupReport>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGroupReport[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
