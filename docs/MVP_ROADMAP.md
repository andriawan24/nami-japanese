# Nami Japanese - MVP Roadmap

## Phase 1: Project Setup and Base Architecture ✅

**Status**: In Progress

### Goals
- Establish project foundation
- Set up architecture layers
- Create core modules
- Implement navigation

### Tasks
- [x] Create Android project with Kotlin
- [x] Add Gradle dependencies
- [x] Set up module structure
- [x] Create core/designsystem module
- [x] Create core/navigation module
- [x] Create core/database module
- [x] Create core/datastore module
- [x] Create core/model module
- [x] Create core/common module
- [x] Create domain/repository module
- [x] Create domain/usecase module
- [x] Create data/repository module
- [x] Create data/local module
- [ ] Set up Hilt dependency injection
- [ ] Create base ViewModel class
- [ ] Create base UseCase class
- [ ] Set up Compose navigation
- [ ] Create placeholder screens
- [ ] Create main activity

### Deliverables
- Working project structure
- Compilable codebase
- Navigation skeleton
- Basic theme

---

## Phase 2: Hiragana Learning

**Status**: Not Started

### Goals
- Implement Hiragana character display
- Create lesson list screen
- Create kana detail screen
- Seed Hiragana data

### Tasks
- [ ] Create KanaCharacter entity
- [ ] Create KanaDao
- [ ] Seed 46 Hiragana characters
- [ ] Create HiraganaRepository
- [ ] Create GetHiraganaCharactersUseCase
- [ ] Create HiraganaListViewModel
- [ ] Create HiraganaListScreen
- [ ] Create KanaDetailViewModel
- [ ] Create KanaDetailScreen
- [ ] Implement group filtering
- [ ] Add character search

### Deliverables
- Functional Hiragana lesson list
- Kana detail screen
- All 46 characters accessible

---

## Phase 3: Katakana Learning

**Status**: Not Started

### Goals
- Mirror Hiragana functionality for Katakana
- Create lesson list screen
- Reuse kana detail screen

### Tasks
- [ ] Seed 46 Katakana characters
- [ ] Create KatakanaRepository
- [ ] Create GetKatakanaCharactersUseCase
- [ ] Create KatakanaListViewModel
- [ ] Create KatakanaListScreen
- [ ] Reuse KanaDetailScreen for Katakana
- [ ] Implement navigation to Katakana

### Deliverables
- Functional Katakana lesson list
- Katakana characters accessible
- Unified kana experience

---

## Phase 4: Flashcard and Quiz

**Status**: Not Started

### Goals
- Create flashcard review system
- Implement basic quiz functionality
- Track quiz performance

### Tasks
- [ ] Create FlashcardScreen
- [ ] Implement flashcard flip animation
- [ ] Create character selection for quiz
- [ ] Create QuizScreen
- [ ] Implement multiple choice questions
- [ ] Create quiz scoring system
- [ ] Store quiz results
- [ ] Create QuizHistoryScreen

### Deliverables
- Functional flashcards
- Basic quiz with scoring
- Performance tracking

---

## Phase 5: Writing Canvas

**Status**: Not Started

### Goals
- Implement drawing canvas
- Basic stroke capture
- Save practice progress

### Tasks
- [ ] Create Canvas composables
- [ ] Implement touch input handling
- [ ] Create stroke data structure
- [ ] Implement clear functionality
- [ ] Implement undo (basic)
- [ ] Create WritingPracticeViewModel
- [ ] Save practice sessions
- [ ] Display reference character
- [ ] Add stroke count indicator

### Deliverables
- Functional drawing canvas
- Stroke capture and display
- Practice session saving

---

## Phase 6: Progress Tracking

**Status**: Not Started

### Goals
- Track learning progress
- Implement streak system
- Create progress visualizations

### Tasks
- [ ] Create UserProgress entity
- [ ] Create DailyStreak entity
- [ ] Create ProgressRepository
- [ ] Create GetProgressUseCase
- [ ] Create ProgressViewModel
- [ ] Create ProgressScreen
- [ ] Implement streak tracking
- [ ] Create progress charts
- [ ] Add achievement badges (basic)
- [ ] Create daily reminder logic

### Deliverables
- Progress tracking screen
- Streak system
- Visual progress indicators

---

## Phase 7: Polish and Play Store Preparation

**Status**: Not Started

### Goals
- Refine UI/UX
- Add animations
- Prepare for Play Store

### Tasks
- [ ] Polish animations
- [ ] Add accessibility support
- [ ] Optimize performance
- [ ] Add dark theme
- [ ] Create app icon
- [ ] Add splash screen
- [ ] Implement onboarding fully
- [ ] Add settings screen
- [ ] Create store listing
- [ ] Add privacy policy

### Deliverables
- Polished app experience
- Play Store ready
- Complete onboarding

---

## Future Phases

### Phase 8: Vocabulary Learning
- Add vocabulary database
- Create vocabulary lessons
- Add audio pronunciation
- Implement spaced repetition

### Phase 9: Advanced Quizzes
- Listening quizzes
- Matching quizzes
- Fill-in-the-blank
- Sentence building

### Phase 10: AI Tutor Integration
- Chat interface
- Natural language processing
- Context-aware hints
- Personalized recommendations

### Phase 11: Handwriting Recognition
- ML Kit integration
- Real-time character recognition
- Stroke order validation
- Detailed feedback

### Phase 12: JLPT Preparation
- N5 module
- N4 module
- N3 module
- Practice tests

### Phase 13: Community Features
- User accounts
- Cloud sync
- Leaderboards
- Social features

---

## Progress Tracking

### Current Phase
**Phase 1**: Project Setup and Base Architecture

### Completion Status
| Phase | Status | Progress |
|-------|--------|----------|
| 1 | In Progress | 0% |
| 2 | Not Started | 0% |
| 3 | Not Started | 0% |
| 4 | Not Started | 0% |
| 5 | Not Started | 0% |
| 6 | Not Started | 0% |
| 7 | Not Started | 0% |

### Milestones
- [ ] Phase 1 Complete: Base architecture ready
- [ ] Phase 2 Complete: Hiragana learning functional
- [ ] Phase 3 Complete: Katakana learning functional
- [ ] Phase 4 Complete: Quiz and flashcards working
- [ ] Phase 5 Complete: Writing canvas implemented
- [ ] Phase 6 Complete: Progress tracking active
- [ ] Phase 7 Complete: Play Store launch

---

## Risk Mitigation

### Technical Risks
- **Canvas performance**: Use hardware acceleration, optimize paths
- **Database migrations**: Plan carefully, use destructive for MVP
- **Compose performance**: Profile regularly, minimize recompositions

### Timeline Risks
- **Scope creep**: Stick to MVP features
- **Complexity**: Break into smaller tasks
- **Testing**: Add tests incrementally

### Mitigation Strategies
- Regular code reviews
- Incremental development
- Documentation first
- Architecture validation
