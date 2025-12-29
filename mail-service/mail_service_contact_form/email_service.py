from email.message import EmailMessage
import aiosmtplib
import os

from pydantic import EmailStr


async def send_contact_mail(name: str, email: EmailStr, message: str, company: str):
    msg = EmailMessage()
    msg["From"] = os.environ["SMTP_USER"]
    msg["To"] = os.environ["CONTACT_RECEIVER"]
    msg["Subject"] = f"Neue Kontaktanfrage - {company}"

    msg.set_content(
        f"""
Neue Anfrage:

Name: {name}
E-Mail: {email}

Nachricht:
{message}
"""
    )

    await aiosmtplib.send(
        msg,
        hostname=os.environ["SMTP_HOST"],
        port=int(os.environ["SMTP_PORT"]),
        username=os.environ["SMTP_USER"],
        password=os.environ["SMTP_PASSWORD"],
        start_tls=True,
    )
