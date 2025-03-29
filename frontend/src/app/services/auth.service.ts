import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

import { BehaviorSubject, Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly TOKEN_KEY = 'auth_token';

  constructor(private http: HttpClient, private router: Router) {}

  login(credentials: {username: string, password: string}) {
    return this.http.post('http://localhost:8080/api/auth/login', credentials).pipe(
      tap((response: any) => {
        localStorage.setItem(this.TOKEN_KEY, response.token);
      })
    );
  }

  logout() {
    localStorage.removeItem(this.TOKEN_KEY);
    this.router.navigate(['/login']);
  }

  isAuthenticated(): boolean {
    return !!localStorage.getItem(this.TOKEN_KEY);
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }
}
