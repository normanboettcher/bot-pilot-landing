import React from 'react';
import { Typography } from '@mui/material';

interface Props {
  text: string;
  color?: string;
}

const TextDecoration: React.FC<Props> = ({ text, color }) => {
  return (
    <Typography
      sx={{
        fontSize: 'inherit',
        fontWeight: 'inherit',
        position: 'relative',
        display: 'inline-block',
        zIndex: 1,

        '&::after': {
          content: '""',
          position: 'absolute',
          left: 0,
          right: 0,

          height: '0.8em',

          bottom: '-0.01em',

          backgroundColor: color,
          zIndex: -1,
          borderRadius: '2px',
        },
      }}
    >
      {text}
    </Typography>
  );
};
export default TextDecoration;
