import React from 'react';
import { Typography } from '@mui/material';

interface Props {
  type: 'heading' | 'subheading';
  content?: string;
  children?: React.ReactNode;
}

const ParagraphHeading: React.FC<Props> = ({ type, content, children }) => {
  const isHeading = type === 'heading';
  return (
    <Typography
      variant={isHeading ? 'h4' : 'subtitle1'}
      component={isHeading ? 'h2' : 'p'}
      gutterBottom
      sx={{
        fontWeight: 'bold',
      }}
    >
      {content ?? children}
    </Typography>
  );
};
export default ParagraphHeading;
