import React from 'react';
import { Box, Card, CardContent, Stack, Typography } from '@mui/material';
import type { Feature } from './domain/Feature.ts';

interface Props {
  feature: Feature;
  direction: 'row' | 'row-reverse' | 'column';
}

const FeaturesCard: React.FC<Props> = ({ feature, direction }) => {
  const { title, imageUrl, content } = feature;
  return (
    <Stack direction={direction} spacing={2}>
      <Box
        sx={{
          width: '100%',
          borderRadius: 3,
          boxShadow: 2,
          backgroundColor: 'background.paper',
        }}
      >
        <Stack
          direction={direction !== 'column' ? direction : 'column'}
          spacing={1}
          width={'100%'}
          p={1}
        >
          <Box
            component={'img'}
            src={imageUrl}
            alt={'title'}
            sx={{
              borderRadius: 2,
            }}
          />
          <Card sx={{ width: '100%' }}>
            <CardContent>
              <Typography variant="h6">{title}</Typography>
              <Typography variant="body1">{content}</Typography>
            </CardContent>
          </Card>
        </Stack>
      </Box>
    </Stack>
  );
};

export default FeaturesCard;
