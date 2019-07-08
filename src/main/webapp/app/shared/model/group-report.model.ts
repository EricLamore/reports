export interface IGroupReport {
  id?: string;
  name?: string;
  description?: string;
  universignOrganizationId?: string;
}

export class GroupReport implements IGroupReport {
  constructor(public id?: string, public name?: string, public description?: string, public universignOrganizationId?: string) {}
}
