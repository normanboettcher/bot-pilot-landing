import ParagraphContainer from './components/containers/ParagraphContainer.tsx';
import React from 'react';
import AppTheme from './theme/AppTheme.tsx';
import { Box, CssBaseline } from '@mui/material';
import Hero from './components/hero/Hero.tsx';

function App() {
  return (
    <>
      <AppTheme>
        <CssBaseline />
        <Box
          sx={{
            padding: {
              sx: '5px',
              sm: '5px',
              md: '10px',
              lg: '10px',
            },
          }}
        >
          <Hero />
          <ParagraphContainer />
        </Box>
      </AppTheme>
    </>
  );
}

export default App;
