import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IReportTemplate } from 'app/shared/model/report-template.model';

type EntityResponseType = HttpResponse<IReportTemplate>;
type EntityArrayResponseType = HttpResponse<IReportTemplate[]>;

@Injectable({ providedIn: 'root' })
export class ReportTemplateService {
  public resourceUrl = SERVER_API_URL + 'api/report-templates';

  constructor(protected http: HttpClient) {}

  create(reportTemplate: IReportTemplate): Observable<EntityResponseType> {
    return this.http.post<IReportTemplate>(this.resourceUrl, reportTemplate, { observe: 'response' });
  }

  update(reportTemplate: IReportTemplate): Observable<EntityResponseType> {
    return this.http.put<IReportTemplate>(this.resourceUrl, reportTemplate, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IReportTemplate>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IReportTemplate[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
