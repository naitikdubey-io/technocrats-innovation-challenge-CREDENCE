import pydantic
from pydantic import BaseModel
from typing import List, Dict,Any,Optional

class ChatRequest(BaseModel):
    user_message : str
    biometrics:Optional[Dict[str,Any]] = None

class ChatResponse(BaseModel):
    reply:str
    session_id : str
