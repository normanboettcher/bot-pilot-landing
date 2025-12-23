import ParagraphContainer from './components/containers/ParagraphContainer.tsx';
import React from 'react';
import AppTheme from './theme/AppTheme.tsx';
import { Box, CssBaseline } from '@mui/material';
import Hero from './components/hero/Hero.tsx';
import AppAppBar from './components/appbar/AppAppBar.tsx';
import ContactUsButton from './components/buttons/ContactUs.tsx';

function App() {
  return (
    <>
      <AppTheme>
        <CssBaseline />
        <Box
          sx={{
            padding: {
              xs: '5px',
              sm: '5px',
              md: '10px',
              lg: '10px',
            },
          }}
        >
          <AppAppBar />
          <Hero />
          <ParagraphContainer />
          <ContactUsButton />
        </Box>
      </AppTheme>
    </>
  );
}

export default App;
