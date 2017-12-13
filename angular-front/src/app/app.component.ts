import { Component } from '@angular/core';
import {Location} from '@angular/common';
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'
  ]
})
export class AppComponent {
  title = 'app';

  constructor( private location: Location, private router: Router){
  }

  goBack(): void {
    this.location.back();
  }
}
