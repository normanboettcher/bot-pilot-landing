import { alpha } from '@mui/material/styles';

export const navy = {
  50: '#EEF2F8',
  100: '#D5E1F0',
  200: '#B0C7E3',
  300: '#7AA3CF',
  400: '#4A7DBB',
  500: '#2563EB',
  600: '#1D4ED8',
  700: '#1E3A6E',
  800: '#162D57',
  900: '#0F1D35',
};

export const amber = {
  50: '#FFFBEB',
  100: '#FEF3C7',
  200: '#FDE68A',
  300: '#FCD34D',
  400: '#FBBF24',
  500: '#F59E0B',
  600: '#D97706',
  700: '#B45309',
  800: '#92400E',
  900: '#78350F',
};

export const neutral = {
  50: '#FAFAFA',
  100: '#F5F5F5',
  200: '#E5E7EB',
  300: '#D1D5DB',
  400: '#9CA3AF',
  500: '#6B7280',
  600: '#4B5563',
  700: '#374151',
  800: '#1F2937',
  900: '#111827',
};

export const green = {
  50: 'hsl(120, 80%, 98%)',
  100: 'hsl(120, 75%, 94%)',
  200: 'hsl(120, 75%, 87%)',
  300: 'hsl(120, 61%, 77%)',
  400: 'hsl(120, 44%, 53%)',
  500: 'hsl(120, 59%, 30%)',
  600: 'hsl(120, 70%, 25%)',
  700: 'hsl(120, 75%, 16%)',
  800: 'hsl(120, 84%, 10%)',
  900: 'hsl(120, 87%, 6%)',
};

export const orange = {
  50: 'hsl(45, 100%, 97%)',
  100: 'hsl(45, 92%, 90%)',
  200: 'hsl(45, 94%, 80%)',
  300: 'hsl(45, 90%, 65%)',
  400: 'hsl(45, 90%, 40%)',
  500: 'hsl(45, 90%, 35%)',
  600: 'hsl(45, 91%, 25%)',
  700: 'hsl(45, 94%, 20%)',
  800: 'hsl(45, 95%, 16%)',
  900: 'hsl(45, 93%, 12%)',
};

export const red = {
  50: 'hsl(0, 100%, 97%)',
  100: 'hsl(0, 92%, 90%)',
  200: 'hsl(0, 94%, 80%)',
  300: 'hsl(0, 90%, 65%)',
  400: 'hsl(0, 90%, 40%)',
  500: 'hsl(0, 90%, 30%)',
  600: 'hsl(0, 91%, 25%)',
  700: 'hsl(0, 94%, 18%)',
  800: 'hsl(0, 95%, 12%)',
  900: 'hsl(0, 93%, 6%)',
};

export const darkColorSchemes = {
  dark: {
    palette: {
      primary: {
        main: navy[300],
        light: navy[200],
        dark: navy[400],
        contrastText: navy[900],
      },
      secondary: {
        main: amber[500],
        light: amber[400],
        dark: amber[600],
        contrastText: navy[900],
      },
      info: {
        main: navy[400],
        light: navy[300],
        dark: navy[600],
        contrastText: '#FFFFFF',
      },
      warning: {
        light: orange[400],
        main: orange[500],
        dark: orange[700],
      },
      error: {
        light: red[400],
        main: red[500],
        dark: red[700],
      },
      success: {
        light: green[400],
        main: green[500],
        dark: green[700],
      },
      background: {
        default: navy[900],
        paper: navy[800],
      },
      text: {
        primary: navy[50],
        secondary: navy[200],
        heading: '#FFFFFF',
        subheading: navy[100],
      },
      divider: navy[800],
      action: {
        hover: alpha(navy[200], 0.08),
        selected: alpha(navy[200], 0.16),
      },
    },
  },
};

export const lightColorSchemes = {
  light: {
    palette: {
      primary: {
        main: navy[700],
        light: navy[500],
        dark: navy[800],
        contrastText: '#FFFFFF',
      },
      secondary: {
        main: amber[500],
        light: amber[400],
        dark: amber[600],
        contrastText: navy[900],
      },
      info: {
        main: navy[500],
        light: navy[300],
        dark: navy[600],
        contrastText: '#FFFFFF',
      },
      warning: {
        light: orange[300],
        main: orange[400],
        dark: orange[800],
      },
      error: {
        light: red[300],
        main: red[400],
        dark: red[800],
      },
      success: {
        light: green[300],
        main: green[400],
        dark: green[800],
      },
      background: {
        default: '#F8F9FC',
        paper: '#FFFFFF',
      },
      text: {
        primary: navy[900],
        secondary: neutral[700],
        heading: navy[900],
        subheading: neutral[600],
      },
      divider: navy[100],
      action: {
        hover: alpha(navy[700], 0.06),
        selected: alpha(navy[700], 0.12),
      },
    },
  },
};
