import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Comment} from "../model/comment";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: HttpClient) {
  }

  getComments() {

    return this.http.get<Comment[]>(environment.apiURL + "/api/comments/getComments");
  }
}
