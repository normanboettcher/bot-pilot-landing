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

const navItems = ['Home', 'Vorteile', 'Produkt', 'Kontakt'];
const drawerWidth = 150;
const AppAppBar: React.FC = () => {
  const [mobileOpen, setMobileOpen] = useState(false);

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
        {navItems.map((item) => (
          <ListItem key={item} disablePadding>
            <ListItemButton sx={{ textAlign: 'center' }}>
              <ListItemText primary={item} />
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
            sx={{
              flexGrow: 1,
              display: {
                xs: 'none',
                sm: 'block',
              },
            }}
          >
            SNB-Technologies
          </Typography>
          <Box sx={{ display: { xs: 'none', sm: 'block' } }}>
            {navItems.map((item) => (
              <Button key={item} sx={{ color: '#fff' }}>
                {item}
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
    </Box>
  );
};

export default AppAppBar;
