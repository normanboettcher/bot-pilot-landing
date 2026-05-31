import React from 'react';
import { Box, Container, Divider, Stack, Typography } from '@mui/material';
import AccessTimeRoundedIcon from '@mui/icons-material/AccessTimeRounded';
import VerifiedUserRoundedIcon from '@mui/icons-material/VerifiedUserRounded';
import RocketLaunchRoundedIcon from '@mui/icons-material/RocketLaunchRounded';
import TuneRoundedIcon from '@mui/icons-material/TuneRounded';

const items = [
  {
    icon: <AccessTimeRoundedIcon fontSize="small" />,
    label: '24/7 verfügbar',
    sub: 'Keine Wartezeiten',
  },
  {
    icon: <VerifiedUserRoundedIcon fontSize="small" />,
    label: '100 % DSGVO-konform',
    sub: 'Hosting in Deutschland',
  },
  {
    icon: <RocketLaunchRoundedIcon fontSize="small" />,
    label: '< 1 Woche Einrichtung',
    sub: 'Schnell & unkompliziert',
  },
  {
    icon: <TuneRoundedIcon fontSize="small" />,
    label: 'Individuell anpassbar',
    sub: 'Ihr Design, Ihre Sprache',
  },
];

const TrustBar: React.FC = () => {
  return (
    <Box
      sx={{
        bgcolor: 'primary.main',
        py: { xs: 3, md: 4 },
      }}
    >
      <Container maxWidth="lg">
        <Stack
          direction={{ xs: 'column', sm: 'row' }}
          divider={
            <Divider
              orientation="vertical"
              flexItem
              sx={{ borderColor: 'rgba(255,255,255,0.2)', display: { xs: 'none', sm: 'block' } }}
            />
          }
          justifyContent="space-around"
          alignItems="center"
          spacing={{ xs: 3, sm: 0 }}
        >
          {items.map(({ icon, label, sub }) => (
            <Stack key={label} direction="row" spacing={1.5} alignItems="center">
              <Box
                sx={{
                  color: 'secondary.main',
                  display: 'flex',
                  alignItems: 'center',
                }}
              >
                {icon}
              </Box>
              <Box>
                <Typography
                  variant="body2"
                  sx={{ color: '#FFFFFF', fontWeight: 700, lineHeight: 1.2 }}
                >
                  {label}
                </Typography>
                <Typography variant="caption" sx={{ color: 'rgba(255,255,255,0.65)' }}>
                  {sub}
                </Typography>
              </Box>
            </Stack>
          ))}
        </Stack>
      </Container>
    </Box>
  );
};

export default TrustBar;
