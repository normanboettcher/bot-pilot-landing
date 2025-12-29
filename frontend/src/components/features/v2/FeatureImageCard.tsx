import { Box, Card } from '@mui/material';
import React from 'react';

interface Props {
  imageUrl: string;
}

const FeatureImageCard: React.FC<Props> = ({ imageUrl }) => {
  return (
    <Box
      sx={{
        display: { xs: 'none', sm: 'flex' },
        width: { xs: '100%', md: '80%' },
      }}
    >
      <Card
        sx={{
          height: '100%',
          display: { xs: 'none', sm: 'flex' },
          pointerEvents: 'none',
          padding: 1,
        }}
      >
        <Box
          component={'img'}
          src={imageUrl}
          alt={'alt'}
          sx={{
            borderRadius: 4,
          }}
        />
      </Card>
    </Box>
  );
};

export default FeatureImageCard;