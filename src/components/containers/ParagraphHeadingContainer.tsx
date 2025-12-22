import React from 'react';
import { Stack, Box } from '@mui/material';
import ParagraphHeading from '../text/ParagraphHeading.tsx';
import { headings } from '../../config/Headings.ts';
import WelcomeParagraphSubheading from '../text/welcome/WelcomeParagraphSubheading.tsx';
const ParagraphHeadingContainer: React.FC = () => {
  return (
    <Box display="flex">
      <Box
        sx={{
          width: '15px',
          backgroundColor: '#7593A2',
          marginTop: '0.3rem',
        }}
      />
      <Stack direction={'column'} pl={1} spacing={0}>
        <ParagraphHeading
          type={'heading'}
          content={headings.welcomeParagraph.heading}
        />
        <WelcomeParagraphSubheading />
      </Stack>
    </Box>
  );
};

export default ParagraphHeadingContainer;
