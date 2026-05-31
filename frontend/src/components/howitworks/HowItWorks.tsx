import React from 'react';
import { Box, Container, Grid, Stack, Typography } from '@mui/material';
import SearchRoundedIcon from '@mui/icons-material/SearchRounded';
import BuildRoundedIcon from '@mui/icons-material/BuildRounded';
import CheckCircleRoundedIcon from '@mui/icons-material/CheckCircleRounded';

const steps = [
  {
    number: '01',
    icon: <SearchRoundedIcon sx={{ fontSize: 32 }} />,
    title: 'Analyse',
    subtitle: 'Wir lernen Ihre Kanzlei kennen',
    description:
      'In einem kostenlosen Erstgespräch analysieren wir Ihre häufigsten Anfragen, Prozesse und Ziele – damit der Chatbot von Anfang an zu Ihrem Unternehmen passt.',
  },
  {
    number: '02',
    icon: <BuildRoundedIcon sx={{ fontSize: 32 }} />,
    title: 'Konfiguration',
    subtitle: 'Ihr Chatbot wird eingerichtet',
    description:
      'Wir konfigurieren BotPilot vollständig auf Sie zugeschnitten: Inhalte, Design, Sprache, Kalenderintegration. Kein technisches Wissen Ihrerseits erforderlich.',
  },
  {
    number: '03',
    icon: <CheckCircleRoundedIcon sx={{ fontSize: 32 }} />,
    title: 'Live',
    subtitle: 'Ihr Assistent übernimmt',
    description:
      'BotPilot geht live auf Ihrer Website. Sofortige Antworten auf Anfragen, automatische Terminvergabe – rund um die Uhr, ohne Mehraufwand für Ihr Team.',
  },
];

const HowItWorks: React.FC = () => {
  return (
    <Box
      id="how-it-works"
      sx={{
        bgcolor: 'background.paper',
        py: { xs: 8, md: 12 },
      }}
    >
      <Container maxWidth="lg">
        <Box mb={{ xs: 6, md: 8 }} textAlign="center">
          <Typography
            variant="overline"
            sx={{ color: 'primary.main', fontWeight: 700, letterSpacing: 2 }}
          >
            So einfach geht&apos;s
          </Typography>
          <Typography variant="h3" component="h2" mt={1} color="text.heading">
            In 3 Schritten zum digitalen Assistenten
          </Typography>
          <Typography
            variant="body1"
            color="text.secondary"
            mt={2}
            sx={{ maxWidth: 560, mx: 'auto' }}
          >
            Von der ersten Idee bis zum live geschalteten Chatbot – schnell, unkompliziert
            und vollständig betreut.
          </Typography>
        </Box>

        <Grid container spacing={{ xs: 4, md: 6 }}>
          {steps.map(({ number, icon, title, subtitle, description }) => (
            <Grid size={{ xs: 12, md: 4 }} key={number}>
              <Stack spacing={2.5} alignItems={{ xs: 'flex-start', md: 'flex-start' }}>
                <Stack direction="row" alignItems="center" spacing={2}>
                  <Box
                    sx={{
                      display: 'flex',
                      alignItems: 'center',
                      justifyContent: 'center',
                      width: 56,
                      height: 56,
                      borderRadius: 2,
                      bgcolor: 'primary.main',
                      color: 'secondary.main',
                      flexShrink: 0,
                    }}
                  >
                    {icon}
                  </Box>
                  <Typography
                    variant="h2"
                    sx={{
                      fontSize: '3rem',
                      fontWeight: 800,
                      color: 'divider',
                      lineHeight: 1,
                      userSelect: 'none',
                    }}
                  >
                    {number}
                  </Typography>
                </Stack>

                <Box>
                  <Typography variant="h5" component="h3" color="text.heading" gutterBottom>
                    {title}
                  </Typography>
                  <Typography
                    variant="subtitle2"
                    sx={{ color: 'primary.light', fontWeight: 600, mb: 1 }}
                  >
                    {subtitle}
                  </Typography>
                  <Typography variant="body2" color="text.secondary" lineHeight={1.7}>
                    {description}
                  </Typography>
                </Box>
              </Stack>
            </Grid>
          ))}
        </Grid>
      </Container>
    </Box>
  );
};

export default HowItWorks;
