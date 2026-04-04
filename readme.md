# 🧬 Credence

> **The Biological Proof Engine & Clinical Translator**

Credence translates a patient's subjective distress into an objective, irrefutable clinical dossier—backed by live smartwatch biometrics. We aim to eliminate the "Gender Pain Gap" by ensuring patients are heard and empowering doctors with the exact data they need for rapid, accurate diagnoses.




-----

## 🚨 The Core Problem: Lost in Translation

  * **The Mismatch:** Patients in severe pain communicate emotionally; rushed doctors require strict, objective data.
  * **The Bias:** This communication gap often triggers unconscious bias, leading doctors to misdiagnose severe physical distress (Endometriosis, PCOS) as "anxiety."
  * **The Result:** A **4.5-year average delay** for women to be accurately diagnosed with chronic pain conditions.

## 🎯 Target Ecosystem (Zero-Friction)

  * 👤 **The Patient (Native Android App):** Uses the app to navigate pain and self-advocate using hard, irrefutable data.
  * 🩺 **The Physician (No App Required):** Hurried doctors simply scan a QR code to view structured, ICD-10 aligned data on a secure, temporary web dashboard.

-----

## ⚙️ How It Works: The Core MVP Loop

1.  🫂 **Empathetic Baseline:** A low-friction daily check-in screen to establish normal health metrics.
2.  🤖 **AI Clinical Companion:** During a flare-up, an empathetic AI actively chats with the user to extract necessary clinical details.
3.  ⌚ **Live Biometrics:** The app seamlessly pulls smartwatch data (HRV, sleep via Health Connect) to act as invisible biological proof.
4.  🔒 **Ephemeral Synthesis (Privacy-First):** The AI translates the emotional chat and biometrics into an objective medical report. Once approved, **the raw chat is permanently scrubbed**.
5.  🗄️ **Secure Medical Ledger:** Reports are encrypted into a chronological Timeline.
6.  📲 **Zero-Trust QR Sharing:** Patients generate a self-destructing QR code and PIN. Doctors scan it to view a bias-free, read-only web dashboard. *(3 incorrect PIN attempts burn the token).*

-----

## 📱 App Structure & User Flow

  * **Phase 1: Entry:** Frictionless 1-click Firebase Auth routes to the Home Screen (daily baseline + watch sync status).
  * **Phase 2: Clinical Logging:** Active AI Chat (with interruption safeguards) ➔ "Processing" transition screen ➔ Dossier Preview (Patient reviews and approves the AI-generated data before saving).
  * **Phase 3: Storage & Handoff:** Encrypted Timeline UI cards ➔ Zero-Trust Share configuration (Select logs + set expiration timer) ➔ External read-only Doctor Web Dashboard.

-----

## 🛠️ Tech Stack Blueprint

  * **📱 Frontend (Android):** Kotlin, Jetpack Compose, MVVM + Coroutines.
      * *APIs/Libs:* Retrofit2 (SSE Streaming), Google Health Connect, Vico (graphs), ZXing (QR).
  * **⚙️ Backend Orchestrator:** Java 21+ & Spring Boot 3.x.
      * *Data/Cache:* PostgreSQL (Dossiers), Redis (Zero-disk chat cache & TTL Doctor tokens).
      * *Web Portal:* Thymeleaf + Tailwind CSS (Physician dashboard).
  * **🧠 AI Microservice:** Python, FastAPI & Uvicorn.
      * *Inference:* Groq (Real-time empathy) & Google Gemini (Clinical synthesis).
      * *Validation:* Pydantic & SQLAlchemy.
  * **☁️ Deployment & Auth:** Firebase Auth (Google Sign-In), Render/Railway (Microservices), Supabase/Aiven (Managed DBs).

-----

## 💳 Business Model (Impact-First)

Putting a paywall in front of a patient in pain is unethical and kills adoption.

  * **Launch/MVP:** 100% Free.
  * **Patients:** Core loop (Chat, Biometrics, QR sharing) is free forever. **Credence Plus ($9.99/mo)** unlocks advanced features like multi-year PDF dossier exports.
  * **Physicians:** Always Free to view dashboards.

-----

## 📸 App Preview

| Home Screen | Empathetic AI Chat | Patient Dossier Preview | QR Sharing |
| :---: | :---: | :---: | :---: |

|<img width="220" height="800" alt="image" src="https://github.com/user-attachments/assets/45fee251-8657-4727-8ce6-191ee95f42f4" />
| <img width="220" height="800" alt="image" src="https://github.com/user-attachments/assets/b7ecf759-485c-4f0d-8bd4-aead43aa306a" />
 | <img width="220" height="800" alt="image" src="https://github.com/user-attachments/assets/c59635a8-61c5-455d-a3cb-00ac7794cc31" />
 | <img width="220" height="800" alt="image" src="https://github.com/user-attachments/assets/6c6fa766-ce6c-4248-ab26-f81193a4ed7c" />
 |
