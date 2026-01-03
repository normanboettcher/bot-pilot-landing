from fastapi import FastAPI, HTTPException
from fastapi.middleware.cors import CORSMiddleware
from mail_service_contact_form.models import ContactRequest
from mail_service_contact_form.email_service import send_contact_mail

app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:5173"],
    allow_methods=["POST"],
    allow_headers=["*"],
)


@app.post("/contact")
async def contact(data: ContactRequest):
    try:
        await send_contact_mail(
            f'{data.first_name} {data.last_name}',
            data.email,
            data.message,
            data.company
        )
        return {"status": "ok"}
    except Exception as e:
        raise HTTPException(status_code=500,
                            detail=f"Mail konnte nicht gesendet werden: {e}")
