export class CheckListProperties {
  mapAttributes: Map<string, string>;

  constructor() {
    this.mapAttributes = new Map<string, string>();
    this.mapAttributes.set('isUserInOVA', 'User is registred in OVA')
      .set('hasGermanCourseAssigned', 'User assigned for first german course')
      .set('initialPassword', 'User has initial password')
      .set('hasBuddyAssigned', 'User has buddy assigned')
      .set('laptopOrder', 'Laptop order')
      .set('mailSent', 'Mail sent to user')
      .set('mailSentToBuddy', 'Mail sent to buddy')
      .set('isAddedToVerteiler', 'User is added to Vertailer');
  }

  public getValueMap(key: string): string {
    return this.mapAttributes.get(key);
  }

}

