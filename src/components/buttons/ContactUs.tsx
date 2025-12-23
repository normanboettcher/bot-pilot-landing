import React from 'react';
import { Box, Button } from '@mui/material';

const ContactUsButton = () => {
  return (
    <Box display={'flex'} justifyContent={'center'} p={1} pt={2}>
      <Button variant={'contained'} color={'secondary'}>
        JETZT KONTAKT AUFNEHMEN!
      </Button>
    </Box>
  );
};

export default ContactUsButton;
