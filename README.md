# FinPilot AI

AI powered personal finance and spending intelligence for Android, built with Kotlin and Jetpack Compose.

FinPilot AI is a personal money manager. It does **not** connect to bank accounts. You record your own income and expenses, set budgets, and get reports, forecasts and optional AI summaries. Core features work fully offline.

> Status: **Milestone 1 of 8** (project setup, dashboard UI, navigation). The full README with screenshots, schema and Firebase setup is completed in Milestone 8.

## Milestones

| # | Scope | Status |
|---|-------|--------|
| 1 | Project setup, Material 3 theme, dashboard, navigation | In review |
| 2 | Room database, transaction CRUD, totals | Planned |
| 3 | Budgets, threshold alerts, charts and reports | Planned |
| 4 | AI summaries via Retrofit and a backend endpoint (with consent) | Planned |
| 5 | Baseline budget forecasting | Planned |
| 6 | Firebase Authentication and Firestore sync | Planned |
| 7 | Crashlytics, Analytics, local notifications | Planned |
| 8 | Java interop, tests, accessibility, docs, release | Planned |

## Tech stack (Milestone 1)

| Area | Choice |
|------|--------|
| Language | Kotlin 2.1.20 (JVM 17) |
| Build | Gradle 8.11.1, AGP 8.9.1, Kotlin DSL, version catalog |
| UI | Jetpack Compose (BOM 2025.06.00), Material 3 |
| Navigation | Navigation Compose 2.8.9 with type safe `@Serializable` routes |
| State | ViewModel + StateFlow, `collectAsStateWithLifecycle` |
| SDK | minSdk 26, target/compile 35 |

## Architecture

```
ui (Compose screens, ViewModels)  ──►  domain (models, calculations, use cases)  ◄──  data (Room, Retrofit, Firebase)
```

* **MVVM** for ordinary screens: each screen exposes one immutable `UiState` through `StateFlow`; composables are split into a stateful `Screen` (gets the ViewModel) and a stateless `Content` (previewable, testable).
* **MVI style** will be used for complex forms (add/edit transaction, budget editor) from Milestone 2: intents in, reduced state out.
* **Money is stored as `Long` paise.** Floating point is never used for financial arithmetic. Ratios use basis points (1% = 100 bps).
* Financial calculations live in `domain`, not in composables, so they are unit tested on the JVM.

```
com.example.finpilotai
├── MainActivity.kt
├── domain/model        MonthlyOverview, BudgetStatus, Category
├── ui
│   ├── components      SectionCard, EmptyState, MonthSelector, DonutChart, category visuals
│   ├── dashboard       DashboardScreen, DashboardViewModel, preview only sample data
│   ├── navigation      Routes, FinPilotApp (bottom bar + NavHost)
│   ├── transactions / budgets / reports / settings
│   └── theme           Color, Type, Theme (brand + finance semantic colors)
└── util                MoneyFormatter (INR, Indian grouping), Formatters
```

## Setup

1. Install a recent stable Android Studio with Android SDK 35.
2. Open the `FinPilotAI` folder (File > Open). Let Gradle sync finish.
3. Run the `app` configuration on an emulator or device (Android 8.0+).

Unit tests: `./gradlew testDebugUnitTest` (or right click `app/src/test` > Run Tests).

## Privacy notes

* `android:allowBackup` is disabled because the app stores personal financial records.
* No credentials, `google-services.json`, or keystores are committed (see `.gitignore`).
* Sample numbers exist only in `DashboardPreviewData` for Android Studio previews; the running app never shows invented figures.
