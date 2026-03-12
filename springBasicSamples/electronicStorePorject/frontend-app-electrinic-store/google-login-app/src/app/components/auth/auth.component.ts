import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { GoogleSigninButtonModule, SocialAuthService } from '@abacritt/angularx-social-login';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [CommonModule, FormsModule, GoogleSigninButtonModule],
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.css'
})
export class AuthComponent {
  isLogin = true;
  
  loginData = { email: '', password: '' };
  signupData = { name: '', email: '', password: '', about: '' };
  
  errorMessage = '';
  successMessage = '';

  constructor(
    private authService: AuthService,
    private socialAuthService: SocialAuthService
  ) {
    this.socialAuthService.authState.subscribe((authData) => {
      if (authData && authData.idToken) {
        this.authService.loginWithGoogle(authData.idToken).subscribe({
          next: (res) => this.handleAuthSuccess(res),
          error: (err) => this.errorMessage = err.error?.message || 'Google login failed'
        });
      }
    });
  }

  toggleMode() {
    this.isLogin = !this.isLogin;
    this.errorMessage = '';
    this.successMessage = '';
  }

  onLogin() {
    this.errorMessage = '';
    console.log('Login attempt:', this.loginData.email);
    this.authService.login(this.loginData.email, this.loginData.password).subscribe({
      next: (res) => {
        console.log('Login success:', res);
        this.handleAuthSuccess(res);
      },
      error: (err) => {
        console.error('Login error:', err);
        this.errorMessage = err.error?.message || err.message || 'Login failed. Please check your credentials.';
      }
    });
  }

  onSignup() {
    this.errorMessage = '';
    this.successMessage = '';
    this.authService.signup(this.signupData).subscribe({
      next: () => {
        this.successMessage = 'Account created! Please login.';
        setTimeout(() => {
          this.isLogin = true;
          this.successMessage = '';
        }, 2000);
      },
      error: (err) => this.errorMessage = err.error?.message || 'Signup failed'
    });
  }

  private handleAuthSuccess(res: any) {
    localStorage.setItem('token', res.token);
    localStorage.setItem('user', JSON.stringify(res.user));
    window.location.reload();
  }
}
