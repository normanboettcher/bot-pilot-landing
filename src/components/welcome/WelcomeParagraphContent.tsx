import React from 'react';
import { Stack, Typography, useMediaQuery } from '@mui/material';
import TextDecoration from '../decoration/TextDecoration.tsx';

const WelcomeParagraphContent: React.FC = () => {
  const isMobile = useMediaQuery('(max-width: 600px)');
  return (
    <Stack direction={isMobile ? 'column' : 'row'} spacing={isMobile ? 1 : 3}>
      <Typography variant={'body1'} textAlign={'justify'}>
        Ein digitaler Assistent, der Ihnen wirklich{' '}
        <TextDecoration>Arbeit abnimmt</TextDecoration> und das rund um die Uhr! Als
        Steuerberater stehen Sie täglich zwischen Anfragen, Fristen und Mandanten. Unser
        intelligenter Chatbot ist mehr als nur ein technisches Tool - er ist ein
        persönlicher Assistent für Ihre Kanzlei.
      </Typography>
      <Typography variant={'body1'} textAlign={'justify'}>
        Der Chatbot beantwortet wiederkehrende Fragen, filtert Anliegen vor, und gibt
        potenziellen Mandanten sofort das Gefühl, gut aufgehoben zu sein – selbst wenn
        Sie gerade keine Zeit haben. So gewinnen Sie{' '}
        <TextDecoration>Ruhe, Präsenz</TextDecoration> und das Vertrauen{' '}
        <TextDecoration>Ihrer Zielgruppe</TextDecoration>.{' '}
      </Typography>
    </Stack>
  );
};

export default WelcomeParagraphContent;
