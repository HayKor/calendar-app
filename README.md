# Calendar
 
An Android calendar app with Google Sign-In and token-based authentication. Built with Jetpack Compose, MVI, and clean architecture.
 
## Tech stack
 
- **UI** — Jetpack Compose, Material 3 Expressive
- **Navigation** — Navigation3
- **DI** — Koin
- **Networking** — Ktor + OkHttp
- **Local storage** — DataStore
- **Auth** — Credential Manager (Google Sign-In) + JWT access/refresh tokens
- **Linting** — Detekt with Compose rules
## Project structure
 
```
feature/
    ...       — feature modules  
core/
  common/     — shared domain models, DataResult, UiText
  di/         — Koin modules
  navigation/ — NavDestination, NavigationRoot, transitions
  ui/theme/   — colors, typography, spacing, shapes
```
 
## Dev environment setup
 
### Prerequisites
 
| Tool | Version |
|---|---|
| Android Studio | Ladybug or newer |
| JDK | 17 |
| Android SDK | API 36 (compile), API 24 (min) |
 
### 1. Clone the repo
 
```bash
git clone https://github.com/haykor/calendar.git
cd calendar
```
 
### 2. Configure local properties
 
Create a `local.properties` file in the project root (it is gitignored):
 
```properties
dev.api.url=http://10.0.2.2:8080/         # your backend base URL
dev.google_web_client_id=YOUR_CLIENT_ID   # from Google Cloud Console
```
 
For a physical device replace `10.0.2.2` with your machine's local IP address.
 
### 3. Google Sign-In setup
 
1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Create an OAuth 2.0 **Web client** credential (not Android)
3. Copy the client ID into `dev.google_web_client_id` above
4. Add your app's SHA-1 fingerprint to the OAuth consent screen
Get your debug SHA-1:
```bash
./gradlew signingReport
```
 
### 4. Build and run
 
```bash
./gradlew assembleDebug
```
 
Or just press **Run** in Android Studio.
 
### 5. Code style
 
The project uses Detekt for static analysis. Run it before pushing:
 
```bash
./gradlew detekt
```
