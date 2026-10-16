# TrueView — Advanced First-Person Camera Overhaul

Version: 1.0.0
Target: Minecraft Java Edition 1.26.3 (Fabric)

Summary
-------
TrueView provides subtle, configurable first-person camera improvements (head bob, landing response, sprint FOV, optional roll, and hand presentation). This is a client-side Visual mod only.

Requirements
- Minecraft Java Edition 1.26.3
- Fabric Loader compatible with 1.26.3
- Fabric API matching Minecraft + Fabric versions
- JDK 21
- Gradle 9+ (project includes Gradle configuration for use with the Gradle wrapper)

Building
--------
1. Ensure Java 21 is installed and available as your JAVA_HOME.
2. In the project root, run:
   ./gradlew genSources
   ./gradlew build
   The generated release JAR will be at `build/libs/trueview-1.0.0.jar`.

Notes on mixins
---------------
Some mixin targets are mapping-sensitive. If you see mixin compilation errors:
1. Run `./gradlew genSources` to generate mapping-deobfuscated sources.
2. Inspect the generated sources (e.g., `build/generated/sources/...`) to find the exact FirstPersonRenderer and GameRenderer method names.
3. Adjust the mixin `@Inject(method = "...", ...)` values to match the mapped names, then re-run `./gradlew build`.

Installation
------------
1. Download `trueview-1.0.0.jar` (from build/libs after building).
2. Install Fabric Loader and the matching Fabric API into your Minecraft instance for 1.26.3.
3. Place `trueview-1.0.0.jar` into the `mods` folder in your Minecraft directory (create `mods` if it doesn't exist).
4. Launch Minecraft with the Fabric profile. Open Options -> Controls or Mod Options to find TrueView settings (config screen is available via the mod options if Fabric API version supports it).

Limitations and verification
----------------------------
- I prepared and included all mod source files and pure-Java tests. I could not run `./gradlew build` or verify the generated JAR or test execution in this environment.
- The mixins include conservative injections but may require small mapping name adjustments after you run `./gradlew genSources`. This is standard when targeting a recent Minecraft version where Yarn mapping artifact coordinates must match exactly.
- All pure-Java components (config, interpolation) are unit tested and should run under JDK 21 via `./gradlew test`.

License
-------
MIT
