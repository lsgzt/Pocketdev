#!/bin/bash

# Pocket Dev APK Build Script
# This script builds the Pocket Dev Android application

set -e

echo "======================================"
echo "Pocket Dev - APK Build Script"
echo "======================================"
echo ""

# Check if Android SDK is set
if [ -z "$ANDROID_SDK_ROOT" ] && [ -z "$ANDROID_HOME" ]; then
    echo "ERROR: ANDROID_SDK_ROOT or ANDROID_HOME environment variable is not set"
    echo "Please set it to your Android SDK location"
    exit 1
fi

# Clean previous builds
echo "Cleaning previous builds..."
./gradlew clean

# Build debug APK
echo ""
echo "Building Debug APK..."
./gradlew assembleDebug

# Build release APK (unsigned)
echo ""
echo "Building Release APK (unsigned)..."
./gradlew assembleRelease

echo ""
echo "======================================"
echo "Build Complete!"
echo "======================================"
echo ""
echo "APK files generated:"
echo "  Debug:   app/build/outputs/apk/debug/app-debug.apk"
echo "  Release: app/build/outputs/apk/release/app-release-unsigned.apk"
echo ""
echo "To install on a device:"
echo "  adb install app/build/outputs/apk/debug/app-debug.apk"
echo ""

# Check if key store exists for signing
if [ -f "pocketdev.keystore" ]; then
    echo "Signing release APK..."
    jarsigner -verbose -sigalg SHA1withRSA -digestalg SHA1 \
        -keystore pocketdev.keystore \
        app/build/outputs/apk/release/app-release-unsigned.apk \
        pocketdev
    
    zipalign -v 4 \
        app/build/outputs/apk/release/app-release-unsigned.apk \
        app/build/outputs/apk/release/PocketDev-v1.0.0.apk
    
    echo ""
    echo "Signed APK: app/build/outputs/apk/release/PocketDev-v1.0.0.apk"
else
    echo "NOTE: No keystore found. Release APK is unsigned."
    echo "To create a keystore for signing:"
    echo "  keytool -genkey -v -keystore pocketdev.keystore -alias pocketdev -keyalg RSA -keysize 2048 -validity 10000"
fi

echo ""
echo "Done!"
