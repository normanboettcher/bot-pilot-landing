import React from 'react';
import { Box, Button, Container, Stack, Typography } from '@mui/material';
import heroImage from '../../assets/images/hero.jpg';
import { useContactDialog } from '../../context/DialogContext.tsx';
import KeyboardArrowDownRoundedIcon from '@mui/icons-material/KeyboardArrowDownRounded';

const Hero: React.FC = () => {
  const { setOpen } = useContactDialog();

  const scrollToFeatures = () => {
    document.getElementById('features-v2')?.scrollIntoView({ behavior: 'smooth' });
  };

  return (
    <Box
      id="hero"
      sx={{
        position: 'relative',
        backgroundImage: `url(${heroImage})`,
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        backgroundRepeat: 'no-repeat',
        height: {
          xs: '60vh',
          sm: '70vh',
          md: '85vh',
          lg: '95vh',
        },
        display: 'flex',
        alignItems: 'center',
      }}
    >
      {/* Dark gradient overlay */}
      <Box
        aria-hidden="true"
        sx={{
          position: 'absolute',
          inset: 0,
          background:
            'linear-gradient(135deg, rgba(15,29,53,0.88) 0%, rgba(30,58,110,0.72) 55%, rgba(30,58,110,0.20) 100%)',
        }}
      />

      <Container maxWidth="lg" sx={{ position: 'relative', zIndex: 1 }}>
        <Box sx={{ maxWidth: { xs: '100%', md: '62%', lg: '56%' } }}>
          <Typography
            variant="overline"
            component="p"
            sx={{
              color: 'secondary.main',
              fontWeight: 700,
              letterSpacing: 3,
              mb: 2,
              display: 'block',
            }}
          >
            Für Steuerberater · Rechtsanwälte · Ärzte
          </Typography>

          <Typography
            variant="h1"
            component="h1"
            sx={{
              color: '#FFFFFF',
              fontSize: { xs: '2.2rem', sm: '3rem', md: '3.75rem' },
              fontWeight: 800,
              lineHeight: 1.1,
              mb: 3,
            }}
          >
            Mehr Zeit für das,{' '}
            <Box component="span" sx={{ color: 'secondary.main' }}>
              was zählt.
            </Box>
          </Typography>

          <Typography
            variant="h6"
            component="p"
            sx={{
              color: 'rgba(255,255,255,0.85)',
              fontWeight: 400,
              lineHeight: 1.65,
              mb: 5,
              fontSize: { xs: '1rem', md: '1.15rem' },
            }}
          >
            BotPilot übernimmt wiederkehrende Anfragen, beantwortet Fragen Ihrer Mandanten
            und vergibt Termine – automatisch, rund um die Uhr.
          </Typography>

          <Stack direction={{ xs: 'column', sm: 'row' }} spacing={2}>
            <Button
              variant="contained"
              size="large"
              onClick={() => setOpen(true)}
              sx={{
                bgcolor: 'secondary.main',
                color: 'secondary.contrastText',
                fontWeight: 700,
                px: 4,
                '&:hover': { bgcolor: 'secondary.dark' },
              }}
            >
              Jetzt kostenlos anfragen
            </Button>
            <Button
              variant="outlined"
              size="large"
              onClick={scrollToFeatures}
              endIcon={<KeyboardArrowDownRoundedIcon />}
              sx={{
                borderColor: 'rgba(255,255,255,0.55)',
                color: '#FFFFFF',
                px: 4,
                '&:hover': {
                  borderColor: '#FFFFFF',
                  bgcolor: 'rgba(255,255,255,0.08)',
                },
              }}
            >
              Mehr erfahren
            </Button>
          </Stack>
        </Box>
      </Container>
    </Box>
  );
};

export default Hero;
