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
  Toolbar,
  Typography,
} from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import { useContactDialog } from '../../context/DialogContext.tsx';
import ContactFormDialog from '../contact/ContactFormDialog.tsx';

interface NavItem {
  title: string;
  key: string;
}
const navItems: NavItem[] = [
  {
    title: 'Home',
    key: 'root',
  },
  {
    key: 'features-v2',
    title: 'Features',
  },
  {
    key: 'highlights-v2',
    title: 'Highlights',
  },
  {
    key: 'kontakt',
    title: 'Kontakt',
  },
];
const drawerWidth = 150;
const AppAppBar: React.FC = () => {
  const [mobileOpen, setMobileOpen] = useState(false);
  const { isOpen, onClose, setOpen, onSubmit } = useContactDialog();

  const onAppBarClick = (key: string) => {
    if (key === 'kontakt') {
      setOpen(true);
    }
    document.getElementById(key)?.scrollIntoView({ behavior: 'smooth' });
  };

  const handleDrawerOpen = () => {
    setMobileOpen((prevState) => !prevState);
  };

  const drawer = (
    <Box onClick={handleDrawerOpen} sx={{ textAlign: 'center' }}>
      <Typography variant={'h6'} sx={{ my: 2 }}>
        SNB-Technologies
      </Typography>
      <Divider
        sx={{
          backgroundColor: 'divider',
        }}
      />
      <List>
        {navItems.map(({ key, title }, index) => (
          <ListItem key={index} disablePadding>
            <ListItemButton
              sx={{ textAlign: 'center' }}
              onClick={() => onAppBarClick(key)}
            >
              <ListItemText primary={title} />
            </ListItemButton>
          </ListItem>
        ))}
      </List>
    </Box>
  );

  return (
    <Box sx={{ display: 'flex', paddingBottom: 4 }}>
      <AppBar component={'nav'}>
        <Toolbar>
          <IconButton
            color={'inherit'}
            aria-label="menu"
            edge={'start'}
            onClick={handleDrawerOpen}
            sx={{ mr: 2, display: { sm: 'none' } }}
          >
            <MenuIcon />
          </IconButton>
          <Typography
            variant={'h6'}
            component={'div'}
            color={'text.primary'}
            sx={{
              flexGrow: 1,
              fontWeight: 'bold',
              display: {
                xs: 'none',
                sm: 'block',
              },
            }}
          >
            SNB-Technologies
          </Typography>
          <Box sx={{ display: { xs: 'none', sm: 'block' } }}>
            {navItems.map(({ key, title }, index) => (
              <Button
                key={index}
                sx={{ color: 'text.primary' }}
                onClick={() => onAppBarClick(key)}
              >
                {title}
              </Button>
            ))}
          </Box>
        </Toolbar>
      </AppBar>
      <nav>
        <Drawer
          variant={'temporary'}
          open={mobileOpen}
          onClose={handleDrawerOpen}
          ModalProps={{ keepMounted: true }}
          sx={{
            display: {
              xs: 'block',
              sm: 'none',
            },
            '& .MuiDrawer-paper': { boxSizing: 'border-box', width: drawerWidth },
          }}
        >
          {drawer}
        </Drawer>
      </nav>
      <ContactFormDialog onSubmit={onSubmit} open={isOpen} onClose={onClose} />
    </Box>
  );
};

export default AppAppBar;
