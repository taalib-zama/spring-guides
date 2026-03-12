# 🚀 Deployment Guide

Complete guide for deploying the Electronic Store Frontend to production.

---

## 📋 Pre-Deployment Checklist

- [ ] All tests passing
- [ ] Environment variables configured
- [ ] API endpoints updated for production
- [ ] Google OAuth credentials for production domain
- [ ] HTTPS enabled
- [ ] Error tracking configured
- [ ] Performance optimized
- [ ] Security headers configured
- [ ] CORS configured on backend
- [ ] Build tested locally

---

## 🔧 Environment Configuration

### 1. Create Environment Files

```bash
# Create production environment
ng generate environments
```

**src/environments/environment.ts** (Development)
```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:9090',
  googleClientId: 'your-dev-client-id.apps.googleusercontent.com'
};
```

**src/environments/environment.prod.ts** (Production)
```typescript
export const environment = {
  production: true,
  apiUrl: 'https://api.yourapp.com',
  googleClientId: 'your-prod-client-id.apps.googleusercontent.com'
};
```

### 2. Update Services to Use Environment

**auth.service.ts**
```typescript
import { environment } from '../../environments/environment';

export class AuthService {
  private baseUrl = environment.apiUrl;
  // ...
}
```

**app.config.ts**
```typescript
import { environment } from '../environments/environment';

export const appConfig: ApplicationConfig = {
  providers: [
    {
      provide: SOCIAL_AUTH_CONFIG,
      useValue: {
        providers: [{
          id: GoogleLoginProvider.PROVIDER_ID,
          provider: new GoogleLoginProvider(environment.googleClientId)
        }]
      }
    }
  ]
};
```

---

## 🏗️ Build for Production

### Standard Build

```bash
# Production build
ng build --configuration production

# Output location
# dist/google-login-app/
```

### Build with Custom Base Href

```bash
# If deploying to subdirectory
ng build --configuration production --base-href /app/
```

### Build Optimization Options

```bash
# With source maps (for debugging)
ng build --configuration production --source-map

# Without source maps (smaller size)
ng build --configuration production --source-map=false

# With stats for analysis
ng build --configuration production --stats-json
```

---

## 🌐 Deployment Options

### Option 1: Firebase Hosting

**Setup:**
```bash
# Install Firebase CLI
npm install -g firebase-tools

# Login
firebase login

# Initialize
firebase init hosting
```

**firebase.json**
```json
{
  "hosting": {
    "public": "dist/google-login-app/browser",
    "ignore": ["firebase.json", "**/.*", "**/node_modules/**"],
    "rewrites": [{
      "source": "**",
      "destination": "/index.html"
    }]
  }
}
```

**Deploy:**
```bash
# Build
ng build --configuration production

# Deploy
firebase deploy --only hosting
```

---

### Option 2: Netlify

**Setup:**
```bash
# Install Netlify CLI
npm install -g netlify-cli

# Login
netlify login
```

**netlify.toml**
```toml
[build]
  command = "ng build --configuration production"
  publish = "dist/google-login-app/browser"

[[redirects]]
  from = "/*"
  to = "/index.html"
  status = 200
```

**Deploy:**
```bash
# Deploy
netlify deploy --prod
```

---

### Option 3: AWS S3 + CloudFront

**1. Build Application**
```bash
ng build --configuration production
```

**2. Create S3 Bucket**
```bash
aws s3 mb s3://your-app-name
```

**3. Configure Bucket for Static Hosting**
```bash
aws s3 website s3://your-app-name \
  --index-document index.html \
  --error-document index.html
```

**4. Upload Files**
```bash
aws s3 sync dist/google-login-app/browser/ s3://your-app-name \
  --delete \
  --cache-control max-age=31536000,public
```

**5. Create CloudFront Distribution**
- Origin: S3 bucket
- Default Root Object: index.html
- Error Pages: 404 -> /index.html (200)

---

### Option 4: Vercel

**Setup:**
```bash
# Install Vercel CLI
npm install -g vercel

# Login
vercel login
```

**vercel.json**
```json
{
  "buildCommand": "ng build --configuration production",
  "outputDirectory": "dist/google-login-app/browser",
  "rewrites": [
    { "source": "/(.*)", "destination": "/index.html" }
  ]
}
```

**Deploy:**
```bash
vercel --prod
```

---

### Option 5: Docker + Nginx

**Dockerfile**
```dockerfile
# Stage 1: Build
FROM node:18 AS build
WORKDIR /app
COPY package*.json ./
RUN npm install --legacy-peer-deps
COPY . .
RUN npm run build -- --configuration production

# Stage 2: Serve
FROM nginx:alpine
COPY --from=build /app/dist/google-login-app/browser /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
```

**nginx.conf**
```nginx
server {
    listen 80;
    server_name localhost;
    root /usr/share/nginx/html;
    index index.html;

    location / {
        try_files $uri $uri/ /index.html;
    }

    # Security headers
    add_header X-Frame-Options "SAMEORIGIN" always;
    add_header X-Content-Type-Options "nosniff" always;
    add_header X-XSS-Protection "1; mode=block" always;

    # Gzip compression
    gzip on;
    gzip_types text/plain text/css application/json application/javascript text/xml application/xml application/xml+rss text/javascript;
}
```

**Build & Run:**
```bash
# Build image
docker build -t electronic-store-frontend .

# Run container
docker run -d -p 80:80 electronic-store-frontend
```

---

## 🔒 Security Configuration

### 1. Content Security Policy

