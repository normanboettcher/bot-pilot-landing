import React from 'react';
import { createTheme } from '@mui/material/styles';
import { responsiveFontSizes, ThemeProvider, useMediaQuery } from '@mui/material';
import { darkColorSchemes, lightColorSchemes } from './ThemePrimitives.ts';

interface AppThemeProps {
  children: React.ReactNode;
}

export default function AppTheme(props: AppThemeProps) {
  const { children } = props;
  const isDark = useMediaQuery('(prefers-color-scheme: dark)');
  let theme = React.useMemo(() => {
    return isDark
      ? createTheme({
          colorSchemes: darkColorSchemes,
        })
      : createTheme({
          colorSchemes: lightColorSchemes,
        });
  }, []);

  theme = responsiveFontSizes(theme);

  return <ThemeProvider theme={theme}>{children}</ThemeProvider>;
}
