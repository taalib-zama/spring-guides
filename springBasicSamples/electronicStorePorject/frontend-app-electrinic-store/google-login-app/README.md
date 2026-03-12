# 🛒 Electronic Store - Frontend Application

> A modern Angular-based e-commerce frontend with multiple authentication strategies including email/password and Google OAuth.

[![Angular](https://img.shields.io/badge/Angular-18.0-red)](https://angular.io/)
[![TypeScript](https://img.shields.io/badge/TypeScript-5.4-blue)](https://www.typescriptlang.org/)
[![Tailwind CSS](https://img.shields.io/badge/Tailwind-3.4-38bdf8)](https://tailwindcss.com/)

---

## 📋 Table of Contents

- [Overview](#overview)
- [System Architecture](#system-architecture)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Setup & Installation](#setup--installation)
- [Authentication Flow](#authentication-flow)
- [API Integration](#api-integration)
- [Component Documentation](#component-documentation)
- [Development Guide](#development-guide)

---

## 🎯 Overview

The Electronic Store Frontend is a single-page application (SPA) built with Angular 18, providing a seamless shopping experience with robust authentication mechanisms. The application supports traditional email/password authentication as well as modern OAuth 2.0 integration with Google.

### Key Highlights
- **Standalone Components** - Modern Angular architecture
- **Multiple Auth Strategies** - Email/Password + Google OAuth
- **JWT Token Management** - Secure session handling
- **Responsive Design** - Mobile-first approach with Tailwind CSS
- **Type-Safe** - Full TypeScript implementation

---

## 🏗️ System Architecture

### High-Level Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                     USER INTERFACE                          │
│                    (Angular Frontend)                       │
└─────────────────────────────────────────────────────────────┘
                            │
                            │ HTTP/HTTPS
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                   API GATEWAY LAYER                         │
│              (Spring Boot Backend - Port 9090)              │
└─────────────────────────────────────────────────────────────┘
                            │
                ┌───────────┴───────────┐
                │                       │
                ▼                       ▼
    ┌──────────────────┐    ┌──────────────────┐
    │  Authentication  │    │  Google OAuth    │
    │     Service      │    │   Verification   │
    └──────────────────┘    └──────────────────┘
                │                       │
                └───────────┬───────────┘
                            ▼
                ┌──────────────────────┐
                │   Database Layer     │
                │   (User Storage)     │
                └──────────────────────┘
```

### Component Architecture

```
┌────────────────────────────────────────────────────────────┐
│                      App Component                         │
│  ┌──────────────────────────────────────────────────────┐ │
│  │  - Session Management (localStorage)                 │ │
│  │  - User State Management                             │ │
│  │  - Logout Handler                                    │ │
│  └──────────────────────────────────────────────────────┘ │
└────────────────────────────────────────────────────────────┘
                            │
            ┌───────────────┴───────────────┐
            │                               │
            ▼                               ▼
┌─────────────────────┐         ┌─────────────────────┐
│  Auth Component     │         │  User Dashboard     │
│  ┌───────────────┐  │         │  ┌───────────────┐  │
│  │ Login Form    │  │         │  │ Profile View  │  │
│  │ Signup Form   │  │         │  │ Logout Button │  │
│  │ Google Button │  │         │  └───────────────┘  │
│  └───────────────┘  │         └─────────────────────┘
└─────────────────────┘
            │
            ▼
┌─────────────────────────────────────────────────────────┐
│                   Auth Service                          │
│  ┌───────────────────────────────────────────────────┐  │
│  │  - login(email, password)                         │  │
│  │  - signup(userData)                               │  │
│  │  - loginWithGoogle(idToken)                       │  │
│  └───────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────┘
            │
            ▼
┌─────────────────────────────────────────────────────────┐
│              HttpClient (Angular)                       │
│              Backend API Communication                  │
└─────────────────────────────────────────────────────────┘
```

### Authentication Flow Diagram

```
┌─────────┐                                    ┌──────────┐
│  User   │                                    │ Backend  │
└────┬────┘                                    └────┬─────┘
     │                                              │
     │  1. Enter Credentials                        │
     ├─────────────────────────────────────────────►│
     │                                              │
     │  2. Validate & Generate JWT                  │
     │◄─────────────────────────────────────────────┤
     │                                              │
     │  3. Store Token in localStorage              │
     ├──────────────┐                               │
     │              │                               │
     │◄─────────────┘                               │
     │                                              │
     │  4. Access Protected Resources               │
     │     (with Authorization Header)              │
     ├─────────────────────────────────────────────►│
     │                                              │
     │  5. Return User Data                         │
     │◄─────────────────────────────────────────────┤
     │                                              │
```

### Google OAuth Flow

```
┌──────┐         ┌──────────┐         ┌─────────┐         ┌──────────┐
│ User │         │ Frontend │         │ Google  │         │ Backend  │
└──┬───┘         └────┬─────┘         └────┬────┘         └────┬─────┘
   │                  │                    │                   │
   │ Click Google     │                    │                   │
   │ Sign-In Button   │                    │                   │
   ├─────────────────►│                    │                   │
   │                  │                    │                   │
   │                  │ Open OAuth Popup   │                   │
   │                  ├───────────────────►│                   │
   │                  │                    │                   │
   │  Authenticate    │                    │                   │
   ├──────────────────┼───────────────────►│                   │
   │                  │                    │                   │
   │                  │  Return ID Token   │                   │
   │                  │◄───────────────────┤                   │
   │                  │                    │                   │
   │                  │  Send ID Token     │                   │
   │                  ├───────────────────────────────────────►│
   │                  │                    │                   │
   │                  │                    │  Verify Token     │
   │                  │                    │  with Google      │
   │                  │                    │◄──────────────────┤
   │                  │                    │                   │
   │                  │                    │  Token Valid      │
   │                  │                    ├──────────────────►│
   │                  │                    │                   │
   │                  │  Return JWT + User Data               │
   │                  │◄───────────────────────────────────────┤
   │                  │                    │                   │
   │  Show Dashboard  │                    │                   │
   │◄─────────────────┤                    │                   │
   │                  │                    │                   │
```

### Data Flow Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                    PRESENTATION LAYER                       │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐        │
│  │   Login     │  │   Signup    │  │  Dashboard  │        │
│  │   Form      │  │   Form      │  │    View     │        │
│  └──────┬──────┘  └──────┬──────┘  └──────┬──────┘        │
└─────────┼─────────────────┼─────────────────┼──────────────┘
          │                 │                 │
          └────────┬────────┴────────┬────────┘
                   │                 │
┌──────────────────┼─────────────────┼────────────────────────┐
│                  ▼                 ▼    SERVICE LAYER       │
│         ┌────────────────────────────────────┐             │
│         │        Auth Service                │             │
│         │  - HTTP Requests                   │             │
│         │  - Token Management                │             │
│         │  - Error Handling                  │             │
│         └────────────────┬───────────────────┘             │
└──────────────────────────┼─────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│                    API LAYER                                │
│  ┌──────────────────────────────────────────────────────┐  │
│  │  POST /auth/generate-token                           │  │
│  │  POST /auth/login-with-google                        │  │
│  │  POST /api/v1/users                                  │  │
│  └──────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│                  BACKEND SERVICES                           │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐     │
│  │ JWT Service  │  │ User Service │  │ OAuth Service│     │
│  └──────────────┘  └──────────────┘  └──────────────┘     │
└─────────────────────────────────────────────────────────────┘
```

---

## ✨ Features

### 🔐 Authentication
- **Email/Password Login** - Traditional authentication with JWT
- **User Registration** - Self-service account creation
- **Google OAuth 2.0** - One-click social login
- **Session Persistence** - localStorage-based session management
- **Secure Logout** - Complete session cleanup

### 🎨 User Interface
- **Responsive Design** - Works on all devices
- **Modern UI/UX** - Clean, intuitive interface
- **Tab-based Navigation** - Easy switching between Login/Signup
- **Real-time Validation** - Instant feedback on form inputs
- **Error Handling** - User-friendly error messages

### 🔧 Technical Features
- **Standalone Components** - Modern Angular architecture
- **Type Safety** - Full TypeScript implementation
- **Reactive Forms** - Angular Forms with two-way binding
- **HTTP Interceptors Ready** - Easy to add auth headers
- **Modular Architecture** - Scalable component structure

---

## 🛠️ Technology Stack

| Category | Technology | Version | Purpose |
|----------|-----------|---------|---------|
| **Framework** | Angular | 18.0 | Frontend framework |
| **Language** | TypeScript | 5.4 | Type-safe JavaScript |
| **Styling** | Tailwind CSS | 3.4 | Utility-first CSS |
| **HTTP Client** | Angular HttpClient | 18.0 | API communication |
| **OAuth** | @abacritt/angularx-social-login | 2.3 | Google authentication |
| **State Management** | localStorage | Native | Session persistence |
| **Build Tool** | Angular CLI | 18.0 | Development & build |

---

## 📁 Project Structure

```
google-login-app/
├── src/
│   ├── app/
│   │   ├── components/
│   │   │   └── auth/
│   │   │       ├── auth.component.ts       # Auth logic
│   │   │       ├── auth.component.html     # Login/Signup UI
│   │   │       └── auth.component.css      # Component styles
│   │   ├── services/
│   │   │   └── auth.service.ts             # API integration
│   │   ├── app.component.ts                # Root component
│   │   ├── app.component.html              # Main template
│   │   ├── app.config.ts                   # App configuration
│   │   └── app.routes.ts                   # Route definitions
│   ├── index.html                          # Entry HTML
│   ├── main.ts                             # Bootstrap file
│   └── styles.css                          # Global styles
├── package.json                            # Dependencies
├── tsconfig.json                           # TypeScript config
├── tailwind.config.js                      # Tailwind config
└── angular.json                            # Angular config
```

---

## 🚀 Setup & Installation

### Prerequisites
- Node.js (v18+ recommended)
- npm or yarn
- Angular CLI (`npm install -g @angular/cli`)

### Installation Steps

```bash
# 1. Navigate to project directory
cd google-login-app

# 2. Install dependencies
npm install --legacy-peer-deps

# 3. Start development server
ng serve

# 4. Open browser
# Navigate to http://localhost:4200
```

### Build for Production

```bash
# Production build
ng build --configuration production

# Output: dist/google-login-app/
```

---

## 🔑 Authentication Flow

### 1. Email/Password Login

**User Flow:**
1. User enters email and password
2. Frontend sends credentials to `/auth/generate-token`
3. Backend validates and returns JWT token
4. Token stored in localStorage
5. User redirected to dashboard

**Code Example:**
```typescript
// auth.service.ts
login(email: string, password: string): Observable<any> {
  return this.http.post(`${this.baseUrl}/auth/generate-token`, 
    { email, password }
  );
}
```

### 2. User Registration

**User Flow:**
1. User fills signup form (name, email, password, about)
2. Frontend sends data to `/api/v1/users`
3. Backend creates user account
4. Success message shown
5. Auto-switch to login tab

**Code Example:**
```typescript
// auth.service.ts
signup(userData: any): Observable<any> {
  return this.http.post(`${this.baseUrl}/api/v1/users`, userData);
}
```

### 3. Google OAuth Login

**User Flow:**
1. User clicks Google Sign-In button
2. Google OAuth popup opens
3. User authenticates with Google
4. Google returns ID token
5. Frontend sends token to `/auth/login-with-google`
6. Backend verifies token with Google
7. Backend returns JWT + user data
8. User logged in

**Code Example:**
```typescript
// auth.component.ts
this.socialAuthService.authState.subscribe((authData) => {
  if (authData && authData.idToken) {
    this.authService.loginWithGoogle(authData.idToken).subscribe({
      next: (res) => this.handleAuthSuccess(res)
    });
  }
});
```

---

## 🌐 API Integration

### Base URL
```typescript
private baseUrl = 'http://localhost:9090';
```

### Endpoints

| Method | Endpoint | Purpose | Request Body | Response |
|--------|----------|---------|--------------|----------|
| POST | `/auth/generate-token` | Login | `{email, password}` | `{token, user, refreshToken}` |
| POST | `/api/v1/users` | Signup | `{name, email, password, about}` | `{userId, name, email, ...}` |
| POST | `/auth/login-with-google` | Google Login | `{idToken}` | `{token, user}` |

### Request/Response Examples

**Login Request:**
```json
POST /auth/generate-token
{
  "email": "user@example.com",
  "password": "password123"
}
```

**Login Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "userId": "uuid",
    "name": "John Doe",
    "email": "user@example.com",
    "imagePath": "profile.jpg"
  },
  "refreshToken": {
    "token": "refresh-token-here",
    "expiryDate": "2025-01-01T00:00:00"
  }
}
```

---

## 📦 Component Documentation

### AppComponent

**Purpose:** Root component managing application state and user session

**Key Features:**
- Session persistence via localStorage
- User state management
- Logout functionality
- Conditional rendering (Auth vs Dashboard)

**Properties:**
```typescript
user: any = null;  // Current logged-in user
```

**Methods:**
```typescript
ngOnInit(): void              // Load user from localStorage
logout(): void                // Clear session and logout
```

---

### AuthComponent

**Purpose:** Handles all authentication operations

**Key Features:**
- Tab-based UI (Login/Signup)
- Form validation
- Error/success messaging
- Google OAuth integration

**Properties:**
```typescript
isLogin: boolean              // Toggle between login/signup
loginData: {email, password}  // Login form data
signupData: {name, email, password, about}  // Signup form data
errorMessage: string          // Error display
successMessage: string        // Success display
```

**Methods:**
```typescript
toggleMode(): void            // Switch between login/signup
onLogin(): void              // Handle login submission
onSignup(): void             // Handle signup submission
handleAuthSuccess(res): void // Process successful auth
```

---

### AuthService

**Purpose:** API communication layer for authentication

**Methods:**
```typescript
login(email, password): Observable<any>
signup(userData): Observable<any>
loginWithGoogle(idToken): Observable<any>
```

---

## 💻 Development Guide

### Running Development Server

```bash
ng serve
# or with custom port
ng serve --port 4300
```

### Code Generation

```bash
# Generate component
ng generate component components/my-component

# Generate service
ng generate service services/my-service
```

### Linting & Formatting

```bash
# Lint code
ng lint

# Format code (if prettier configured)
npm run format
```

### Testing

```bash
# Run unit tests
ng test

# Run e2e tests
ng e2e
```

---

## 🔒 Security Considerations

### Current Implementation
- ✅ JWT token storage in localStorage
- ✅ Google OAuth token verification on backend
- ✅ HTTPS recommended for production
- ✅ CORS configured on backend

### Recommendations
- 🔐 Add HTTP interceptor for automatic token injection
- 🔐 Implement token refresh mechanism
- 🔐 Add route guards for protected routes
- 🔐 Consider HttpOnly cookies for token storage
- 🔐 Implement CSRF protection

---

## 🎨 UI/UX Features

### Design System
- **Color Palette:** Purple/Blue gradient theme
- **Typography:** System fonts with Tailwind defaults
- **Spacing:** Consistent 8px grid system
- **Shadows:** Layered shadow system for depth

### Responsive Breakpoints
```css
sm: 640px   /* Mobile landscape */
md: 768px   /* Tablet */
lg: 1024px  /* Desktop */
xl: 1280px  /* Large desktop */
```

---

## 🐛 Troubleshooting

### Common Issues

**1. npm install fails**
```bash
# Solution: Use legacy peer deps
npm install --legacy-peer-deps
```

**2. Google login not working**
- Check Google Client ID in `app.config.ts`
- Verify authorized origins in Google Console
- Ensure backend is running

**3. CORS errors**
- Verify backend CORS configuration
- Check API base URL in `auth.service.ts`

---

## 📝 Environment Configuration

### Development
```typescript
// auth.service.ts
private baseUrl = 'http://localhost:9090';
```

### Production
```typescript
// Use environment files
import { environment } from '../environments/environment';
private baseUrl = environment.apiUrl;
```

---

## 🚀 Deployment

### Build for Production
```bash
ng build --configuration production
```

### Deploy to Hosting
```bash
# Example: Deploy to Firebase
firebase deploy

# Example: Deploy to Netlify
netlify deploy --prod --dir=dist/google-login-app
```

---

## 📚 Additional Resources

- [Angular Documentation](https://angular.io/docs)
- [Tailwind CSS Docs](https://tailwindcss.com/docs)
- [Google OAuth Guide](https://developers.google.com/identity/protocols/oauth2)
- [JWT Best Practices](https://tools.ietf.org/html/rfc8725)

---

## 👥 Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open Pull Request

---

## 📄 License

This project is part of the Spring Guides practice repository.

---

## 📞 Support

For issues and questions:
- Create an issue in the repository
- Check existing documentation
- Review backend API documentation

---

**Built with ❤️ using Angular & Tailwind CSS**
