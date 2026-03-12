# System Architecture Diagrams

This file contains Mermaid diagrams that can be rendered in GitHub, VS Code, or any Mermaid-compatible viewer.

## 1. High-Level System Architecture

```mermaid
graph TB
    subgraph "Frontend Layer"
        UI[Angular SPA<br/>Port 4200]
    end
    
    subgraph "Backend Layer"
        API[Spring Boot API<br/>Port 9090]
        AUTH[Authentication Service]
        OAUTH[Google OAuth Verifier]
    end
    
    subgraph "External Services"
        GOOGLE[Google OAuth 2.0]
    end
    
    subgraph "Data Layer"
        DB[(Database<br/>User Data)]
    end
    
    UI -->|HTTP/HTTPS| API
    API --> AUTH
    API --> OAUTH
    OAUTH -->|Verify Token| GOOGLE
    AUTH --> DB
    OAUTH --> DB
    
    style UI fill:#e1f5ff
    style API fill:#fff4e6
    style DB fill:#f3e5f5
    style GOOGLE fill:#e8f5e9
```

## 2. Component Architecture

```mermaid
graph TD
    subgraph "App Component"
        APP[App Component<br/>Session Management]
    end
    
    subgraph "Feature Components"
        AUTH_COMP[Auth Component<br/>Login/Signup Forms]
        DASH[Dashboard Component<br/>User Profile]
    end
    
    subgraph "Services"
        AUTH_SVC[Auth Service<br/>API Communication]
        HTTP[HttpClient<br/>HTTP Requests]
    end
    
    subgraph "External"
        SOCIAL[Social Auth Service<br/>Google OAuth]
        BACKEND[Backend API]
    end
    
    APP --> AUTH_COMP
    APP --> DASH
    AUTH_COMP --> AUTH_SVC
    AUTH_COMP --> SOCIAL
    AUTH_SVC --> HTTP
    HTTP --> BACKEND
    SOCIAL --> BACKEND
    
    style APP fill:#bbdefb
    style AUTH_COMP fill:#c8e6c9
    style DASH fill:#c8e6c9
    style AUTH_SVC fill:#fff9c4
    style BACKEND fill:#ffccbc
```

## 3. Authentication Flow - Email/Password

```mermaid
sequenceDiagram
    participant U as User
    participant F as Frontend
    participant B as Backend
    participant DB as Database
    
    U->>F: Enter email & password
    F->>F: Validate form
    F->>B: POST /auth/generate-token
    B->>DB: Verify credentials
    DB-->>B: User found
    B->>B: Generate JWT token
    B-->>F: Return {token, user, refreshToken}
    F->>F: Store in localStorage
    F-->>U: Show dashboard
```

## 4. Google OAuth Flow

```mermaid
sequenceDiagram
    participant U as User
    participant F as Frontend
    participant G as Google
    participant B as Backend
    participant DB as Database
    
    U->>F: Click Google Sign-In
    F->>G: Open OAuth popup
    U->>G: Authenticate
    G-->>F: Return ID Token
    F->>B: POST /auth/login-with-google<br/>{idToken}
    B->>G: Verify ID Token
    G-->>B: Token valid + user info
    B->>DB: Find or create user
    DB-->>B: User data
    B->>B: Generate JWT
    B-->>F: Return {token, user}
    F->>F: Store in localStorage
    F-->>U: Show dashboard
```

## 5. User Registration Flow

```mermaid
sequenceDiagram
    participant U as User
    participant F as Frontend
    participant B as Backend
    participant DB as Database
    
    U->>F: Fill signup form
    F->>F: Validate inputs
    F->>B: POST /api/v1/users<br/>{name, email, password, about}
    B->>B: Hash password
    B->>DB: Create user record
    DB-->>B: User created
    B-->>F: Return user data
    F-->>U: Show success message
    F->>F: Switch to login tab
```

## 6. Data Flow Architecture

```mermaid
graph LR
    subgraph "Presentation"
        LOGIN[Login Form]
        SIGNUP[Signup Form]
        DASH[Dashboard]
    end
    
    subgraph "Service Layer"
        AUTH_SVC[Auth Service]
        STORAGE[localStorage]
    end
    
    subgraph "API Layer"
        EP1[/auth/generate-token]
        EP2[/auth/login-with-google]
        EP3[/api/v1/users]
    end
    
    subgraph "Backend"
        JWT[JWT Service]
        USER[User Service]
        OAUTH[OAuth Service]
    end
    
    LOGIN --> AUTH_SVC
    SIGNUP --> AUTH_SVC
    AUTH_SVC --> EP1
    AUTH_SVC --> EP2
    AUTH_SVC --> EP3
    AUTH_SVC --> STORAGE
    EP1 --> JWT
    EP2 --> OAUTH
    EP3 --> USER
    STORAGE --> DASH
    
    style LOGIN fill:#e3f2fd
    style SIGNUP fill:#e3f2fd
    style DASH fill:#e3f2fd
    style AUTH_SVC fill:#fff3e0
    style JWT fill:#f3e5f5
    style USER fill:#f3e5f5
    style OAUTH fill:#f3e5f5
```

