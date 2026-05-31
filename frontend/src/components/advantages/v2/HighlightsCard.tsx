import React from 'react';
import type { Advantage } from '../domain/Advantage.ts';
import { Box, Card, Stack, Typography } from '@mui/material';

interface Props {
  highlight: Advantage;
}

const HighlightsCard: React.FC<Props> = ({ highlight }) => {
  const { icon, description, title } = highlight;
  return (
    <Card
      sx={{
        height: '100%',
        border: '1px solid',
        borderColor: 'divider',
        transition: 'box-shadow 0.2s ease, transform 0.2s ease',
        '&:hover': {
          boxShadow: '0 6px 24px rgba(30,58,110,0.10)',
          transform: 'translateY(-3px)',
        },
      }}
    >
      <Stack spacing={2} direction={'column'} p={3}>
        <Box
          sx={{
            display: 'inline-flex',
            alignItems: 'center',
            justifyContent: 'center',
            width: 44,
            height: 44,
            borderRadius: 1.5,
            bgcolor: 'primary.main',
            color: 'secondary.main',
          }}
        >
          {icon}
        </Box>
        <Typography
          component={'h3'}
          variant={'h6'}
          sx={{
            fontWeight: 700,
            wordWrap: 'break-word',
            overflowWrap: 'break-word',
            hyphens: 'auto',
            color: 'text.heading',
          }}
        >
          {title}
        </Typography>
        <Typography variant={'body2'} color="text.secondary" lineHeight={1.7}>
          {description}
        </Typography>
      </Stack>
    </Card>
  );
};

export default HighlightsCard;
