# CICERO: EXPRESSIONISM ON TRIAL

"What if a Roman courtroom, German Expressionism, and an AI literary-analysis machine fused into a stylish indie courtroom nightmare?"

## Overview

*CICERO: EXPRESSIONISM ON TRIAL* is a stylized indie courtroom game built with ClojureScript, re-frame, and a local AI backend (Ollama). The player takes the role of the opposing lawyer against Cicero. You must write increasingly expressionistic speeches to rhetorically defeat Cicero while being judged by a diegetic AI entity known as "The Consilium".

## Features

- **2D Expressionist Aesthetics**: Comic-book outlines, torn parchment UI, cel-shaded imagery, and dark atmospheric presentation.
- **AI Judging System**: Uses a local Ollama server running `gemma4:26b` to semantically evaluate player speeches.
- **Adaptive Punishment**: Losing levels results in permanent visual "K" burn marks, creating an oppressive atmosphere as the game progresses.
- **Educational Mechanics**: Teach expressionist concepts implicitly through gameplay and an unlockable Encyclopedia.

## Tech Stack

- **Frontend**: ClojureScript, Reagent (React Wrapper), Re-frame (State Management).
- **Build Tool**: shadow-cljs.
- **Backend/AI**: Local Ollama Server (`gemma4:26b`).

## How to Play / Development

1. Ensure you have `npm`, `node`, and `java` installed.
2. Install Javascript dependencies:
   ```bash
   npm install
   ```
3. Start the shadow-cljs development server:
   ```bash
   npm run dev
   # or
   npx shadow-cljs watch app
   ```
4. Start your local Ollama instance with CORS enabled (if necessary) and the required model:
   ```bash
   OLLAMA_ORIGINS="*" ollama run gemma4:26b
   ```
5. Open your browser to the local dev server (usually `http://localhost:8080`).

## Documentation

For more detailed information, please see the `context/` folder:
- [Scope](context/scope.md)
- [Architecture (arc42)](context/architecture.md)
