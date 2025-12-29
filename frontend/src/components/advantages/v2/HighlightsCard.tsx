import React from 'react';
import type { Advantage } from '../domain/Advantage.ts';
import { Box, Card, Stack, Typography } from '@mui/material';

interface Props {
  highlight: Advantage;
}

const HighlightsCard: React.FC<Props> = ({ highlight }) => {
  const { icon, description, title } = highlight;
  return (
    <Card>
      <Stack spacing={1} direction={'column'} p={2}>
        <Box>{icon}</Box>
        <Box>
          <Typography component={'h3'} variant={'h5'}>
            {title}
          </Typography>
        </Box>
        <Box>
          <Typography variant={'body1'}>{description}</Typography>
        </Box>
      </Stack>
    </Card>
  );
};

export default HighlightsCard;
