import React from 'react';
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

interface Props {
  onClose: () => void;
  onSubmit: () => void;
  open: boolean;
}

const ContactFormDialog: React.FC<Props> = ({ open, onSubmit, onClose }) => {
  const textFieldSlotPops = {
    inputLabel: {
      sx: {
        color: 'text.primary',
      },
    },
  };
  return (
    <Box>
      <Dialog open={open} onClose={onClose}>
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
                  name={'vorname'}
                  label={'Vorname'}
                  type={'text'}
                  fullWidth
                  variant={'standard'}
                />
              </Grid>
              <Grid size={{ xs: 12, sm: 6 }}>
                <TextField
                  slotProps={textFieldSlotPops}
                  autoFocus
                  required
                  id={'nachname-field'}
                  name={'nachname'}
                  label={'Nachname'}
                  type={'text'}
                  fullWidth
                  variant={'standard'}
                />
              </Grid>
              <Grid size={{ xs: 12, sm: 6 }}>
                <TextField
                  slotProps={textFieldSlotPops}
                  autoFocus
                  required
                  id={'unternehmen-field'}
                  name={'unternehmen'}
                  label={'Unternehmen'}
                  type={'text'}
                  fullWidth
                  variant={'standard'}
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
                  name={'anmerkungen'}
                  type={'text'}
                  fullWidth
                  variant={'outlined'}
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
          <Button
            onClick={onSubmit}
            sx={{
              color: 'text.primary',
              '&:hover': {
                backgroundColor: 'action.hover',
              },
            }}
          >
            Absenden
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
};

export default ContactFormDialog;
