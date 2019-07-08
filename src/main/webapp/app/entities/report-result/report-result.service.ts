import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IReportResult } from 'app/shared/model/report-result.model';

type EntityResponseType = HttpResponse<IReportResult>;
type EntityArrayResponseType = HttpResponse<IReportResult[]>;

@Injectable({ providedIn: 'root' })
export class ReportResultService {
  public resourceUrl = SERVER_API_URL + 'api/report-results';

  constructor(protected http: HttpClient) {}

  create(reportResult: IReportResult): Observable<EntityResponseType> {
    return this.http.post<IReportResult>(this.resourceUrl, reportResult, { observe: 'response' });
  }

  update(reportResult: IReportResult): Observable<EntityResponseType> {
    return this.http.put<IReportResult>(this.resourceUrl, reportResult, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IReportResult>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IReportResult[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
