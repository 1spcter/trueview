@echo off
REM Lightweight Gradle bootstrapper for Windows. Downloads Gradle distribution and runs the included gradle.bat.
SETLOCAL ENABLEDELAYEDEXPANSION
set ROOT_DIR=%~dp0nset GRADLE_VERSION=9.6
set WRAPPER_DIR=%ROOT_DIR%\.gradle\wrapper\gradle-%GRADLE_VERSION%
set ZIP_PATH=%ROOT_DIR%\.gradle\wrapper\gradle-%GRADLE_VERSION%-bin.zip
set DIST_URL=https://services.gradle.org/distributions/gradle-%GRADLE_VERSION%-bin.zip

:: PowerShell helper to download file
where powershell >nul 2>&1nif errorlevel 1 (
  echo PowerShell is required to bootstrap Gradle on Windows.
  exit /b 1
)
nif not exist "%WRAPPER_DIR%" (
  if not exist "%ZIP_PATH%" (
    echo Downloading Gradle %GRADLE_VERSION% ...
    powershell -Command "(New-Object Net.WebClient).DownloadFile('%DIST_URL%', '%ZIP_PATH%')"
    if errorlevel 1 (
      echo Failed to download Gradle distribution.
      exit /b 1
    )
  )
  echo Extracting Gradle ...
  powershell -Command "Add-Type -AssemblyName System.IO.Compression.FileSystem; [System.IO.Compression.ZipFile]::ExtractToDirectory('%ZIP_PATH%', '%ROOT_DIR%\.gradle\wrapper')"
  if errorlevel 1 (
    echo Failed to extract Gradle distribution.
    exit /b 1
  )
)
nset GRADLE_BAT=%WRAPPER_DIR%\bin\gradle.batnif not exist "%GRADLE_BAT%" (
  echo Gradle executable not found: %GRADLE_BAT%
  exit /b 1
)
n:: Run gradle with passed arguments
"%GRADLE_BAT%" %*
