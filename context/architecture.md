# Architecture (arc42)

## 1. Introduction and Goals

"CICERO: EXPRESSIONISM ON TRIAL" is an interactive, browser-based courtroom game relying heavily on natural language generation and AI-assisted evaluation.

**Goals**:
- Provide an engaging, visually distinct interactive experience.
- Seamlessly connect a browser-based UI to a locally hosted Large Language Model.
- Ensure the game state is robust, testable, and easily extendable.

## 2. Architecture Constraints

- **Local Execution**: The AI must run locally via Ollama to avoid API costs and provide a diegetic feel.
- **Functional Paradigm**: The frontend state management must use ClojureScript and `re-frame` to maintain a strict unidirectional data flow.
- **Browser Environment**: The client is a web application and is constrained by standard browser security (CORS) and capabilities.

## 3. System Scope and Context

The system consists of the Game Client (running in a standard web browser) and the Local AI Backend (Ollama).

- **Player**: Interacts with the game via browser UI, submitting text.
- **Game Client**: Manages game state, scenes, renders the UI, and communicates with the AI.
- **Local AI (Ollama)**: Receives prompts containing Cicero's text and Player's text, returning JSON evaluations.

## 4. Solution Strategy

- **ClojureScript + Reagent**: Used for creating the component tree (React wrapper).
- **Re-frame**: Used for the event-driven state machine. All game state (score, text, K-marks) lives in a central `app-db`.
- **Fetch API**: Standard JavaScript `fetch` is used within re-frame effect handlers to call the Ollama HTTP API asynchronously.

## 5. Building Block View

### Level 1: System Context
- **`cicero.core`**: Entry point. Mounts the React root and initializes the re-frame database.
- **`cicero.views`**: Reagent components (Main Menu, Trial Scene, Encyclopedia).
- **`cicero.events`**: Re-frame event handlers that update the state or trigger side effects.
- **`cicero.subs`**: Re-frame subscriptions providing reactive data to `views`.
- **`cicero.ai`**: Encapsulates the specific API calling logic and prompt construction for Ollama.

## 6. Runtime View

**Submitting a Speech (Game Loop)**
1. Player types speech and clicks "Submit".
2. `views.cljs` dispatches `[:submit-speech]`.
3. `events.cljs` sets `ai-evaluating?` to true and triggers the `:fetch-ai-evaluation` effect.
4. `ai.cljs` creates the prompt and sends a POST request to `http://localhost:11434/api/generate`.
5. Ollama evaluates the text and returns a JSON payload.
6. `ai.cljs` parses the JSON and dispatches `[:ai-evaluation-success]`.
7. `events.cljs` updates the `score`, manages `k-marks`, and clears the loading state.
8. `subs.cljs` reacts to the new state, updating the UI in `views.cljs`.

## 7. Deployment View

- **Frontend**: Compiled by `shadow-cljs` into static assets (`public/index.html`, `public/js/main.js`, `public/css/style.css`). Can be hosted on any static web server.
- **Backend**: Requires the user to have Ollama installed locally with the `gemma4:26b` model.

## 8. Cross-cutting Concepts

- **State Management**: Strictly managed via re-frame. Side effects are isolated from view components.
- **Styling**: Managed globally via CSS variables and scoped classes to enforce the "expressionist comic" aesthetic.
- **Asynchronous AI**: Handled natively using JS Promises inside re-frame effect handlers (`reg-fx`).

## 9. Architecture Decisions

- **Decision to use 2D/DOM instead of 3D**: Decided to leverage robust CSS capabilities (mix-blend-mode, overlapping elements) rather than React Three Fiber. This significantly reduced complexity while meeting the aesthetic constraints of a "comic book" style.
- **Ollama Integration**: Hardcoded to `localhost:11434` for the initial prototype. Production versions could make this an environment variable or a configurable setting in the Main Menu.

## 10. Quality Requirements

- **Responsiveness**: The UI must update immediately when switching scenes or typing.
- **Fault Tolerance**: If Ollama fails to respond or returns invalid JSON, the game should gracefully handle the error without crashing, informing the player via the UI.

## 11. Risks and Technical Debts

- **CORS Issues**: Browsers may block the local `fetch` request if Ollama is not configured to accept origins (`OLLAMA_ORIGINS="*"`).
- **LLM Latency**: Depending on the user's hardware, evaluating a speech using `gemma4:26b` may take several seconds. The UI must clearly reflect the loading state to prevent confusion.
- **JSON Parsing**: The LLM might occasionally hallucinate or output non-compliant JSON, requiring robust parsing or retry logic in `ai.cljs`.
