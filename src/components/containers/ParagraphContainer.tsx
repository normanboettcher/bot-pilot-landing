import { Stack, Box } from '@mui/material';
import ParagraphHeadingContainer from './ParagraphHeadingContainer.tsx';
import WelcomeParagraph from '../text/WelcomeParagraph.tsx';
import React from 'react';

const ParagraphContainer: React.FC = () => {
  return (
    <>
      <Box
        display="flex"
        sx={{
          backgroundColor: 'background.default',
        }}
      >
        <Stack>
          <ParagraphHeadingContainer />
          <WelcomeParagraph />
        </Stack>
      </Box>
    </>
  );
};
export default ParagraphContainer;
