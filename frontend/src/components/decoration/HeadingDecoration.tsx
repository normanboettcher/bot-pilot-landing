import React from 'react';
import { Box } from '@mui/material';

const HeadingDecoration = () => {
  return (
    <Box
      minHeight={'45px'}
      maxHeight={'50px'}
      sx={{
        width: '15px',
        backgroundColor: 'secondary.main',
        marginTop: '0.3rem',
      }}
    />
  );
};

export default HeadingDecoration;
