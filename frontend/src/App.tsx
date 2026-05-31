import React from 'react';
import AppTheme from './theme/AppTheme.tsx';
import { Box, CssBaseline, Divider } from '@mui/material';
import Hero from './components/hero/Hero.tsx';
import AppAppBar from './components/appbar/AppAppBar.tsx';
import TrustBar from './components/trust/TrustBar.tsx';
import ParagraphContainer from './components/containers/ParagraphContainer.tsx';
import ContactUsButton from './components/buttons/ContactUs.tsx';
import WelcomeParagraphHeading from './components/welcome/WelcomeParagraphHeading.tsx';
import WelcomeParagraphContent from './components/welcome/WelcomeParagraphContent.tsx';
import HowItWorks from './components/howitworks/HowItWorks.tsx';
import FeaturesV2 from './components/features/v2/FeaturesV2.tsx';
import HighlightsV2 from './components/advantages/v2/HighlightsV2.tsx';
import Footer from './components/footer/Footer.tsx';
import { DialogContextProvider } from './context/DialogContext.tsx';

function App() {
  return (
    <AppTheme>
      <CssBaseline />
      <DialogContextProvider>
        <AppAppBar />
        <Hero />
        <TrustBar />
        <ParagraphContainer
          heading={<WelcomeParagraphHeading />}
          content={<WelcomeParagraphContent />}
        />
        <Box
          sx={{
            py: { xs: 4, sm: 6, md: 8 },
          }}
        >
          <ContactUsButton />
        </Box>
        <Divider />
        <HowItWorks />
        <Divider />
        <FeaturesV2 />
        <Divider />
        <HighlightsV2 />
        <Divider />
        <Footer />
      </DialogContextProvider>
    </AppTheme>
  );
}

export default App;
