import React, { useState } from 'react';
import {
  Box,
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  Grid,
  TextField,
} from '@mui/material';
import type {} from '@mui/lab/themeAugmentation';
import { LoadingButton } from '@mui/lab';
import ContactFormCaptcha from '../captcha/ContactFormCaptcha.tsx';

interface Props {
  onClose: () => void;
  open: boolean;
}

interface ContactRequest {
  firstName: string;
  lastName: string;
  company: string;
  email: string;
  message: string;
}

const ContactFormDialog: React.FC<Props> = ({ open, onClose }) => {
  const [request, setRequest] = useState<ContactRequest>({
    firstName: '',
    lastName: '',
    company: '',
    email: '',
    message: '',
  });
  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setRequest((prev) => ({
      ...prev,
      [name]: value,
    }));
  };
  const onSubmit = async (e: React.FormEvent) => {
    if (e) {
      e.preventDefault();
      const payload = JSON.stringify({
        ...request,
        captchaToken: captchaToken,
      });
      try {
        setLoading(true);
        const response = await fetch('/api/contact', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: payload,
        });
        if (response.ok) {
          setLoading(false);
        }
      } catch (e) {
        console.log(`Fehler: ${e}`);
      }
    }
  };
  const textFieldSlotPops = {
    inputLabel: {
      sx: {
        color: 'text.primary',
      },
    },
  };
  const [captchaToken, setCaptchaToken] = useState<string | null>(null);
  const [loading, setLoading] = useState<boolean>(false);
  return (
    <Box>
      <Dialog open={open} onClose={onClose}>
        <ContactFormCaptcha onVerify={setCaptchaToken} />
        <DialogTitle>{'Contact Form'}</DialogTitle>
        <DialogContent>
          <DialogContentText color={'text.primary'} py={2}>
            Wir freuen uns, dass Sie an einem individuellen Chatbot von SNB-Technologies
            interessiert sind. Füllen Sie einfach die nachfolgenden Felder aus und wir
            werden uns umgehend mit Ihnen in Verbindung setzen.
          </DialogContentText>
          <form onSubmit={onSubmit} id={'contact-form'}>
            <Grid container spacing={2}>
              <Grid size={{ xs: 12, sm: 6 }}>
                <TextField
                  slotProps={textFieldSlotPops}
                  autoFocus
                  required
                  id={'vorname-field'}
                  name={'firstName'}
                  label={'Vorname'}
                  type={'text'}
                  fullWidth
                  variant={'standard'}
                  onChange={onChange}
                />
              </Grid>
              <Grid size={{ xs: 12, sm: 6 }}>
                <TextField
                  slotProps={textFieldSlotPops}
                  autoFocus
                  required
                  id={'nachname-field'}
                  name={'lastName'}
                  label={'Nachname'}
                  type={'text'}
                  fullWidth
                  variant={'standard'}
                  onChange={onChange}
                />
              </Grid>
              <Grid size={{ xs: 12, sm: 6 }}>
                <TextField
                  slotProps={textFieldSlotPops}
                  autoFocus
                  required
                  id={'unternehmen-field'}
                  name={'company'}
                  label={'Unternehmen'}
                  type={'text'}
                  fullWidth
                  variant={'standard'}
                  onChange={onChange}
                />
              </Grid>
              <Grid size={{ xs: 12, sm: 6 }}>
                <TextField
                  slotProps={textFieldSlotPops}
                  autoFocus
                  required
                  id={'email-field'}
                  name={'email'}
                  label={'E-Mail'}
                  type={'email'}
                  fullWidth
                  variant={'standard'}
                  onChange={onChange}
                />
              </Grid>
              <Grid size={12}>
                <TextField
                  slotProps={textFieldSlotPops}
                  autoFocus
                  minRows={5}
                  multiline
                  required
                  id={'anmerkungen-field'}
                  label={'Anmerkungen'}
                  name={'message'}
                  type={'text'}
                  fullWidth
                  variant={'outlined'}
                  onChange={onChange}
                />
              </Grid>
            </Grid>
          </form>
        </DialogContent>
        <DialogActions>
          <Button
            onClick={onClose}
            sx={{
              color: 'text.primary',
              '&:hover': {
                backgroundColor: 'action.hover',
              },
            }}
          >
            Schliessen
          </Button>
          <LoadingButton
            loading={loading}
            onClick={onSubmit}
            loadingPosition={'start'}
            sx={{
              color: 'text.primary',
              '&:hover': {
                backgroundColor: 'action.hover',
              },
            }}
          >
            Absenden
          </LoadingButton>
        </DialogActions>
      </Dialog>
    </Box>
  );
};

export default ContactFormDialog;
