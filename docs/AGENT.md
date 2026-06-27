# Nami Japanese - Agent Instructions

## Project Coding Rules

### General Principles
1. **Write clean, production-ready code**
2. **Follow Kotlin conventions** and idioms
3. **Use meaningful names** for variables, functions, and classes
4. **Keep functions small** and focused (max 30 lines preferred)
5. **Avoid magic numbers** - use named constants
6. **Handle errors gracefully** - never crash silently

### Code Style
- Use ktlint standards
- Prefer `val` over `var`
- Use extension functions when appropriate
- Use sealed classes for type safety
- Use data classes for models
- Use companion objects for constants

## Architecture Rules

### Dependency Direction
```
Presentation → Domain → Data
     ↑
     │
   Core (shared)
```

### Layer Responsibilities

#### Presentation Layer
- **Screen**: Composable functions, UI rendering only
- **ViewModel**: State management, business logic orchestration
- **State**: Immutable data classes representing UI state

#### Domain Layer
- **UseCase**: Single business operation, orchestrates repositories
- **Repository Interface**: Contract for data access
- **Model**: Domain models, no framework dependencies

#### Data Layer
- **Repository Impl**: Implements domain interfaces
- **Local DataSource**: Room, DataStore operations
- **Entity**: Database models
- **Mapper**: Convert between Entity and Domain models

#### Core Layer
- **DesignSystem**: Theme, components, typography
- **Navigation**: Routes, nav graph, arguments
- **Database**: Room setup, DAOs, migrations
- **DataStore**: User preferences
- **Model**: Shared domain models
- **Common**: Extensions, utilities

### Rules
1. Domain layer MUST NOT depend on Android framework
2. Domain layer MUST NOT depend on Data layer
3. Data layer implements Domain repository interfaces
4. Presentation layer consumes UseCases
5. Core modules are shared across all layers

## Compose UI Rules

### Composable Functions
- Name screens as `XxxScreen`
- Name components as `XxxCard`, `XxxButton`, `XxxItem`
- Keep composables focused on single responsibility
- Use `@Composable` with `@Preview` for all screens

### State Management
- Use `remember` for local state
- Use `viewModel` for screen state
- Use `StateFlow` for reactive data
- Use `collectAsStateWithLifecycle()` for collection

### Theming
- Use `MaterialTheme` for colors, typography
- Use design tokens from `core/designsystem`
- Support light and dark themes
- Use semantic color names (primary, surface, etc.)

### Animations
- Use `animateContentSize()` for simple animations
- Use `AnimatedVisibility` for show/hide
- Use `Crossfade` for content switching
- Keep animations smooth (60fps)

### Performance
- Use `key()` in lazy lists
- Use `derivedStateOf` when appropriate
- Avoid unnecessary recompositions
- Use `Immutable` annotations for stable lists

## Naming Conventions

### Files
- Screens: `XxxScreen.kt`
- ViewModels: `XxxViewModel.kt`
- UseCases: `GetXxxUseCase.kt`
- Repositories: `XxxRepository.kt` (interface), `XxxRepositoryImpl.kt`
- Entities: `XxxEntity.kt`
- Models: `Xxx.kt` (domain), `XxxDto.kt` (data)

### Classes
- Screens: `XxxScreen` (composable function)
- ViewModels: `XxxViewModel`
- UseCases: `GetXxxUseCase`
- Repositories: `XxxRepository`, `XxxRepositoryImpl`
- Entities: `XxxEntity`

### Functions
- Use camelCase
- Use descriptive verbs: `loadXxx`, `saveXxx`, `calculateXxx`
- Use extension functions: `String.toLocalDate()`
- Use suspend for async: `suspend fun getXxx()`

### Variables
- Use camelCase
- Use meaningful names
- Use `is` prefix for booleans: `isLoading`
- Use `uiState` for screen state

### Constants
- Use SCREAMING_SNAKE_CASE
- Group in companion objects
- Use descriptive names: `MAX_RETRY_COUNT`

## File Editing Limits

### Maximum Lines Per File
- Composables: 300 lines
- ViewModels: 250 lines
- UseCases: 100 lines
- Repositories: 200 lines
- DAOs: 150 lines
- Entities: 50 lines

### Split Strategy
If a file exceeds limits:
1. Extract sub-composables to separate files
2. Split UseCases into smaller, focused operations
3. Create specialized DAOs
4. Use feature packages for related code

### Operation Limits
- Maximum 300-350 lines per edit operation
- Split large files into multiple edits
- Prefer small, incremental changes
- Test after each significant change

## Build/Test Restrictions

### DO NOT Run Automatically
- `./gradlew build`
- `./gradlew test`
- `./gradlew lint`
- `./gradlew detekt`
- `./gradlew ktlint`
- `./gradlew assembleDebug`

### Always Ask Before
- Running any Gradle command
- Installing dependencies
- Running emulator
- Generating code

### Safe Operations
- Creating new files
- Editing existing files
- Running `git` commands
- Reading documentation

## Documentation Update Rules

### When to Update Docs
- After adding new feature
- After changing architecture
- After adding new entity/model
- After modifying navigation
- After changing data flow

### What to Update
- `docs/SDD.md` - Architecture changes
- `docs/DATA_MODEL.md` - New entities
- `docs/MVP_ROADMAP.md` - Phase progress
- `docs/AGENT.md` - New rules
- `docs/PROJECT_OVERVIEW.md` - Feature additions

### Documentation Style
- Use markdown
- Include code examples
- Keep it concise
- Update tables when needed
- Add diagrams for complex flows

## How Future Agents Should Safely Continue the Project

### Before Making Changes
1. Read `docs/PROJECT_OVERVIEW.md` for context
2. Read `docs/SDD.md` for architecture
3. Read `docs/DATA_MODEL.md` for entities
4. Read `docs/MVP_ROADMAP.md` for phase status
5. Read this file (`AGENT.md`) for rules

### During Development
1. Follow architecture rules strictly
2. Respect file size limits
3. Keep changes incremental
4. Use existing patterns
5. Don't break existing functionality

### After Changes
1. Verify compilation (ask user)
2. Update relevant documentation
3. Commit with clear message
4. Describe what was changed
5. List files modified

### Safety Checklist
- [ ] Architecture rules followed
- [ ] File size limits respected
- [ ] No breaking changes
- [ ] Documentation updated
- [ ] Code is clean and readable
- [ ] Error handling included
- [ ] No hardcoded values
- [ ] Tests considered (if applicable)

### Common Pitfalls to Avoid
1. Don't put business logic in composables
2. Don't use `runBlocking` in coroutines
3. Don't leak ViewModels into composables
4. Don't use global state
5. Don't skip error handling
6. Don't add unnecessary dependencies
7. Don't break the dependency direction
8. Don't modify core modules without understanding impact

### When Uncertain
1. Ask the user for clarification
2. Read existing code for patterns
3. Follow existing conventions
4. Keep changes minimal
5. Document decisions made
