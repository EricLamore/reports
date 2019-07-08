export interface IReportTemplate {
  id?: string;
  groupReportId?: string;
  reportId?: string;
}

export class ReportTemplate implements IReportTemplate {
  constructor(public id?: string, public groupReportId?: string, public reportId?: string) {}
}
