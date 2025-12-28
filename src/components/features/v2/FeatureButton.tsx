import React from 'react';
import { Box, Button, Typography } from '@mui/material';
import type { Feature } from '../domain/Feature.ts';

interface Props {
  selectedFeature: Feature;
  index: number;
  handleItemClick: (index: number) => void;
  selectedItemIndex: number;
}

const FeatureButton: React.FC<Props> = ({
  selectedFeature,
  index,
  handleItemClick,
  selectedItemIndex,
}) => {
  const { icon, title, content } = selectedFeature;
  return (
    <Box
      key={index}
      component={Button}
      onClick={() => handleItemClick(index)}
      sx={[
        (theme) => ({
          p: 2,
          height: '100%',
          width: '100%',
          '&:hover': {
            backgroundColor: (theme.vars || theme).palette.action.hover,
          },
        }),
        selectedItemIndex === index && {
          backgroundColor: 'action.selected',
        },
      ]}
    >
      <Box
        sx={[
          {
            width: '100%',
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'left',
            gap: 1,
            textAlign: 'left',
            textTransform: 'none',
            color: 'text.primary',
          },
          selectedItemIndex === index && {
            color: 'text.primary',
          },
        ]}
      >
        {icon}

        <Typography variant="h6">{title}</Typography>
        <Typography variant={'body2'}>{content}</Typography>
      </Box>
    </Box>
  );
};

export default FeatureButton;
