#!/bin/sh
# Gradle wrapper script for standalone use
# This will download and use Gradle 8.5 automatically

GRADLE_VERSION=8.5
GRADLE_DIR="$HOME/.gradle/wrapper/dists/gradle-$GRADLE_VERSION-bin"
GRADLE_ZIP="$GRADLE_DIR/gradle-$GRADLE_VERSION-bin.zip"
GRADLE_HOME="$GRADLE_DIR/gradle-$GRADLE_VERSION"

# Download Gradle if not present
if [ ! -d "$GRADLE_HOME" ]; then
    mkdir -p "$GRADLE_DIR"
    echo "Downloading Gradle $GRADLE_VERSION..."
    if [ ! -f "$GRADLE_ZIP" ]; then
        wget -q "https://services.gradle.org/distributions/gradle-$GRADLE_VERSION-bin.zip" -O "$GRADLE_ZIP"
    fi
    unzip -q "$GRADLE_ZIP" -d "$GRADLE_DIR"
fi

# Set environment
export ANDROID_HOME=/opt/android-sdk
export PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools

# Run Gradle
exec "$GRADLE_HOME/bin/gradle" "$@"
