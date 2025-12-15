# Jetpack Navigation - Travelupa App

Aplikasi Android yang mengimplementasikan Jetpack Navigation untuk mengelola navigasi antar layar dengan Firebase Authentication.

## Deskripsi Aplikasi

**Travelupa** adalah aplikasi rekomendasi tempat wisata yang membantu pengguna menemukan destinasi wisata menarik di Indonesia. Aplikasi ini menggunakan Jetpack Navigation Component untuk menangani navigasi antar layar dan Firebase Authentication untuk manajemen pengguna.

## Alur Navigasi

### Diagram Alur Navigasi

```
┌─────────────────────┐
│                     │
│  Greeting Screen    │
│  (Welcome Page)     │
│                     │
└──────────┬──────────┘
           │
           │ Click "Mulai"
           │ popUpTo(Greeting) { inclusive = true }
           ↓
┌─────────────────────┐
│                     │
│   Login Screen      │
│  (Authentication)   │
│                     │
└──────────┬──────────┘
           │
           │ Login Success
           │ popUpTo(Login) { inclusive = true }
           ↓
┌─────────────────────┐
│                     │
│ RekomendasiTempat   │
│   Screen (Home)     │
│                     │
└──────────┬──────────┘
           │
           │ Click "Logout" + FirebaseAuth.signOut()
           │ popUpTo(RekomendasiTempat) { inclusive = true }
           │
           └─────────────────┐
                             │
                             ↓
                   ┌─────────────────────┐
                   │  Greeting Screen    │
                   │  (Back to Welcome)  │
                   └─────────────────────┘
```

### Penjelasan Alur Navigasi

#### 1**Greeting Screen → Login Screen**
- **Trigger**: User menekan tombol "Mulai"
- **Navigasi**: `navController.navigate(Screen.Login.route)`
- **Back Stack Management**: `popUpTo(Screen.Greeting.route) { inclusive = true }`
- **Hasil**: Greeting screen dihapus dari back stack, user tidak bisa kembali dengan tombol back

#### 2**Login Screen → RekomendasiTempat Screen**
- **Trigger**: Login berhasil (Firebase Authentication success)
- **Navigasi**: `navController.navigate(Screen.RekomendasiTempat.route)`
- **Back Stack Management**: `popUpTo(Screen.Login.route) { inclusive = true }`
- **Hasil**: Login screen dihapus dari back stack, user tidak bisa kembali ke login

#### 3**RekomendasiTempat Screen → Greeting Screen**
- **Trigger**: User menekan tombol "Logout"
- **Proses**: 
  1. Firebase sign out: `FirebaseAuth.getInstance().signOut()`
  2. Navigasi: `navController.navigate(Screen.Greeting.route)`
- **Back Stack Management**: `popUpTo(Screen.RekomendasiTempat.route) { inclusive = true }`
- **Hasil**: Semua screen dihapus dari back stack, kembali ke welcome screen


## Implementasi 

### 1. Navigation Routes (Screen.kt)

```kotlin
sealed class Screen(val route: String) {
    object Greeting : Screen("greeting")
    object Login : Screen("login")
    object RekomendasiTempat : Screen("rekomendasi_tempat")
}
```

### 2. Navigation Host (AppNavigation.kt)

```kotlin
@Composable
fun AppNavigation(currentUser: FirebaseUser?) {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = if (currentUser != null) 
            Screen.RekomendasiTempat.route 
        else 
            Screen.Greeting.route
    ) {
        // Composable destinations...
    }
}
```

### 3. Firebase Authentication Integration

**Login:**
```kotlin
auth.signInWithEmailAndPassword(email, password)
    .addOnCompleteListener { task ->
        if (task.isSuccessful) {
            onLoginSuccess()
        }
    }
```

**Register:**
```kotlin
auth.createUserWithEmailAndPassword(email, password)
    .addOnCompleteListener { task ->
        if (task.isSuccessful) {
            onLoginSuccess()
        }
    }
```

**Logout:**
```kotlin
FirebaseAuth.getInstance().signOut()
navController.navigate(Screen.Greeting.route)
```

### Firebase Configuration

File `google-services.json` berisi konfigurasi Firebase untuk project ini:
- **Project ID**: jetpacknavigation-dcf91
- **Package Name**: com.example.jetpacknavigation
- **Authentication**: Email/Password enabled
