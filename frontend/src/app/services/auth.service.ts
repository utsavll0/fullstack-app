import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';
import { LoginRequest, LoginResponse } from '../models/auth.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';
  private currentUserSubject = new BehaviorSubject<LoginResponse | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient) {
    this.checkAuthStatus();
  }

  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, credentials, {
      withCredentials: true
    }).pipe(
      tap(response => {
        if (response.success) {
          this.currentUserSubject.next(response);
        }
      })
    );
  }

  logout(): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/logout`, {}, {
      withCredentials: true
    }).pipe(
      tap(() => {
        this.currentUserSubject.next(null);
      })
    );
  }

  checkAuthStatus(): void {
    this.http.get<LoginResponse>(`${this.apiUrl}/status`, {
      withCredentials: true
    }).subscribe({
      next: (response) => {
        if (response.success) {
          this.currentUserSubject.next(response);
        }
      },
      error: () => {
        this.currentUserSubject.next(null);
      }
    });
  }

  isAuthenticated(): boolean {
    return this.currentUserSubject.value?.success ?? false;
  }

  getCurrentUser(): LoginResponse | null {
    return this.currentUserSubject.value;
  }
}
