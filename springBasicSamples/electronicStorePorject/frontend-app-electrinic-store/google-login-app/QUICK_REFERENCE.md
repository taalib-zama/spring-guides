# 🚀 Quick Reference Guide

## 📋 Quick Start Commands

```bash
# Install dependencies
npm install --legacy-peer-deps

# Start dev server
ng serve

# Build for production
ng build --production

# Run tests
ng test

# Generate component
ng g c components/my-component

# Generate service
ng g s services/my-service
```

---

## 🔑 Key Files Reference

| File | Purpose | Key Content |
|------|---------|-------------|
| `app.component.ts` | Root component | Session management, logout |
| `auth.component.ts` | Auth logic | Login/signup handlers |
| `auth.service.ts` | API calls | HTTP requests to backend |
| `app.config.ts` | App config | Google OAuth setup |
| `app.routes.ts` | Routing | Route definitions |

---

## 🌐 API Endpoints Quick Reference

```typescript
// Base URL
const BASE_URL = 'http://localhost:9090';

// Login
POST /auth/generate-token
Body: { email: string, password: string }
Response: { token: string, user: User, refreshToken: RefreshToken }

// Signup
POST /api/v1/users
Body: { name: string, email: string, password: string, about?: string }
Response: User

// Google Login
POST /auth/login-with-google
Body: { idToken: string }
Response: { token: string, user: User }
```

---

## 💾 localStorage Keys

```typescript
// Token storage
localStorage.setItem('token', jwtToken);
localStorage.getItem('token');

// User data storage
localStorage.setItem('user', JSON.stringify(userData));
localStorage.getItem('user');

// Clear on logout
localStorage.removeItem('token');
localStorage.removeItem('user');
```

---

## 🎨 Tailwind CSS Classes Used

```css
/* Layout */
.min-h-screen
.flex .items-center .justify-center
.p-4 .p-8
.max-w-md .w-full

/* Colors */
.bg-gradient-to-br .from-blue-900 .via-purple-900 .to-indigo-900
.bg-white .bg-gray-100
.text-gray-800 .text-gray-600

/* Buttons */
.bg-purple-500 .hover:bg-purple-600
.bg-red-500 .hover:bg-red-600
.py-2 .px-6 .rounded-lg

/* Forms */
.border .border-gray-300
.focus:ring-2 .focus:ring-purple-500
.rounded-lg

/* Shadows */
.shadow-2xl .shadow-lg
```

---

## 🔧 Common Code Snippets

### Add HTTP Interceptor for Auth Token

```typescript
// Create interceptor
ng g interceptor interceptors/auth

// auth.interceptor.ts
export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const token = localStorage.getItem('token');
  
  if (token) {
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
  }
  
  return next(req);
};

// Add to app.config.ts
export const appConfig: ApplicationConfig = {
  providers: [
    provideHttpClient(withInterceptors([authInterceptor]))
  ]
};
```

### Add Route Guard

```typescript
// Create guard
ng g guard guards/auth

// auth.guard.ts
export const authGuard: CanActivateFn = (route, state) => {
  const token = localStorage.getItem('token');
  
  if (token) {
    return true;
  }
  
  return false; // or redirect to login
};

// Use in routes
export const routes: Routes = [
  { path: 'dashboard', component: DashboardComponent, canActivate: [authGuard] }
];
```

### Add Environment Configuration

```typescript
// environment.ts
export const environment = {
  production: false,
  apiUrl: 'http://localhost:9090'
};

// environment.prod.ts
export const environment = {
  production: true,
  apiUrl: 'https://api.yourapp.com'
};

// Use in service
import { environment } from '../environments/environment';

private baseUrl = environment.apiUrl;
```

---

## 🐛 Debugging Tips

```typescript
// Enable Angular DevTools
// Install: Chrome Web Store -> Angular DevTools

// Console logging
console.log('User data:', this.user);
console.table(this.loginData);

// Network debugging
// Chrome DevTools -> Network tab
// Filter: XHR to see API calls

// Check localStorage
console.log('Token:', localStorage.getItem('token'));
console.log('User:', JSON.parse(localStorage.getItem('user') || '{}'));
```

---

## 📦 Package.json Scripts

```json
{
  "scripts": {
    "ng": "ng",
    "start": "ng serve",
    "build": "ng build",
    "watch": "ng build --watch --configuration development",
    "test": "ng test"
  }
}
```

---

## 🔒 Security Checklist

- [ ] Use HTTPS in production
- [ ] Implement HTTP interceptor for auth headers
- [ ] Add route guards for protected routes
- [ ] Implement token refresh mechanism
- [ ] Add CSRF protection
- [ ] Sanitize user inputs
- [ ] Implement rate limiting on backend
- [ ] Use environment variables for sensitive data
- [ ] Enable Content Security Policy (CSP)
- [ ] Implement proper error handling

---

## 📱 Responsive Design Breakpoints

```typescript
// Tailwind breakpoints
sm: '640px'   // Mobile landscape
md: '768px'   // Tablet
lg: '1024px'  // Desktop
xl: '1280px'  // Large desktop
2xl: '1536px' // Extra large

// Usage
<div class="w-full md:w-1/2 lg:w-1/3">
```

---

## 🧪 Testing Examples

```typescript
// Component test
describe('AuthComponent', () => {
  it('should toggle between login and signup', () => {
    component.isLogin = true;
    component.toggleMode();
    expect(component.isLogin).toBe(false);
  });
});

// Service test
describe('AuthService', () => {
  it('should call login endpoint', () => {
    service.login('test@test.com', 'password').subscribe();
    const req = httpMock.expectOne(`${service.baseUrl}/auth/generate-token`);
    expect(req.request.method).toBe('POST');
  });
});
```

---

## 📊 Performance Tips

```typescript
// Use OnPush change detection
@Component({
  changeDetection: ChangeDetectionStrategy.OnPush
})

// Lazy load modules
const routes: Routes = [
  { 
    path: 'admin', 
    loadComponent: () => import('./admin/admin.component')
  }
];

// Optimize images
<img loading="lazy" src="..." alt="...">

// Use trackBy in *ngFor
<div *ngFor="let item of items; trackBy: trackByFn">
```

---

## 🔄 Git Workflow

```bash
# Create feature branch
git checkout -b feature/add-login

# Commit changes
git add .
git commit -m "feat: add login functionality"

# Push to remote
git push origin feature/add-login

# Merge to main
git checkout main
git merge feature/add-login
```

---

## 📞 Useful Links

- [Angular Docs](https://angular.io/docs)
- [Tailwind CSS](https://tailwindcss.com/docs)
- [RxJS Docs](https://rxjs.dev/)
- [TypeScript Handbook](https://www.typescriptlang.org/docs/)
- [Google OAuth Guide](https://developers.google.com/identity/protocols/oauth2)

---

## 💡 Pro Tips

1. **Use Angular CLI** - Faster development with code generation
2. **Leverage TypeScript** - Type safety prevents bugs
3. **Component Reusability** - Create small, reusable components
4. **Service Layer** - Keep business logic in services
5. **Reactive Programming** - Use RxJS for async operations
6. **Error Handling** - Always handle errors gracefully
7. **Code Splitting** - Lazy load routes for better performance
8. **Testing** - Write tests as you develop
9. **Documentation** - Comment complex logic
10. **Version Control** - Commit frequently with clear messages

---

**Last Updated:** 2025
