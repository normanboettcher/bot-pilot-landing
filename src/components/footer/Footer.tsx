import React from 'react';
import {
  Box,
  Button,
  Container,
  IconButton,
  InputLabel,
  Link,
  Stack,
  TextField,
  Typography,
} from '@mui/material';
import LinkedInIcon from '../icons/LinkedInIcon.tsx';

function Copyright() {
  return (
    <Typography variant="body2" sx={{ color: 'text.secondary', mt: 1 }}>
      {'Copyright © '}
      <Link color="text.secondary" href="https://mui.com/">
        SNB-Technologies
      </Link>
      &nbsp;
      {new Date().getFullYear()} Alle Rechte vorbehalten.
    </Typography>
  );
}

const Footer = () => {
  return (
    <Container
      sx={{
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        gap: { xs: 4, sm: 8 },
        py: { xs: 8, sm: 10 },
        textAlign: { sm: 'center', md: 'left' },
      }}
    >
      <Stack
        spacing={3}
        direction={{ xs: 'column', sm: 'row' }}
        sx={{
          width: '100%',
          justifyContent: 'space-between',
        }}
      >
        <Box
          sx={{
            display: 'flex',
            flexDirection: 'column',
            gap: 5,
            minWidth: { xs: '100%', sm: '60%' },
          }}
        >
          <Stack sx={[{ width: { xs: '100%', sm: '60%' } }]} alignItems={'start'}>
            <Typography variant="body2" gutterBottom sx={{ fontWeight: 600, mt: 2 }}>
              Jetzt zum Newsletter anmelden
            </Typography>
            <Typography variant="body2" sx={{ mb: 2 }}>
              Kein Update mehr verpassen!
            </Typography>
            <InputLabel
              htmlFor="email-newsletter"
              sx={{
                color: 'text.primary',
              }}
            >
              Email
            </InputLabel>
            <Stack direction="row" spacing={1} useFlexGap>
              <TextField
                id="email-newsletter"
                hiddenLabel
                size="small"
                variant="outlined"
                fullWidth
                aria-label="E-Mail Adresse:"
                placeholder="jane.doe@mail.de"
                slotProps={{
                  htmlInput: {
                    autoComplete: 'off',
                    'aria-label': 'Ihre E-Mail Adresse',
                  },
                }}
                sx={{ width: '250px' }}
              />
              <Button
                variant="contained"
                color="secondary"
                size="small"
                sx={{ flexShrink: 0 }}
              >
                Anmelden
              </Button>
            </Stack>
          </Stack>
        </Box>
        <Box
          sx={{
            display: { xs: 'none', sm: 'flex' },
            flexDirection: 'column',
            gap: 1,
          }}
        >
          <Typography variant="body2" sx={{ fontWeight: 'medium' }}>
            BotPilot
          </Typography>
          <Link color="text.secondary" variant="body2" href="#">
            Home
          </Link>
          <Link color="text.secondary" variant="body2" href="#features-v2">
            Features
          </Link>
          <Link color="text.secondary" variant="body2" href="#highlights-v2">
            Highlights
          </Link>
          <Link color="text.secondary" variant="body2" href="#">
            Jetzt Kontakt aufnehmen!
          </Link>
        </Box>
        <Box
          sx={{
            display: { xs: 'none', sm: 'flex' },
            flexDirection: 'column',
            gap: 1,
          }}
        >
          <Typography variant="body2" sx={{ fontWeight: 'medium' }}>
            SNB-Technologies
          </Typography>
          <Link color="text.secondary" variant="body2" href="#">
            Über uns
          </Link>
          <Link color="text.secondary" variant="body2" href="#">
            Kontakt: info@snb.de
          </Link>
        </Box>
        <Box
          sx={{
            display: { xs: 'none', sm: 'flex' },
            flexDirection: 'column',
            gap: 1,
          }}
        >
          <Typography variant="body2" sx={{ fontWeight: 'medium' }}>
            Rechtliches
          </Typography>
          <Link color="text.secondary" variant="body2" href="#">
            Impressung
          </Link>
          <Link color="text.secondary" variant="body2" href="#">
            Datenschutz
          </Link>
          <Link color="text.secondary" variant="body2" href="#">
            Nutzungsbedingungen
          </Link>
        </Box>
      </Stack>
      <Box
        sx={{
          display: 'flex',
          justifyContent: 'space-between',
          pt: { xs: 4, sm: 8 },
          width: '100%',
          borderTop: '1px solid',
          borderColor: 'divider',
        }}
      >
        <div>
          <Link color="text.secondary" variant="body2" href="#">
            Datenschutzerklärung
          </Link>
          <Typography sx={{ display: 'inline', mx: 0.5, opacity: 0.5 }}>
            &nbsp;•&nbsp;
          </Typography>
          <Link color="text.secondary" variant="body2" href="#">
            Nutzungsbedingungen
          </Link>
          <Typography
            variant={'body2'}
            sx={{ fontWeight: 'medium' }}
            color="text.secondary"
          >
            Kontakt: snb@info.de
          </Typography>
          <Copyright />
        </div>
        <Stack
          direction="row"
          spacing={1}
          useFlexGap
          sx={{ justifyContent: 'left', color: 'text.secondary' }}
        >
          <IconButton
            color="inherit"
            size="small"
            href="https://www.linkedin.com/in/norman-boettcher-software-engineering/"
            aria-label="LinkedIn"
            sx={{ alignSelf: 'center' }}
          >
            <LinkedInIcon />
          </IconButton>
        </Stack>
      </Box>
    </Container>
  );
};

export default Footer;
