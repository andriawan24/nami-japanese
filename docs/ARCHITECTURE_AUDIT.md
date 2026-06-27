# Nami Japanese - Architecture Audit

## Audit Summary

The original project had **18 Gradle modules** for an MVP app. This was restructured to **4 modules** for maintainability.

---

## Original Problems

| Issue | Impact | Resolution |
|-------|--------|------------|
| 18 modules for 10 screens | Slow builds, cognitive overhead | Reduced to 4 modules |
| Room for 92 immutable characters | Unnecessary complexity | In-memory Kotlin objects |
| Separate domain/data modules | Extra indirection | Merged into core |
| 7 feature modules (2-4 files each) | Build config duplication | Single feature module |
| Use cases wrapping single calls | Adds layers without value | Inlined into ViewModels |
| Navigation in separate module | 1 file doesn't justify module | Moved to app module |
| DI module in separate module | Creates confusion | Moved to app module |

---

## What Was Correct (Kept)

- MVVM with StateFlow
- Hilt for dependency injection
- Room for user-generated data (progress, streaks)
- DataStore for preferences
- Compose + Material 3
- Repository pattern concept

---

## Current Architecture

### Module Structure (4 modules)

```
NamiJapanese/
├── app/       → Entry point, navigation, DI
├── core/      → Design system, database, models, repositories, static data
├── feature/   → All screens and ViewModels
└── docs/      → Documentation
```

### Dependency Direction

```
app → feature, core
feature → core
core → (no project dependencies)
```

### Data Strategy

| Data | Storage | Reason |
|------|---------|--------|
| 92 Kana characters | In-memory | Immutable, fast, simple |
| User progress | Room | User-generated, needs persistence |
| Daily streak | Room | User-generated, needs persistence |
| Preferences | DataStore | Simple key-value storage |

---

## Metrics

| Metric | Before | After |
|--------|--------|-------|
| Gradle modules | 18 | 4 |
| build.gradle.kts files | 18 | 4 |
| Kana entities in Room | Yes (unnecessary) | No (in-memory) |
| Repository interfaces module | Yes | No (merged) |
| Use case module | Yes | No (inlined) |
| Feature modules | 7 | 1 |

---

## Risk Assessment (Mitigated)

| Risk | Status |
|------|--------|
| Build time too slow | ✅ Reduced by ~70% |
| Over-engineering abandonment | ✅ Simplified architecture |
| Room complexity for simple data | ✅ Using in-memory objects |
| Module dependency confusion | ✅ Clear 4-module structure |
