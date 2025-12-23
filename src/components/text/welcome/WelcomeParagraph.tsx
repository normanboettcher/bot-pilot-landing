import React from 'react';
import { Stack, Typography } from '@mui/material';
import TextDecoration from '../../decoration/TextDecoration.tsx';

const WelcomeParagraph: React.FC = () => {
  return (
    <Stack direction={'row'} pt={1}>
      <Typography variant={'body1'}>
        <span>
          Ein digitaler Assistent, der Ihnen wirklich{' '}
          <TextDecoration>Arbeit abnimmt</TextDecoration> - rund um die Uhr! Als
          Steuerberater stehen Sie täglich zwischen Anfragen, Fristen und Mandanten.
          Unser intelligenter Chatbot ist mehr als nur ein technisches Tool – er ist ein
          persönlicher Assistent für Ihre Kanzlei.
        </span>
      </Typography>
      <Typography variant={'body1'}></Typography>
    </Stack>
  );
};

export default WelcomeParagraph;
