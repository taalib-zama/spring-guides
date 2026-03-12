import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:9090';

  constructor(private http: HttpClient) {}

  login(email: string, password: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/auth/generate-token`, { email, password });
  }

  signup(userData: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/api/v1/users`, userData);
  }

  loginWithGoogle(idToken: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/auth/login-with-google`, { idToken });
  }
}
