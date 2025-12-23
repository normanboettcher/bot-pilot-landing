import { Stack, Box } from '@mui/material';
import React from 'react';

interface Props {
  heading: React.ReactNode;
  content: React.ReactNode;
}

const ParagraphContainer: React.FC<Props> = ({ heading, content }) => {
  return (
    <>
      <Box
        display="flex"
        sx={{
          backgroundColor: 'background.default',
        }}
      >
        <Stack>
          {heading}
          {content}
        </Stack>
      </Box>
    </>
  );
};
export default ParagraphContainer;
