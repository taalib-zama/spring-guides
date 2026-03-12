# Electronic Store - Login/Signup Implementation

## Features Implemented

### 1. **Email/Password Login**
- Users can login with email and password
- Integrates with backend `/auth/generate-token` endpoint
- Stores JWT token and user data in localStorage

### 2. **User Registration (Signup)**
- New users can create accounts
- Fields: Name, Email, Password, About (optional)
- Integrates with backend `/api/v1/users` endpoint
- Auto-switches to login after successful registration

### 3. **Google OAuth Login** (Existing - Kept Intact)
- Google Sign-In button integration
- Integrates with backend `/auth/login-with-google` endpoint

### 4. **User Dashboard**
- Displays logged-in user information
- Shows user profile picture, name, and email
- Logout functionality

## Files Created/Modified

### New Files:
- `src/app/services/auth.service.ts` - Authentication service
- `src/app/components/auth/auth.component.ts` - Auth component logic
- `src/app/components/auth/auth.component.html` - Auth component UI
- `src/app/components/auth/auth.component.css` - Auth component styles

### Modified Files:
- `src/app/app.component.ts` - Updated to use new auth component
- `src/app/app.component.html` - Updated UI to show auth component

## How to Run

```bash
cd google-login-app
npm install --legacy-peer-deps
ng serve
```

Open browser: `http://localhost:4200`

## Backend Requirements

Ensure backend is running on `http://localhost:9090` with these endpoints:
- `POST /auth/generate-token` - Login
- `POST /api/v1/users` - Signup
- `POST /auth/login-with-google` - Google login

## Usage

1. **Login**: Enter email and password, click "Sign In"
2. **Signup**: Click "Sign Up" tab, fill form, click "Create Account"
3. **Google Login**: Click Google Sign-In button
4. **Logout**: Click "Sign Out" button on dashboard

## Notes

- JWT token stored in localStorage
- User data persists across page refreshes
- Google login functionality preserved from original implementation
- Responsive design with Tailwind CSS
