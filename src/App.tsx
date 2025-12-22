import ParagraphContainer from './components/containers/ParagraphContainer.tsx';
import React from 'react';
import AppTheme from './theme/AppTheme.tsx';
import { CssBaseline } from '@mui/material';

function App() {
  return (
    <>
      <AppTheme>
        <CssBaseline />
        <ParagraphContainer />
      </AppTheme>
    </>
  );
}

export default App;
