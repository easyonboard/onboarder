import {Injectable} from '@angular/core';
import {Review} from "../domain/review";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {RootConst} from "../util/RootConst";

@Injectable()
export class ReviewService {
  private rootConst: RootConst = new RootConst();
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) {
  }

  addReview(review: Review): any{
    const body = JSON.stringify(review);
    return this.http.post(this.rootConst.WEB_SERVICE_ADD_REVIEW, body, this.httpOptions);

  }
}
