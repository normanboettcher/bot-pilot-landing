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
        <Stack spacing={2} pr={1} pl={1}>
          {heading}
          {content}
        </Stack>
      </Box>
    </>
  );
};
export default ParagraphContainer;