**index.html**
```html
<meta http-equiv="Content-Security-Policy" 
      content="default-src 'self'; 
               script-src 'self' 'unsafe-inline' https://accounts.google.com; 
               style-src 'self' 'unsafe-inline'; 
               img-src 'self' data: https:; 
               connect-src 'self' https://api.yourapp.com;">
```

### 2. Security Headers (Nginx)

```nginx
add_header X-Frame-Options "SAMEORIGIN" always;
add_header X-Content-Type-Options "nosniff" always;
add_header X-XSS-Protection "1; mode=block" always;
add_header Referrer-Policy "strict-origin-when-cross-origin" always;
add_header Permissions-Policy "geolocation=(), microphone=(), camera=()" always;
```

### 3. HTTPS Configuration

```nginx
server {
    listen 443 ssl http2;
    server_name yourapp.com;

    ssl_certificate /path/to/cert.pem;
    ssl_certificate_key /path/to/key.pem;
    ssl_protocols TLSv1.2 TLSv1.3;
    ssl_ciphers HIGH:!aNULL:!MD5;

    # ... rest of config
}

# Redirect HTTP to HTTPS
server {
    listen 80;
    server_name yourapp.com;
    return 301 https://$server_name$request_uri;
}
```

---

## 📊 Performance Optimization

### 1. Enable Compression

**angular.json**
```json
{
  "projects": {
    "google-login-app": {
      "architect": {
        "build": {
          "configurations": {
            "production": {
              "optimization": true,
              "outputHashing": "all",
              "sourceMap": false,
              "namedChunks": false,
              "extractLicenses": true,
              "vendorChunk": false,
              "buildOptimizer": true
            }
          }
        }
      }
    }
  }
}
```

### 2. Lazy Loading

```typescript
// app.routes.ts
export const routes: Routes = [
  {
    path: 'admin',
    loadComponent: () => import('./admin/admin.component')
      .then(m => m.AdminComponent)
  }
];
```

### 3. Service Worker (PWA)

```bash
# Add PWA support
ng add @angular/pwa

# Build with service worker
ng build --configuration production
```

---

## 🔍 Monitoring & Analytics

### 1. Google Analytics

**index.html**
```html
<!-- Google Analytics -->
<script async src="https://www.googletagmanager.com/gtag/js?id=GA_MEASUREMENT_ID"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());
  gtag('config', 'GA_MEASUREMENT_ID');
</script>
```

### 2. Error Tracking (Sentry)

```bash
npm install @sentry/angular
```

**main.ts**
```typescript
import * as Sentry from "@sentry/angular";

Sentry.init({
  dsn: "your-sentry-dsn",
  environment: environment.production ? 'production' : 'development'
});
```

---

## 🧪 Pre-Production Testing

### 1. Build Locally

```bash
ng build --configuration production
```

### 2. Serve Production Build

```bash
# Install http-server
npm install -g http-server

# Serve
cd dist/google-login-app/browser
http-server -p 8080
```

### 3. Test Checklist

- [ ] All pages load correctly
- [ ] Login/Signup works
- [ ] Google OAuth works
- [ ] API calls succeed
- [ ] Images load
- [ ] Responsive on mobile
- [ ] No console errors
- [ ] Performance acceptable (Lighthouse)

---

## 🔄 CI/CD Pipeline

### GitHub Actions Example

**.github/workflows/deploy.yml**
```yaml
name: Deploy to Production

on:
  push:
    branches: [ main ]

jobs:
  build-and-deploy:
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v3
    
    - name: Setup Node.js
      uses: actions/setup-node@v3
      with:
        node-version: '18'
    
    - name: Install dependencies
      run: npm ci --legacy-peer-deps
    
    - name: Build
      run: npm run build -- --configuration production
    
    - name: Deploy to Firebase
      uses: FirebaseExtended/action-hosting-deploy@v0
      with:
        repoToken: '${{ secrets.GITHUB_TOKEN }}'
        firebaseServiceAccount: '${{ secrets.FIREBASE_SERVICE_ACCOUNT }}'
        channelId: live
        projectId: your-project-id
```

---

## 📝 Post-Deployment

### 1. Verify Deployment

```bash
# Check if site is live
curl -I https://yourapp.com

# Check SSL certificate
openssl s_client -connect yourapp.com:443 -servername yourapp.com
```

### 2. Update Google OAuth

- Go to Google Cloud Console
- Add production domain to authorized origins
- Add redirect URIs

### 3. Monitor Logs

```bash
# Firebase
firebase functions:log

# AWS CloudWatch
aws logs tail /aws/cloudfront/your-distribution

# Docker
docker logs -f container-name
```

---

## 🐛 Troubleshooting

### Issue: 404 on Refresh

**Solution:** Configure server to redirect all routes to index.html

### Issue: CORS Errors

**Solution:** Update backend CORS configuration to include production domain

### Issue: Google OAuth Not Working

**Solution:** 
1. Check authorized origins in Google Console
2. Verify client ID in environment.prod.ts
3. Ensure HTTPS is enabled

### Issue: Slow Load Times

**Solution:**
1. Enable gzip compression
2. Use CDN for static assets
3. Implement lazy loading
4. Optimize images

---

## 📞 Support & Resources

- [Angular Deployment Guide](https://angular.io/guide/deployment)
- [Firebase Hosting Docs](https://firebase.google.com/docs/hosting)
- [Netlify Docs](https://docs.netlify.com/)
- [AWS S3 Static Hosting](https://docs.aws.amazon.com/AmazonS3/latest/userguide/WebsiteHosting.html)

---

**Deployment Checklist Complete! 🎉**
