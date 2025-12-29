import React from 'react';
import { Box, Button } from '@mui/material';
import ContactFormDialog from '../contact/ContactFormDialog.tsx';
import { useContactDialog } from '../../context/DialogContext.tsx';

const ContactUsButton = () => {
  const { setOpen, isOpen, onClose, onSubmit } = useContactDialog();
  const onClick = () => {
    setOpen(true);
  };
  return (
    <Box>
      <Box display={'flex'} justifyContent={'center'} p={1} pt={2}>
        <Button variant={'contained'} color={'secondary'} onClick={onClick}>
          JETZT KONTAKT AUFNEHMEN!
        </Button>
      </Box>
      <ContactFormDialog open={isOpen} onClose={onClose} onSubmit={onSubmit} />
    </Box>
  );
};

export default ContactUsButton;
