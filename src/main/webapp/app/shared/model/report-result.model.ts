export interface IReportResult {
  id?: string;
  groupReportId?: string;
  reportId?: string;
}

export class ReportResult implements IReportResult {
  constructor(public id?: string, public groupReportId?: string, public reportId?: string) {}
}
