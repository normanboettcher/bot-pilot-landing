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
          width: '100%',
          backgroundColor: 'background.default',
        }}
      >
        <Stack spacing={2} pr={1} pl={1} pt={2} width={'100%'}>
          {heading}
          <Box
            sx={{
              width: '100%',
              position: 'relative',
            }}
          >
            {content}
          </Box>
        </Stack>
      </Box>
    </>
  );
};
export default ParagraphContainer;
