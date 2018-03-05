import {Component, OnInit} from '@angular/core';
import {MaterialService} from '../service/material.service';
import {ActivatedRoute} from '@angular/router';
import {CourseService} from '../service/course.service';
import {Subject} from '../domain/subject';
import {SubjectService} from '../service/subject.service';
import {UserDTO} from '../domain/user';
import {UtilityService} from '../service/utility.service';
import {Observable} from 'rxjs/Observable';
import {of} from 'rxjs/observable/of';
import {map} from 'rxjs/operator/map';

@Component({
  selector: 'app-subject-detail',
  templateUrl: './subject-detail.component.html',
  styleUrls: ['./subject-detail.component.css']
})
export class SubjectDetailComponent implements OnInit {

  public subject: Subject;
  private user: UserDTO;
  private hasAnotherSubject: Boolean;
  private modalMessage: string;
  public isSubjectFinished: Boolean;

  constructor(private materialService: MaterialService, public utilityService:UtilityService, private route: ActivatedRoute, private courseService: CourseService, private materialSevice: MaterialService, private subjectService: SubjectService) {
  }

  ngOnInit() {
    this.subject=new Subject();
    this.subject.idSubject=+this.route.snapshot.paramMap.get('idSubject');
    this.getSubject();
    this.user = new UserDTO();
    this.user.username = localStorage.getItem('userLogged');
    this.subjectService.isSubjectFinished(this.subject, this.user).subscribe(res=> this.isSubjectFinished=res);
  }

  getSubject() {

    const id = +this.route.snapshot.paramMap.get('id');
    const idSubject = +this.route.snapshot.paramMap.get('idSubject');
    this.courseService.getSubject(id, idSubject).subscribe(subject => {
      this.subject = subject;
    });
  }


  markAsFinish(subject: Subject) {
    this.subjectService.markAsFinish(subject, this.user).subscribe(resp=>{this.hasAnotherSubject=resp;
    this.isSubjectFinished=true;
    console.log(this.hasAnotherSubject);
    if(this.hasAnotherSubject==true)
      this.modalMessage="Subject finished! You can now see next subject!";
    else{
      this.modalMessage="Course finished!";
    }

    this.utilityService.openModal("subjectFinished")});
}


  downloadFile(idMaterial: number, titleMaterial: string): void {
    var file: Blob;
    this.materialSevice.getFileWithId(idMaterial).subscribe(url => window.open(url));
  }
}
