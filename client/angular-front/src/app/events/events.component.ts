import {Component, OnInit, ViewChild} from '@angular/core';
import {MatSort, MatTableDataSource} from '@angular/material';

@Component({
  selector: 'app-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.css']
})
export class EventsComponent implements OnInit {
  panelOpenState: boolean =false;
  @ViewChild(MatSort) sort: MatSort;



  constructor() {
  }

  ngOnInit() {

  }

}
