# Building Pocket Dev APK

This guide explains how to build the Pocket Dev Android application from source.

## Prerequisites

### Required Software
1. **Android Studio** (latest stable version)
   - Download from: https://developer.android.com/studio
   
2. **JDK 17** or higher
   - Android Studio includes a bundled JDK
   - Or download from: https://adoptium.net/

3. **Android SDK**
   - Installed automatically with Android Studio
   - Minimum API level: 26 (Android 8.0)
   - Target API level: 34 (Android 14)

### System Requirements
- **RAM**: 8 GB minimum, 16 GB recommended
- **Storage**: 10 GB free space
- **OS**: Windows 10/11, macOS 10.14+, or Linux

## Setup Instructions

### 1. Clone or Download the Project

```bash
git clone https://github.com/yourusername/pocketdev.git
cd pocketdev
```

Or extract the project ZIP file to a directory.

### 2. Open in Android Studio

1. Launch Android Studio
2. Select "Open an existing Android Studio project"
3. Navigate to the `pocketdev` folder and click "OK"
4. Wait for Gradle sync to complete (this may take several minutes)

### 3. Configure SDK (if needed)

If Android Studio prompts for SDK configuration:
1. Click "Install missing platform(s) and sync project"
2. Accept the license agreements
3. Wait for installation to complete

## Building the APK

### Method 1: Using Android Studio (Recommended for beginners)

1. **Build Debug APK:**
   - Go to `Build` → `Build Bundle(s) / APK(s)` → `Build APK(s)`
   - The APK will be generated at:
     ```
     app/build/outputs/apk/debug/app-debug.apk
     ```

2. **Build Release APK:**
   - Go to `Build` → `Generate Signed Bundle / APK`
   - Select "APK" and click "Next"
   - Create a new keystore or select existing one
   - Fill in the keystore details
   - Select "release" build variant
   - Click "Finish"
   - The signed APK will be generated at:
     ```
     app/build/outputs/apk/release/app-release.apk
     ```

### Method 2: Using Command Line

#### On Linux/macOS:

```bash
# Make gradlew executable
chmod +x gradlew

# Build debug APK
./gradlew assembleDebug

# Build release APK (unsigned)
./gradlew assembleRelease
```

#### On Windows:

```cmd
# Build debug APK
gradlew.bat assembleDebug

# Build release APK (unsigned)
gradlew.bat assembleRelease
```

### Method 3: Using the Build Script

```bash
# Run the build script
./build_apk.sh
```

This script will:
1. Clean previous builds
2. Build both debug and release APKs
3. Sign the release APK (if keystore exists)

## Creating a Keystore for Signing

To distribute your APK, you need to sign it with a keystore:

```bash
keytool -genkey -v \
    -keystore pocketdev.keystore \
    -alias pocketdev \
    -keyalg RSA \
    -keysize 2048 \
    -validity 10000
```

When prompted, enter:
- **Keystore password**: Create a strong password
- **Key password**: Can be same as keystore password
- **Your information**: Name, organization, etc.

**Important**: Keep your keystore file secure and back it up. You cannot update your app on the Play Store without the same keystore.

## Installing the APK

### On Emulator

1. Start an Android emulator from Android Studio
2. Run:
   ```bash
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```

### On Physical Device

1. Enable "Developer Options" on your device:
   - Go to `Settings` → `About Phone`
   - Tap "Build Number" 7 times

2. Enable "USB Debugging":
   - Go to `Settings` → `Developer Options`
   - Turn on "USB Debugging"

3. Connect device via USB and run:
   ```bash
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```

### Manual Installation

1. Copy the APK file to your device
2. Enable "Install from Unknown Sources" in device settings
3. Open the APK file on your device
4. Tap "Install"

## Troubleshooting

### Gradle Sync Failed

**Problem**: "Gradle sync failed: Could not find..."

**Solution**:
1. Check your internet connection
2. Try `File` → `Sync Project with Gradle Files`
3. Clear Gradle cache:
   ```bash
   ./gradlew cleanBuildCache
   ```

### Out of Memory Error

**Problem**: Build fails with "OutOfMemoryError"

**Solution**:
1. Open `gradle.properties`
2. Increase heap size:
   ```properties
   org.gradle.jvmargs=-Xmx4096m
   ```

### Chaquopy Not Found

**Problem**: "Could not find com.chaquo.python:gradle..."

**Solution**:
1. Ensure you have the Chaquopy plugin in `build.gradle.kts`:
   ```kotlin
   plugins {
       id("com.chaquo.python") version "14.0.2"
   }
   ```
2. Check that the Chaquopy repository is in `settings.gradle.kts`:
   ```kotlin
   pluginManagement {
       repositories {
           maven("https://chaquo.com/maven")
       }
   }
   ```

### SDK License Not Accepted

**Problem**: "License for package Android SDK Build-Tools not accepted"

**Solution**:
```bash
$ANDROID_SDK_ROOT/cmdline-tools/latest/bin/sdkmanager --licenses
```
Accept all licenses by typing `y` when prompted.

## Build Variants

### Debug Build
- Includes debugging information
- Faster build time
- Larger file size
- For development and testing

### Release Build
- Optimized and obfuscated
- Smaller file size
- Requires signing
- For distribution

## Output Locations

After building, APK files are located at:

```
app/build/outputs/apk/
├── debug/
│   └── app-debug.apk
└── release/
    ├── app-release-unsigned.apk
    └── app-release.apk (if signed)
```

## Continuous Integration

### GitHub Actions Example

Create `.github/workflows/build.yml`:

```yaml
name: Build APK

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v3
    
    - name: Set up JDK 17
      uses: actions/setup-java@v3
      with:
        java-version: '17'
        distribution: 'temurin'
    
    - name: Grant execute permission for gradlew
      run: chmod +x gradlew
    
    - name: Build with Gradle
      run: ./gradlew assembleDebug
    
    - name: Upload APK
      uses: actions/upload-artifact@v3
      with:
        name: app-debug
        path: app/build/outputs/apk/debug/app-debug.apk
```

## Additional Resources

- [Android Developer Guide](https://developer.android.com/studio/build)
- [Chaquopy Documentation](https://chaquo.com/chaquopy/doc/current/)
- [Gradle Build Tool](https://gradle.org/guides/)

## Support

If you encounter issues:
1. Check the [Troubleshooting](#troubleshooting) section
2. Search existing [GitHub Issues](https://github.com/yourusername/pocketdev/issues)
3. Create a new issue with:
   - Error message
   - Steps to reproduce
   - Your environment details

---

**Happy Building!** 🚀
