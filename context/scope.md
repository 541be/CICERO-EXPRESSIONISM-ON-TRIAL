# Project Scope: CICERO - EXPRESSIONISM ON TRIAL

## Vision

To build a fully playable, polished, and stylized indie courtroom game that combines Roman rhetoric, literary analysis, expressionism, AI judging systems, dark humor, and comic-book aesthetics.

## Target Audience

Players who enjoy narrative-driven, stylistic games (e.g., *Ace Attorney*, *Disco Elysium*) and those interested in literature, rhetoric, and expressionist art.

## Core Mechanics & Features

### In-Scope
1. **Interactive Text Input**: Players write original text responses to Cicero's historical Latin/English passages.
2. **AI Judging System ("The Consilium")**: A local LLM (Ollama running `gemma4:26b`) evaluates the player's text based on specific expressionistic criteria (emotional intensity, imagery, fragmentation, rhetorical aggression).
3. **Progressive Difficulty**: Levels increase in rhetorical intensity, starting from calm logic and escalating to apocalyptic proto-expressionism.
4. **Visual Consequences**: A dark, oppressive visual progression. If the player loses, a "K" burn mark is permanently placed on the screen, persisting through the gameplay loop.
5. **Expressionism Encyclopedia**: An educational codex tracking historical context, rhetorical devices, and player speeches.
6. **2D Comic-Book Aesthetics**: A visually striking DOM-based UI utilizing heavy contrast, cel-shading concepts, speech bubbles, and parchment textures.

### Out of Scope
1. **Photorealistic Graphics**: The game specifically aims for an exaggerated, expressionistic comic-book style.
2. **Real-time 3D Rendering Engine**: We have consciously chosen a 2D DOM-based approach using CSS over React Three Fiber/Three.js to simplify UI implementation while maintaining the aesthetic goal.
3. **Cloud/Hosted AI**: The game relies on a local LLM API (Ollama) to preserve privacy and remove latency/cost barriers associated with external APIs, though it can easily be swapped.
4. **Simplistic Keyword Matching**: The scoring system must be semantic and context-aware, not a simple regex search.

## Deliverables

- Complete Single Page Application (SPA) built with ClojureScript and re-frame.
- UI Framework styling and CSS for expressionist design.
- Local AI integration module to communicate with Ollama.
- Save system architecture via re-frame DB.
- Comprehensive documentation.
