from pydantic import BaseModel, EmailStr


class ContactRequest(BaseModel):
    first_name: str
    last_name: str
    email: EmailStr
    company: str
    message: str
