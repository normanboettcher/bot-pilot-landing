import ParagraphContainer from './components/containers/ParagraphContainer.tsx';
import React from 'react';
import AppTheme from './theme/AppTheme.tsx';
import { Box, CssBaseline, Divider } from '@mui/material';
import Hero from './components/hero/Hero.tsx';
import AppAppBar from './components/appbar/AppAppBar.tsx';
import ContactUsButton from './components/buttons/ContactUs.tsx';
import WelcomeParagraphHeading from './components/welcome/WelcomeParagraphHeading.tsx';
import WelcomeParagraphContent from './components/welcome/WelcomeParagraphContent.tsx';
import Footer from './components/footer/Footer.tsx';
import FeaturesV2 from './components/features/v2/FeaturesV2.tsx';
import HighlightsV2 from './components/advantages/v2/HighlightsV2.tsx';

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
          <ParagraphContainer
            heading={<WelcomeParagraphHeading />}
            content={<WelcomeParagraphContent />}
          />
          <Box pt={8}>
            <ContactUsButton />
          </Box>
          <FeaturesV2 />
          <Divider />
          <HighlightsV2 />
          <Divider />
          <Footer />
        </Box>
      </AppTheme>
    </>
  );
}

export default App;
