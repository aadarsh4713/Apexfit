# APEXFIT
Premium Android fitness tracker foundation.

## Included
- Luxury multi-color Compose UI
- Dashboard, workout studio, nutrition, progress, profile
- 10 customizable workout slots
- Split selection: Full Body, Upper/Lower, PPL, PPLUL, Body-Part, Arnold, and Chest+Biceps / Back+Triceps / Legs+Shoulders
- Exercise library with muscle/equipment filtering and search
- GitHub Actions APK build workflow

## Build
Open in Android Studio (Ladybug or newer), let Gradle sync, then Build > Build APK(s).
Or push to GitHub and run **Build APK** under Actions; the debug APK is uploaded as an artifact.

## Product direction
This is the foundation/MVP. Production version should add persistent local storage (Room/DataStore), food database, barcode scanning, workout timers, notifications, charts, cloud backup, authentication, exercise media, and signed release builds.
