from sqlalchemy import create_engine, Column, Integer, String, Text, JSON
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker, Session
import os
from dotenv import load_dotenv

load_dotenv()


SQLALCHEMY_DATABASE_URL = os.getenv("DATABASE_URL")
engine = create_engine(SQLALCHEMY_DATABASE_URL)
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)
Base = declarative_base()


class MedicalSession(Base):
    """Stores the extracted medical symptoms in the background"""
    __tablename__ = "medical_sessions"
    id = Column(Integer, primary_key=True, index=True)
    session_id = Column(String, unique=True, index=True)
    extracted_data = Column(JSON, default=dict)
    status = Column(String, default="active") 

class ChatLog(Base):
    """Stores the actual back-and-forth chat history"""
    __tablename__ = "chat_logs"
    id = Column(Integer, primary_key=True, index=True)
    session_id = Column(String, index=True)
    role = Column(String) 
    content = Column(Text)


Base.metadata.create_all(bind=engine)



def get_db():
    """Provides a database session for the FastAPI endpoints"""
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

def save_chat_log(db: Session, session_id: str, role: str, content: str):
    """Helper function to easily save messages"""
    chat_log = ChatLog(session_id=session_id, role=role, content=content)
    db.add(chat_log)
    db.commit()