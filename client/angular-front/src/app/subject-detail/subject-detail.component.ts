import {Component, OnInit} from '@angular/core';
import {MaterialService} from '../service/material.service';
import {ActivatedRoute} from '@angular/router';
import {CourseService} from '../service/course.service';
import {Subject} from '../domain/subject';
import {SubjectService} from '../service/subject.service';
import {UserDTO} from '../domain/user';

@Component({
  selector: 'app-subject-detail',
  templateUrl: './subject-detail.component.html',
  styleUrls: ['./subject-detail.component.css']
})
export class SubjectDetailComponent implements OnInit {

  public subject: Subject;
  private user: UserDTO;


  constructor(private materialService: MaterialService, private route: ActivatedRoute, private courseService: CourseService, private materialSevice: MaterialService, private subjectService: SubjectService) {
  }

  ngOnInit() {
    this.getSubject();
    this.user = new UserDTO();
    this.user.username = localStorage.getItem('userLogged');
  }

  getSubject() {

    const id = +this.route.snapshot.paramMap.get('id');
    const idSubject = +this.route.snapshot.paramMap.get('idSubject');
    this.courseService.getSubject(id, idSubject).subscribe(subject => {
      this.subject = subject;
    });
  }

  markAsFinish(subject: Subject) {


    this.subjectService.markAsFinish(subject, this.user).subscribe();
  }


  downloadFile(idMaterial: number, titleMaterial: string): void {
    var file: Blob;
    this.materialSevice.getFileWithId(idMaterial).subscribe(url => window.open(url));
  }
}
