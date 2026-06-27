---
name: Modern Washi
colors:
  surface: '#faf9f6'
  surface-dim: '#dbdad7'
  surface-bright: '#faf9f6'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#f4f3f1'
  surface-container: '#efeeeb'
  surface-container-high: '#e9e8e5'
  surface-container-highest: '#e3e2e0'
  on-surface: '#1a1c1a'
  on-surface-variant: '#564241'
  inverse-surface: '#2f312f'
  inverse-on-surface: '#f2f1ee'
  outline: '#897271'
  outline-variant: '#dcc0bf'
  surface-tint: '#a03e40'
  primary: '#a03e40'
  on-primary: '#ffffff'
  primary-container: '#e57373'
  on-primary-container: '#5e0c15'
  inverse-primary: '#ffb3b1'
  secondary: '#4c56af'
  on-secondary: '#ffffff'
  secondary-container: '#959efd'
  on-secondary-container: '#27308a'
  tertiary: '#5e604d'
  on-tertiary: '#ffffff'
  tertiary-container: '#969781'
  on-tertiary-container: '#2e2f1f'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#ffdad8'
  primary-fixed-dim: '#ffb3b1'
  on-primary-fixed: '#410007'
  on-primary-fixed-variant: '#80272b'
  secondary-fixed: '#e0e0ff'
  secondary-fixed-dim: '#bdc2ff'
  on-secondary-fixed: '#000767'
  on-secondary-fixed-variant: '#343d96'
  tertiary-fixed: '#e4e4cc'
  tertiary-fixed-dim: '#c8c8b0'
  on-tertiary-fixed: '#1b1d0e'
  on-tertiary-fixed-variant: '#474836'
  background: '#faf9f6'
  on-background: '#1a1c1a'
  surface-variant: '#e3e2e0'
typography:
  display-kana:
    fontFamily: Plus Jakarta Sans
    fontSize: 80px
    fontWeight: '500'
    lineHeight: 96px
    letterSpacing: 0.02em
  headline-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 28px
    fontWeight: '700'
    lineHeight: 36px
  headline-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 22px
    fontWeight: '600'
    lineHeight: 28px
  body-lg:
    fontFamily: Inter
    fontSize: 18px
    fontWeight: '400'
    lineHeight: 28px
  body-md:
    fontFamily: Inter
    fontSize: 16px
    fontWeight: '400'
    lineHeight: 24px
  label-lg:
    fontFamily: Inter
    fontSize: 14px
    fontWeight: '600'
    lineHeight: 20px
    letterSpacing: 0.05em
  label-sm:
    fontFamily: Inter
    fontSize: 12px
    fontWeight: '500'
    lineHeight: 16px
    letterSpacing: 0.08em
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  base: 4px
  xs: 8px
  sm: 12px
  md: 16px
  lg: 24px
  xl: 32px
  container-padding: 20px
  gutter: 16px
---

## Brand & Style
The design system is built upon the concept of *digital stationery*—merging the tactile warmth of traditional Japanese paper (Washi) with the precision of a premium modern application. The target audience consists of language learners seeking a focused, meditative environment. 

The aesthetic is a blend of **Minimalism** and **Tactile** design. It emphasizes whitespace (Ma) to reduce cognitive load, utilizing soft rounded surfaces and organic motifs to create an emotional response of calm and steady progress. Visual depth is achieved through gentle layering rather than aggressive shadows, mimicking the way sheets of high-quality paper sit atop one another.

## Colors
The palette is grounded in a warm ivory base, providing a softer reading experience than pure white. 

- **Primary (Sakura Coral):** Used for primary actions, progress indicators, and highlighting correct kana strokes. It evokes energy and growth.
- **Secondary (Deep Indigo):** Reserved for high-contrast text, calligraphic ink elements, and specialized navigation to provide a sense of authority and tradition.
- **Surface (Warm Beige/Off-White):** Used for cards and interactive containers to distinguish content from the ivory background.
- **Functional Colors:** Success, warning, and inactive states are muted to maintain the "calm" profile, ensuring they inform the user without causing visual alarm.

## Typography
Typography is the centerpiece of the learning experience. **Plus Jakarta Sans** is selected for its friendly, open counters and modern geometric feel, which complements the rounded UI. **Inter** provides high legibility for smaller romaji descriptions and interface labels.

The `display-kana` level is the primary focus for flashcards, designed to be large and unobstructed. Titles should use a "soft-bold" weight (600-700) to stand out against the ivory background. Letter spacing is slightly increased in labels to reflect an airy, editorial feel.

## Layout & Spacing
This design system utilizes a **Fluid Grid** optimized for a 390px mobile width. The layout relies on a 4-column system with 20px outer margins to ensure content feels centered and "contained" like a page in a notebook.

- **Vertical Rhythm:** Components are spaced primarily in increments of 8px (md/lg/xl) to maintain a consistent flow.
- **Safe Areas:** Generous top padding (48px+) is used for screen headers to avoid crowding the status bar and notch.
- **Grouping:** Use `lg` (24px) spacing between distinct content sections and `sm` (12px) for related elements within a card.

## Elevation & Depth
Elevation is expressed through **Tonal Layering** and **Ambient Shadows**. Instead of traditional drop shadows that imply a harsh light source, this system uses "Soft Glows"—low-opacity shadows tinted with a hint of the secondary indigo color to make cards appear as if they are resting gently on the paper surface.

- **Level 0 (Background):** #FAF9F6.
- **Level 1 (Cards/Inputs):** #FFFFFF with a 4px blur, 5% opacity Indigo shadow.
- **Level 2 (Floating Actions/Modals):** #FFFFFF with a 12px blur, 10% opacity Indigo shadow.
- **Dividers:** 1px solid lines using #F5F5DC for a subtle, etched-in look.

## Shapes
The shape language is defined by extreme softness. 
- **Main Containers:** A consistent 24px corner radius (`rounded-xl`) is applied to all primary cards and modals to echo the rounded strokes of Hiragana.
- **Interactive Elements:** Buttons and input fields use a 16px radius for a comfortable, "squishy" tactile feel.
- **Action Indicators:** Small decorative elements and progress pips use 100% rounding (pill-shaped or circular).

## Components
- **Primary Buttons:** High-radius (16px+) or fully pill-shaped. Uses the Sakura Coral background with white text. Apply a subtle "press" animation that slightly reduces scale (0.98) to enhance tactility.
- **Learning Cards:** 24px rounded corners, white surface, subtle indigo ambient shadow. Content is centered with the `display-kana` type level.
- **Pill Chips:** Used for category tags (e.g., "N5", "Verbs"). Background: Warm Beige (#F5F5DC); Text: Deep Indigo (#1A237E).
- **Circular Action Buttons:** Floating buttons for "Play Audio" or "Next" should be perfectly circular with an icon centered.
- **Input Fields:** Soft beige backgrounds with no border, becoming Sakura Coral when focused.
- **Motif Integration:** Subtle, low-opacity (5-10%) SVG patterns of Sakura petals or Washi textures may be applied to the background of the *Top App Bar* or *Navigation Bar* to reinforce the stationery theme.