import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-general-infos',
  templateUrl: './general-infos.component.html',
  styleUrls: ['./general-infos.component.css']
})
export class GeneralInfosComponent implements OnInit {

  public info: Map<string, string> = new Map<string, string>();

  constructor() { }

  ngOnInit() {
    this.createInfosMap();
  }


  private createInfosMap() {
    this.info.set('https://team.msg.de/site/msg%20systems%20RO/SitePages/Homepage.aspx', 'Team site .msg Systems Romania');
    this.info.set('https://guru.msg.de/', '.msg Systems Global');
    this.info.set('https://team.msg.de/site/msg%20systems%20RO/Docs/Administrativ/Organigrama/Orga%2026.02.2018.png', '.msg Systems Romania department structure');
    // this.inf.set('', '.msg Gillardon Global department structure(de luat de la Miruna)');
    this.info.set('https://team.msg.de/site/msg%20systems%20RO/SitePages/Persoane%20de%20contact.aspx', 'Contact persons (Administrativ department && HR)');
    this.info.set('https://team.msg.de/site/msg%20systems%20RO/SitePages/Beneficiile%20tale.aspx', 'Your benefits in .msg');
    this.info.set('https://team.msg.de/site/msg%20systems%20RO/SitePages/Zile%20libere%202018.aspx', 'Holidays');
    this.info.set('https://team.msg.de/site/msg%20systems%20RO/Docs/Marketing/Event%20Calendar.png', 'Event calendar');
    this.info.set('https://team.msg.de/site/msg%20systems%20RO/Docs/Administrativ/Handout_SelfServicePortal_CIT_v1.0_engl.pdf', 'Service desk & how to create a ticket');
    this.info.set('https://team.msg.de/site/msg%20systems%20RO/SitePages/Aplicatii%20Interne.aspx', 'Intern applications (Birthday app, Reise Portal, Timesheet etc. )');
    this.info.set('https://team.msg.de/site/msg%20systems%20RO/SitePages/Start-Tag.aspx', 'Start-tag');
    this.info.set('https://team.msg.de/site/msg%20systems%20RO/SitePages/Dezvoltare%20profesionala.aspx', 'Professional development in .msg Romania');
    this.info.set('https://team.msg.de/site/msg%20systems%20RO/SitePages/Delegatii.aspx', 'Delegations');

  }

   get infosKeys(){
    return  Array.from(this.info.keys());
  }
}
