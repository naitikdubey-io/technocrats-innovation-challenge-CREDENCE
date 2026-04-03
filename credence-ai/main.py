from fastapi import FastAPI, Depends
from sqlalchemy.orm import Session
import json
import uuid
from fastapi.middleware.cors import CORSMiddleware
from app.database import get_db, MedicalSession, save_chat_log
from pydantic import BaseModel
from typing import Optional
import os
import json
from groq import Groq
from dotenv import load_dotenv

app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"], 
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

load_dotenv()


client = Groq()


def extract_entities_from_message(message: str) -> dict:
    """
    Uses Groq to read the user's message and extract medical symptoms as JSON.
    """
    extraction_prompt = """
    You are a clinical data extractor. Read the user's message and extract any medical symptoms, 
    durations, or triggers. 
    Return ONLY a valid JSON object. Do not include markdown formatting or extra text.
    Example: {"symptom": "headache", "duration": "3 days"}
    If no medical info is present, return an empty JSON object: {}
    """
    
    try:
        response = client.chat.completions.create(
            model="llama-3.3-70b-versatile", 
            messages=[
                {"role": "system", "content": extraction_prompt},
                {"role": "user", "content": message}
            ],
            response_format={"type": "json_object"} 
        )
        
        
        extracted_data = json.loads(response.choices[0].message.content)
        return extracted_data
        
    except Exception as e:
        print(f"Extraction error: {e}")
        return {} 


def generate_llm_response(system_prompt: str, user_text: str) -> str:
    """
    Uses Groq to generate the actual conversational reply or the final report.
    """
    try:
        response = client.chat.completions.create(
            model="llama-3.3-70b-versatile", 
            messages=[
                {"role": "system", "content": system_prompt},
                {"role": "user", "content": user_text}
            ]
        )
        return response.choices[0].message.content
        
    except Exception as e:
        print(f"Generation error: {e}")
        return "I apologize, but I am having trouble connecting to my system right now. Please try again."


SINGLE_USER_ID = "my_personal_chat"

class ChatRequest(BaseModel):
    user_message: str
    biometrics: Optional[dict] = None  


#  API ENDPOINTS


@app.post("/chat")
async def chat_endpoint(request: ChatRequest, db: Session = Depends(get_db)):
    """Phase 1: The empathetic listener with real-time biometric integration."""
    
    session = db.query(MedicalSession).filter(
        MedicalSession.session_id == SINGLE_USER_ID, 
        MedicalSession.status == "active"
    ).first()
    
    if not session:
        session = MedicalSession(session_id=SINGLE_USER_ID, extracted_data={}, status="active")
        db.add(session)
        db.commit()

    
    new_extracted_info = extract_entities_from_message(request.user_message) 
    

    if new_extracted_info or request.biometrics:
        current_data = session.extracted_data or {}
        
       
        if new_extracted_info:
            current_data = {**current_data, **new_extracted_info}
            
       
        if request.biometrics:
            if "baseline_biometrics" not in current_data:
                current_data["baseline_biometrics"] = request.biometrics
            
        session.extracted_data = dict(current_data) 
        db.commit()

    
    system_prompt = """
    You are a caring and empathetic health intake assistant. 
    Listen and comfort the user. Do not diagnose.And make the responses short and concise.
    """
    
    saved_vitals = session.extracted_data.get("baseline_biometrics")

    if saved_vitals:
        system_prompt += f"""
        CRITICAL CONTEXT: At the start of this session, the user's smartwatch reported this baseline data: {saved_vitals}. 
        Subtly use this to inform your empathy.And if  it is not suitable for normal person than also try to talk by taking them in cobsideration.
        """
        
    bot_reply = generate_llm_response(system_prompt, request.user_message)
    
    save_chat_log(db, SINGLE_USER_ID, "user", request.user_message)
    save_chat_log(db, SINGLE_USER_ID, "assistant", bot_reply)
    
    return {"reply": bot_reply}


@app.post("/generate-report")
async def generate_report(db: Session = Depends(get_db)):
    """Phase 2: Triggered by the 'Finish Chat' button to create the doctor's report."""
    
    session = db.query(MedicalSession).filter(
        MedicalSession.session_id == SINGLE_USER_ID,
        MedicalSession.status == "active"
    ).first()
    
    if not session or not session.extracted_data:
        return {"error": "No medical data was collected. Please chat first."}
 
    session.status = "completed"
    

    session.session_id = f"{SINGLE_USER_ID}_completed_{uuid.uuid4().hex[:6]}" 
    db.commit()

    
    report_prompt = """
    You are a clinical summarizer. Generate a concise, professional medical intake report using the provided JSON data.
    If the data includes 'biometrics' , create a dedicated 'Wearable Data' bulleted section for the doctor.
    """
    
    
    patient_data_string = json.dumps(session.extracted_data)
    final_doctor_report = generate_llm_response(report_prompt, patient_data_string)
    
    return {"report": final_doctor_report}