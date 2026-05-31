import React, { useState } from 'react';
import {
  AppBar,
  Box,
  Button,
  Divider,
  Drawer,
  IconButton,
  List,
  ListItem,
  ListItemButton,
  ListItemText,
  Stack,
  Toolbar,
  Typography,
} from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import SmartToyRoundedIcon from '@mui/icons-material/SmartToyRounded';
import { useContactDialog } from '../../context/DialogContext.tsx';
import ContactFormDialog from '../contact/ContactFormDialog.tsx';

interface NavItem {
  title: string;
  key: string;
}

const navItems: NavItem[] = [
  { title: 'Home', key: 'root' },
  { key: 'how-it-works', title: 'Wie es funktioniert' },
  { key: 'features-v2', title: 'Features' },
  { key: 'highlights-v2', title: 'Highlights' },
];

const drawerWidth = 240;

const AppAppBar: React.FC = () => {
  const [mobileOpen, setMobileOpen] = useState(false);
  const { isOpen, onClose, setOpen } = useContactDialog();

  const onNavClick = (key: string) => {
    if (key === 'kontakt') {
      setOpen(true);
      return;
    }
    setMobileOpen(false);
    document.getElementById(key)?.scrollIntoView({ behavior: 'smooth' });
  };

  const drawer = (
    <Box sx={{ textAlign: 'center' }}>
      <Stack direction="row" alignItems="center" justifyContent="center" spacing={1} sx={{ py: 2 }}>
        <SmartToyRoundedIcon sx={{ color: 'secondary.main' }} />
        <Typography variant="h6" sx={{ fontWeight: 700, color: 'text.primary' }}>
          BotPilot
        </Typography>
      </Stack>
      <Divider />
      <List>
        {navItems.map(({ key, title }) => (
          <ListItem key={key} disablePadding>
            <ListItemButton sx={{ textAlign: 'center' }} onClick={() => onNavClick(key)}>
              <ListItemText primary={title} />
            </ListItemButton>
          </ListItem>
        ))}
        <ListItem disablePadding>
          <ListItemButton
            sx={{ textAlign: 'center', color: 'primary.main', fontWeight: 700 }}
            onClick={() => setOpen(true)}
          >
            <ListItemText primary="Kostenlos anfragen" />
          </ListItemButton>
        </ListItem>
      </List>
    </Box>
  );

  return (
    <Box sx={{ display: 'flex', paddingBottom: { xs: 7, sm: 8 } }}>
      <AppBar component="nav" color="primary">
        <Toolbar>
          <IconButton
            color="inherit"
            aria-label="Menü öffnen"
            edge="start"
            onClick={() => setMobileOpen((prev) => !prev)}
            sx={{ mr: 1, display: { sm: 'none' } }}
          >
            <MenuIcon />
          </IconButton>

          <Stack
            direction="row"
            alignItems="center"
            spacing={1}
            sx={{ flexGrow: 1, cursor: 'pointer' }}
            onClick={() => onNavClick('root')}
          >
            <SmartToyRoundedIcon sx={{ color: 'secondary.main' }} />
            <Typography
              variant="h6"
              component="div"
              sx={{ fontWeight: 700, color: '#FFFFFF' }}
            >
              BotPilot
            </Typography>
          </Stack>

          <Box sx={{ display: { xs: 'none', sm: 'flex' }, alignItems: 'center', gap: 0.5 }}>
            {navItems.map(({ key, title }) => (
              <Button
                key={key}
                sx={{ color: 'rgba(255,255,255,0.85)', '&:hover': { color: '#FFFFFF' } }}
                onClick={() => onNavClick(key)}
              >
                {title}
              </Button>
            ))}
            <Button
              variant="contained"
              color="secondary"
              onClick={() => setOpen(true)}
              sx={{ ml: 1.5, fontWeight: 700 }}
            >
              Kostenlos anfragen
            </Button>
          </Box>
        </Toolbar>
      </AppBar>

      <nav>
        <Drawer
          variant="temporary"
          open={mobileOpen}
          onClose={() => setMobileOpen(false)}
          ModalProps={{ keepMounted: true }}
          sx={{
            display: { xs: 'block', sm: 'none' },
            '& .MuiDrawer-paper': { boxSizing: 'border-box', width: drawerWidth },
          }}
        >
          {drawer}
        </Drawer>
      </nav>

      <ContactFormDialog open={isOpen} onClose={onClose} />
    </Box>
  );
};

export default AppAppBar;
