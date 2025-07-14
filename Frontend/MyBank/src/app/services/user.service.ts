import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UUID } from 'crypto';
import { Observable } from 'rxjs';

export interface User {
  userId: UUID,
  name: string,
  email: string,
  password: string,
  money: number
}

@Injectable({
  providedIn: 'root'
})
export class UserService {
  // para que o express consiga interceptar a chamada e leva-la para o backend corretamente
  private readonly backendUrl: string = "/api";

  constructor(private http: HttpClient) { }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.backendUrl + "/users");
  }
}
