import React from 'react';
import { Box } from '@mui/material';
import heroImage from '../../assets/images/hero.jpg';

const Hero: React.FC = () => {
  console.log('Hero');
  return (
    <Box
      id={'hero'}
      sx={{
        backgroundColor: 'background.default',
        backgroundImage: `url(${heroImage})`,
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        backgroundRepeat: 'no-repeat',
        alignItems: 'start',
        alignContent: 'center',
        height: {
          xs: '30vh', // Mobile
          sm: '50vh', // Tablet
          md: '80vh', // Desktop
          lg: '95vh', // Large Desktop
        },
      }}
    >
      <Box
        sx={{
          display: 'inline-block',
          backgroundColor: 'background.default',
          color: 'secondary.main',
          fontWeight: 'bold',
          fontSize: { xs: '1.8rem', md: '3.5rem' },
          textAlign: 'start',
          px: 1,
          py: 0.3,
          maxWidth: '50%',
          overflowWrap: 'break-word',
          ml: 1,
          lineHeight: 1,
        }}
      >
        IHR DIGITALER ASSISTENT
      </Box>
    </Box>
  );
};

export default Hero;
