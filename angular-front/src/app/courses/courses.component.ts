import { Component, OnInit } from '@angular/core';
import {COURSES} from '../coursesListDemo';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.css']
})
export class CoursesComponent implements OnInit {

  courses=COURSES;
  constructor() { }

  ngOnInit() {
  }

}
