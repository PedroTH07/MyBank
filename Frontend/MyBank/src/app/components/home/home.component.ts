import { Component, OnInit, signal, WritableSignal } from '@angular/core';
import { User, UserService } from '../../services/user.service';

@Component({
  selector: 'app-home',
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {
  usersList: WritableSignal<User[]> = signal([]);

  constructor(private service: UserService) {}

  ngOnInit(): void {
      this.service.getUsers().subscribe({
        next: (data) => {
          this.usersList.set(data);
        },
        error: (error) => {
          console.error(error.message);
        }
      })
  }
}
