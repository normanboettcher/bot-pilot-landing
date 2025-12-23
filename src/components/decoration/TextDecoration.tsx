import React from 'react';
import { Box } from '@mui/material';
import { alpha, useTheme } from '@mui/material/styles';

type Props = {
  children: React.ReactNode;
  color?: string;
};

const TextDecoration: React.FC<Props> = ({ children, color }) => {
  const theme = useTheme();

  const highlightColor = color ?? alpha(theme.palette.secondary.main, 0.8);

  return (
    <Box
      component="span"
      sx={{
        display: 'inline',
        font: 'inherit',
        color: 'inherit',
        lineHeight: 'inherit',
        whiteSpace: 'nowrap',

        boxShadow: `inset 0 -0.55em 0 ${highlightColor}`,

        px: '0.10em',
      }}
    >
      {children}
    </Box>
  );
};

export default TextDecoration;
