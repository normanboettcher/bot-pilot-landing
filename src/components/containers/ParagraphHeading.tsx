import React from 'react';
import { Typography } from '@mui/material';

interface Props {
  type: 'heading' | 'subheading';
  content?: string | React.ReactNode;
}

const ParagraphHeading: React.FC<Props> = ({ type, content }) => {
  const isHeading = type === 'heading';
  return (
    <Typography
      variant={isHeading ? 'h4' : 'subtitle1'}
      component={isHeading ? 'h2' : 'p'}
      color={isHeading ? 'text.heading' : 'text.subheading'}
      gutterBottom
      sx={{
        fontWeight: 'bold',
      }}
    >
      {content}
    </Typography>
  );
};
export default ParagraphHeading;
