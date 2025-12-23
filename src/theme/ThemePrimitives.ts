export const blue = {
  50: 'hsl(210, 100%, 95%)',
  100: 'hsl(210, 100%, 92%)',
  200: 'hsl(210, 100%, 80%)',
  300: 'hsl(210, 100%, 65%)',
  400: 'hsl(210, 98%, 48%)',
  500: 'hsl(210, 98%, 42%)',
  600: 'hsl(210, 98%, 55%)',
  700: 'hsl(210, 100%, 35%)',
  800: 'hsl(210, 100%, 16%)',
  900: 'hsl(210, 100%, 21%)',
};

export const neutral = {
  50: 'hsl(0, 0%, 97%)', // sehr helle Sections
  100: 'hsl(0, 0%, 92%)', // Page Background
  200: 'hsl(0, 0%, 88%)', // Cards
  300: 'hsl(0, 0%, 85%)', // <- #D9D9D9
  400: 'hsl(0, 0%, 80%)', // Borders
  500: 'hsl(0, 0%, 72%)', // Disabled
  600: 'hsl(0, 0%, 60%)', // Secondary Text
  700: 'hsl(0, 0%, 45%)', // Body Text
  800: 'hsl(0, 0%, 30%)', // Headings
  900: 'hsl(0, 0%, 15%)', // Strong Text
};

export const brand = {
  50: 'hsl(200, 30%, 95%)', // sehr dezente Highlights
  100: 'hsl(200, 25%, 88%)',
  200: 'hsl(200, 22%, 78%)',
  300: 'hsl(200, 20%, 68%)',
  400: 'hsl(200, 19%, 60%)',
  500: 'hsl(200, 19%, 55%)', // <- #7593A2
  600: 'hsl(200, 22%, 45%)', // Hover
  700: 'hsl(200, 25%, 35%)', // Active
  800: 'hsl(200, 30%, 25%)', // Strong emphasis
  900: 'hsl(200, 35%, 18%)', // Dark accents
};

export const gray = {
  50: 'hsl(220, 35%, 97%)',
  100: 'hsl(220, 30%, 94%)',
  200: 'hsl(220, 20%, 88%)',
  300: 'hsl(220, 20%, 80%)',
  400: 'hsl(220, 20%, 65%)',
  500: 'hsl(220, 20%, 42%)',
  600: 'hsl(220, 20%, 35%)',
  700: 'hsl(220, 20%, 25%)',
  800: 'hsl(220, 30%, 6%)',
  900: 'hsl(220, 35%, 3%)',
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
      background: {
        default: neutral[900],
        paper: neutral[800],
      },

      text: {
        primary: neutral[50],
        secondary: neutral[300],
      },

      primary: {
        main: brand[300],
        light: brand[200],
        dark: brand[500],
        contrastText: neutral[900],
      },
      secondary: {
        main: brand[400],
        light: brand[300],
        dark: brand[600],
        contrastText: neutral[900],
      },
      divider: neutral[700],
    },
    info: {
      contrastText: brand[300],
      light: brand[500],
      main: brand[700],
      dark: brand[900],
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
    grey: {
      ...gray,
    },
  },
};

export const lightColorSchemes = {
  light: {
    palette: {
      primary: {
        main: neutral[500],
        light: neutral[300],
        dark: neutral[700],
        contrastText: brand[50],
      },
      secondary: {
        main: brand[600],
        light: brand[400],
        dark: brand[800],
        contrastText: neutral[50],
      },
      info: {
        light: brand[100],
        main: brand[300],
        dark: brand[600],
        contrastText: gray[50],
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
      grey: {
        ...gray,
      },
      divider: brand[500],
      background: {
        default: neutral[300],
        paper: neutral[400],
      },
    },
    text: {
      main: brand[500],
      light: brand[300],
      dark: brand[700],
      secondary: brand[500],
    },
  },
};
