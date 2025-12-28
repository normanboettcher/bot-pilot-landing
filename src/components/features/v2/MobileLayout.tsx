import React from 'react';
import { features } from '../Features.tsx';
import type { Feature } from '../domain/Feature.ts';
import { Box, Card, Chip, Typography } from '@mui/material';

interface MobileLayoutProps {
  selectedItemIndex: number;
  handleItemClick: (index: number) => void;
  selectedFeature: Feature;
}
export function MobileLayout({
  selectedItemIndex,
  handleItemClick,
  selectedFeature,
}: MobileLayoutProps) {
  if (!features[selectedItemIndex]) {
    return null;
  }

  return (
    <Box
      sx={{
        display: { xs: 'flex', sm: 'none' },
        flexDirection: 'column',
        gap: 2,
      }}
    >
      <Box sx={{ display: 'flex', gap: 2, overflow: 'auto' }}>
        {features.map(({ title }, index) => (
          <Chip
            variant="outlined"
            size="medium"
            key={index}
            label={title}
            onClick={() => handleItemClick(index)}
          />
        ))}
      </Box>
      <Card>
        <Box
          component={'img'}
          src={features[selectedItemIndex].imageUrl}
          alt={'alt'}
          width="100%"
          p={1}
          sx={{
            borderRadius: 4,
          }}
        />
        <Box sx={{ px: 2, pb: 2 }}>
          <Typography gutterBottom sx={{ color: 'text.primary', fontWeight: 'medium' }}>
            {selectedFeature.title}
          </Typography>
          <Typography variant="body2" sx={{ color: 'text.primary', mb: 1.5 }}>
            {selectedFeature.content}
          </Typography>
        </Box>
      </Card>
    </Box>
  );
}