## 7. State Management Flow

```mermaid
stateDiagram-v2
    [*] --> Unauthenticated
    
    Unauthenticated --> Authenticating: Login/Signup/Google
    
    Authenticating --> Authenticated: Success
    Authenticating --> Unauthenticated: Failure
    
    Authenticated --> Dashboard: Show User Info
    Dashboard --> Authenticated: Active Session
    
    Authenticated --> Unauthenticated: Logout
    Authenticated --> Unauthenticated: Token Expired
    
    Unauthenticated --> [*]
```

## 8. Component Lifecycle

```mermaid
graph TD
    START([App Starts]) --> INIT[App Component Init]
    INIT --> CHECK{User in<br/>localStorage?}
    
    CHECK -->|Yes| LOAD[Load User Data]
    CHECK -->|No| SHOW_AUTH[Show Auth Component]
    
    LOAD --> SHOW_DASH[Show Dashboard]
    
    SHOW_AUTH --> USER_ACTION{User Action}
    USER_ACTION -->|Login| LOGIN_FLOW[Login Flow]
    USER_ACTION -->|Signup| SIGNUP_FLOW[Signup Flow]
    USER_ACTION -->|Google| GOOGLE_FLOW[Google OAuth Flow]
    
    LOGIN_FLOW --> SUCCESS{Success?}
    SIGNUP_FLOW --> SUCCESS
    GOOGLE_FLOW --> SUCCESS
    
    SUCCESS -->|Yes| STORE[Store in localStorage]
    SUCCESS -->|No| ERROR[Show Error]
    
    STORE --> RELOAD[Reload App]
    RELOAD --> SHOW_DASH
    
    ERROR --> SHOW_AUTH
    
    SHOW_DASH --> LOGOUT{Logout?}
    LOGOUT -->|Yes| CLEAR[Clear localStorage]
    CLEAR --> SHOW_AUTH
    
    style START fill:#4caf50
    style SHOW_DASH fill:#2196f3
    style ERROR fill:#f44336
    style SUCCESS fill:#ff9800
```

## 9. Module Dependencies

```mermaid
graph TD
    subgraph "Core Modules"
        ANGULAR[Angular Core 18.0]
        COMMON[Angular Common]
        FORMS[Angular Forms]
        HTTP[Angular HttpClient]
        ROUTER[Angular Router]
    end
    
    subgraph "Third-Party"
        SOCIAL[angularx-social-login]
        TAILWIND[Tailwind CSS]
    end
    
    subgraph "Application"
        APP[App Module]
        AUTH_COMP[Auth Component]
        AUTH_SVC[Auth Service]
    end
    
    APP --> ANGULAR
    APP --> COMMON
    APP --> ROUTER
    
    AUTH_COMP --> FORMS
    AUTH_COMP --> COMMON
    AUTH_COMP --> SOCIAL
    AUTH_COMP --> AUTH_SVC
    
    AUTH_SVC --> HTTP
    
    APP --> TAILWIND
    
    style ANGULAR fill:#dd0031
    style APP fill:#42a5f5
    style AUTH_COMP fill:#66bb6a
    style AUTH_SVC fill:#ffa726
```

## 10. Error Handling Flow

```mermaid
graph TD
    START[User Action] --> API_CALL[API Call]
    
    API_CALL --> RESPONSE{Response<br/>Status}
    
    RESPONSE -->|200 OK| SUCCESS[Process Success]
    RESPONSE -->|400 Bad Request| VALIDATION[Show Validation Error]
    RESPONSE -->|401 Unauthorized| AUTH_ERROR[Show Auth Error]
    RESPONSE -->|403 Forbidden| FORBIDDEN[Show Access Denied]
    RESPONSE -->|500 Server Error| SERVER_ERROR[Show Server Error]
    RESPONSE -->|Network Error| NETWORK_ERROR[Show Network Error]
    
    SUCCESS --> UPDATE_UI[Update UI]
    
    VALIDATION --> DISPLAY_ERROR[Display Error Message]
    AUTH_ERROR --> DISPLAY_ERROR
    FORBIDDEN --> DISPLAY_ERROR
    SERVER_ERROR --> DISPLAY_ERROR
    NETWORK_ERROR --> DISPLAY_ERROR
    
    DISPLAY_ERROR --> USER_RETRY{User<br/>Retry?}
    USER_RETRY -->|Yes| API_CALL
    USER_RETRY -->|No| END[End]
    
    UPDATE_UI --> END
    
    style SUCCESS fill:#4caf50
    style VALIDATION fill:#ff9800
    style AUTH_ERROR fill:#f44336
    style SERVER_ERROR fill:#f44336
    style NETWORK_ERROR fill:#f44336
```

---

## How to View These Diagrams

### Option 1: GitHub
- Push this file to GitHub
- Diagrams will render automatically

### Option 2: VS Code
- Install "Markdown Preview Mermaid Support" extension
- Open this file and preview

### Option 3: Online Viewer
- Visit https://mermaid.live/
- Copy and paste diagram code

### Option 4: IntelliJ IDEA
- Install "Mermaid" plugin
- Diagrams will render in markdown preview
