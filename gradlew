#!/usr/bin/env sh
# Lightweight Gradle bootstrapper for environments without gradle-wrapper.jar
# Downloads the specified Gradle distribution into .gradle/wrapper and runs the included gradle binary.
# This is a fallback to make ./gradlew work when the official gradle-wrapper.jar isn't included.

set -e
ROOT_DIR="$(cd "$(dirname "$0")" && pwd)"
GRADLE_VERSION="9.6"
WRAPPER_DIR="$ROOT_DIR/.gradle/wrapper/gradle-$GRADLE_VERSION"
ZIP_PATH="$ROOT_DIR/.gradle/wrapper/gradle-$GRADLE_VERSION-bin.zip"
DIST_URL="https://services.gradle.org/distributions/gradle-$GRADLE_VERSION-bin.zip"

ensure_unzip() {
  if command -v unzip >/dev/null 2>&1; then
    return 0
  fi
  if command -v jar >/dev/null 2>&1; then
    return 0
  fi
  echo "Error: 'unzip' or Java 'jar' command required to extract Gradle distribution. Please install unzip or Java JDK." >&2
  exit 1
}

download_dist() {
  mkdir -p "$(dirname "$ZIP_PATH")"
  echo "Downloading Gradle $GRADLE_VERSION from $DIST_URL ..."
  if command -v curl >/dev/null 2>&1; then
    curl -fSL "$DIST_URL" -o "$ZIP_PATH"
  elif command -v wget >/dev/null 2>&1; then
    wget -O "$ZIP_PATH" "$DIST_URL"
  else
    echo "Error: curl or wget required to download Gradle distribution." >&2
    exit 1
  fi
}

extract_dist() {
  echo "Extracting Gradle to $WRAPPER_DIR ..."
  mkdir -p "$ROOT_DIR/.gradle/wrapper"
  if command -v unzip >/dev/null 2>&1; then
    unzip -q "$ZIP_PATH" -d "$ROOT_DIR/.gradle/wrapper"
  else
    # try using jar (bundled with JDK)
    (cd "$ROOT_DIR/.gradle/wrapper" && jar -xf "$ZIP_PATH")
  fi
  # the zip extracts to gradle-<version> directory; ensure the path matches
  if [ -d "$ROOT_DIR/.gradle/wrapper/gradle-$GRADLE_VERSION" ]; then
    mv -f "$ROOT_DIR/.gradle/wrapper/gradle-$GRADLE_VERSION" "$WRAPPER_DIR" 2>/dev/null || true
  fi
}

run_gradle() {
  GRADLE_BIN="$WRAPPER_DIR/bin/gradle"
  if [ ! -x "$GRADLE_BIN" ]; then
    chmod +x "$GRADLE_BIN" 2>/dev/null || true
  fi
  if [ ! -x "$GRADLE_BIN" ]; then
    echo "Gradle binary not found or not executable: $GRADLE_BIN" >&2
    exit 1
  fi
  exec "$GRADLE_BIN" "$@"
}

# Main
if [ ! -d "$WRAPPER_DIR" ]; then
  if [ ! -f "$ZIP_PATH" ]; then
    download_dist
  fi
  extract_dist
fi

ensure_unzip
run_gradle "$@"
