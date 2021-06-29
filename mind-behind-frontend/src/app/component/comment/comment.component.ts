import {Component, OnInit} from '@angular/core';
import {CommentService} from "../../services/comment.service";
import {Comment} from "../../model/comment";

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css']
})
export class CommentComponent implements OnInit {

  constructor(private commentService: CommentService) {
  }

  comments: Comment[] = [];

  ngOnInit(): void {
    this.commentService.getComments().subscribe((data: Comment[]) => this.comments = data);
  }

}
