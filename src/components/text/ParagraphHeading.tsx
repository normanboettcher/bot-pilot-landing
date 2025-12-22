import React from 'react';
import { Typography } from '@mui/material';

interface Props {
  type: 'heading' | 'subheading';
  content?: string;
  children?: React.ReactNode;
}

const ParagraphHeading: React.FC<Props> = ({ type, content, children }) => {
  const variant = type === 'heading' ? 'h4' : 'subtitle1';
  return (
    <Typography
      variant={variant}
      sx={{
        fontWeight: 'bold',
      }}
    >
      {content ?? children}
    </Typography>
  );
};
export default ParagraphHeading;
