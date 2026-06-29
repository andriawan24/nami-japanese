# Security Policy

## Reporting Vulnerabilities

If you discover a security vulnerability, please do not open a public GitHub issue. Instead, please report it privately by emailing:

[security@example.com](mailto:security@example.com)

**TODO**: Replace `security@example.com` with the real maintainer email before publishing.

## Security Guidelines

### Do Not Commit

- Keystore files or keystore passwords
- `local.properties` (contains SDK paths and signing configs)
- Service account JSON files
- Google OAuth client secrets
- API keys or secret tokens
- Production signing configurations

### Google OAuth

- Android OAuth client IDs are typically safe to include in client-side code
- Client secrets must never be committed to the repository
- Ensure `google-services.json` is not committed unless it is safe for public access

### General

- Never commit secrets, credentials, or sensitive configuration
- Review `.gitignore` to ensure sensitive files are excluded
- Use environment variables or local configuration for secrets
