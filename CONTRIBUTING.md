# Contributing to Nami Japanese

Thank you for your interest in contributing to Nami Japanese! This document provides guidelines for contributing to the project.

## Welcome

We welcome contributions from the community. Whether it's fixing a bug, improving documentation, or adding a new feature, your help is appreciated.

## Getting Started

1. Fork the repository
2. Clone your fork locally
3. Create a new branch for your changes
4. Make your changes
5. Test your changes
6. Submit a pull request

## Development Setup

1. Open the project in Android Studio
2. Sync Gradle
3. Run on an emulator or device

## Branch Naming Convention

Use descriptive branch names:

- `feature/short-description` — for new features
- `fix/short-description` — for bug fixes
- `docs/short-description` — for documentation changes
- `refactor/short-description` — for code refactoring

## Commit Messages

Write clear, concise commit messages:

- Use the imperative mood ("Add feature" not "Added feature")
- Keep the subject line under 72 characters
- Reference issues when applicable

Examples:
```
Add kana detail screen navigation
Fix quiz score calculation on device rotation
Update README with build instructions
```

## Pull Request Checklist

Before submitting a PR, ensure:

- [ ] App compiles without errors
- [ ] No unrelated changes are included
- [ ] UI has been tested manually if UI changed
- [ ] Screenshots are included for UI changes
- [ ] Data migration is considered if Room schema changed
- [ ] Privacy impact is considered if auth/data changed
- [ ] Code follows existing patterns and conventions

## Code Style

- Follow Kotlin coding conventions
- Use MVVM + Repository pattern
- Keep UI consistent with the Nami design system
- Avoid adding dependencies without discussion
- Do not run formatters on unrelated files

## Bug Reports

When reporting bugs, please include:

- Clear summary of the issue
- Steps to reproduce
- Expected behavior
- Actual behavior
- Device/OS information
- App version or branch

## Feature Requests

When suggesting features:

- Describe the problem you're trying to solve
- Propose a solution
- Mention any alternatives you've considered
- Provide additional context if helpful

## Questions?

If you have questions about contributing, feel free to open an issue with your question.
