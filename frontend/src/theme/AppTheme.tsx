import React from 'react';
import { createTheme } from '@mui/material/styles';
import { responsiveFontSizes, ThemeProvider, useMediaQuery } from '@mui/material';
import { darkColorSchemes, lightColorSchemes } from './ThemePrimitives.ts';

interface AppThemeProps {
  children: React.ReactNode;
}

const fontFamily = [
  'Inter',
  'system-ui',
  '-apple-system',
  'BlinkMacSystemFont',
  '"Segoe UI"',
  'sans-serif',
].join(',');

export default function AppTheme(props: AppThemeProps) {
  const { children } = props;
  const isDark = useMediaQuery('(prefers-color-scheme: dark)');

  let theme = React.useMemo(() => {
    const base = isDark
      ? createTheme({ colorSchemes: darkColorSchemes })
      : createTheme({ colorSchemes: lightColorSchemes });

    return createTheme(base, {
      typography: {
        fontFamily,
        h1: { fontWeight: 800, lineHeight: 1.1 },
        h2: { fontWeight: 700, lineHeight: 1.2 },
        h3: { fontWeight: 700 },
        h4: { fontWeight: 700 },
        h5: { fontWeight: 600 },
        h6: { fontWeight: 600 },
        button: { textTransform: 'none' as const, fontWeight: 600 },
      },
      shape: { borderRadius: 10 },
      components: {
        MuiButton: {
          styleOverrides: {
            root: { borderRadius: 8 },
            sizeLarge: { paddingTop: 12, paddingBottom: 12, fontSize: '1rem' },
          },
        },
        MuiCard: {
          styleOverrides: {
            root: {
              borderRadius: 12,
              boxShadow: 'none',
            },
          },
        },
        MuiAppBar: {
          styleOverrides: {
            root: { boxShadow: '0 1px 3px rgba(15,29,53,0.15)' },
          },
        },
      },
    });
  }, [isDark]);

  theme = responsiveFontSizes(theme);

  return <ThemeProvider theme={theme}>{children}</ThemeProvider>;
}
