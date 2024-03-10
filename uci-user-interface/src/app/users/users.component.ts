import { Component } from '@angular/core';
import { UserInfo } from '../interfaces/user-info';
import { UserService } from '../services/user.service';
import { PageEvent } from '@angular/material/paginator';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent {
  viewAllUsersApi: Subscription  = new Subscription();
  users: UserInfo[] = [];
  totalElements: number = 0;
  pageIndex: number = 0;
  pageSize: number = 20;

  constructor(private userService: UserService) {}

  ngOnInit() {
    this.viewUsers(this.pageIndex);
  }

  viewUsers(page: number) {
    this.viewAllUsersApi = this.userService.viewAllUsers(page)
    .subscribe({
      next: (response) => {       
        this.users  = response.content;
        this.pageSize = response.size;
        this.totalElements = response.totalElements;
      },
      error: (err) => {
        alert(err.error);
      }
    })
  }

  handlePageEvent(e: PageEvent) {
    this.pageIndex = e.pageIndex;
    this.viewUsers(this.pageIndex);
  }

  ngOnDestroy() {
    this.viewAllUsersApi.unsubscribe();
  }
}
