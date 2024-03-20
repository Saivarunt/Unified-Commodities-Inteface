import { Component } from '@angular/core';
import { UserInfo } from '../interfaces/user-info';
import { UserService } from '../services/user.service';
import { PageEvent } from '@angular/material/paginator';
import { Subscription } from 'rxjs';
import { AlertService } from '../services/alert.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent {
  viewAllUsersApi: Subscription = new Subscription();
  searchApi: Subscription = new Subscription();
  users: UserInfo[] = [];
  totalElements: number = 0;
  pageIndex: number = 0;
  pageSize: number = 20;
  search: string = "";

  constructor(private userService: UserService, private al: AlertService) { }

  ngOnInit() {
    this.viewUsers(this.pageIndex);
  }

  viewUsers(page: number) {
    this.viewAllUsersApi = this.userService.viewAllUsers(page)
      .subscribe({
        next: (response) => {
          this.users = response.content;
          this.pageSize = response.size;
          this.totalElements = response.totalElements;
        },
        error: (err) => {
          this.al.alertPrompt("Error", err.error, "error");;
        }
      })
  }

  handlePageEvent(e: PageEvent) {
    this.pageIndex = e.pageIndex;
    this.viewUsers(this.pageIndex);
  }

  trigger = this.debounce(() => {
    this.searchApi = this.userService.searchByUsername(this.search, this.pageIndex > 0 ? 0 : this.pageIndex)
      .subscribe({
        next: (response) => {
          this.users = response.content;
          this.pageSize = response.size;
          this.totalElements = response.totalElements;
        },
        error: (err) => {
          this.al.alertPrompt("Error", err.error, "error");;
        }
      });
  }, 1000)


  debounce(cb: Function, delay: number) {
    let interval = setTimeout(() => { }, 0);
    return (...args: any) => {
      clearTimeout(interval);
      interval = setTimeout(() => {
        cb(...args)
      }, delay)
    }
  }

  searchProduct() {
    if (this.search[this.search.length - 1] !== " ") {
      this.trigger();
    }
  }

  ngOnDestroy() {
    this.viewAllUsersApi.unsubscribe();
    this.searchApi.unsubscribe();
  }
}
