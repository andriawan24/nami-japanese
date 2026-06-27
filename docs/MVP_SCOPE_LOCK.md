# Nami Japanese - MVP Scope Lock

## IN SCOPE (MVP)

| Feature | Status | Details |
|---------|--------|---------|
| Hiragana List | ✅ Complete | All 46 characters, group filters |
| Katakana List | ✅ Complete | All 46 characters, group filters |
| Kana Detail | ✅ Complete | Character, romaji, stroke count, info |
| Writing Practice | ✅ Complete | Canvas drawing, clear, undo, complete |
| Quiz | ✅ Complete | 10 multiple-choice questions, score |
| Progress | ✅ Complete | Learned count, streak, progress bars |
| Settings | ✅ Complete | Sound, reminder, about |

## OUT OF SCOPE (Post-MVP)

| Feature | Phase | Notes |
|---------|-------|-------|
| Onboarding flow | Phase 2 | Simple first-launch check |
| Vocabulary lessons | Phase 3 | New data model needed |
| Audio pronunciation | Phase 3 | Requires audio files |
| Spaced repetition (SRS) | Phase 4 | Algorithm implementation |
| AI tutor | Phase 5 | External API integration |
| Handwriting recognition | Phase 5 | ML Kit integration |
| Cloud sync / accounts | Phase 6 | Backend required |
| JLPT modules | Phase 6 | New content required |
| Achievements/badges | Phase 4 | Gamification layer |
| Flashcards | Phase 3 | Separate from quiz |

## Data Strategy

| Data | Storage | Reason |
|------|---------|--------|
| 92 Kana characters | In-memory | Immutable, fast, simple |
| User progress | Room | User-generated, needs persistence |
| Daily streak | Room | User-generated, needs persistence |
| Learning sessions | Room | User-generated, for analytics |
| Writing strokes | Ephemeral | Not saved for MVP |
| Preferences | DataStore | Simple key-value storage |

## Architecture Constraints

1. **4 modules only**: app, core, feature, docs
2. **No new dependencies** unless critical
3. **No network calls** in MVP
4. **No user accounts** in MVP
5. **No external APIs** in MVP

## Quality Gates

Before marking any feature complete:
- [ ] Screen renders correctly
- [ ] ViewModel handles state
- [ ] Data persists (if applicable)
- [ ] No crashes on rotation
- [ ] Back navigation works
- [ ] Hilt injection works

## Post-MVP Checklist

When starting post-MVP work:
1. Review this document
2. Update scope before implementing
3. Add new dependencies to version catalog
4. Update documentation
5. Test on multiple devices
