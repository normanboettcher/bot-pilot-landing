import { Stack, Box } from '@mui/material';
import ParagraphHeadingContainer from './ParagraphHeadingContainer.tsx';
import React from 'react';
import WelcomeParagraph from '../text/welcome/WelcomeParagraph.tsx';

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
