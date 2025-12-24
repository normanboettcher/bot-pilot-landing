import ThumbUpAltIcon from '@mui/icons-material/ThumbUpAlt';
import React from 'react';
import type { Advantage } from './domain/Advantage.ts';

export const getProductAdvantages = (): Advantage[] => [
  {
    title: 'Vorteil Numero uno',
    description: 'what a great feature',
    icon: <ThumbUpAltIcon />,
  },
  {
    title: 'Vorteil Numero dos',
    description: 'what a great feature',
    icon: <ThumbUpAltIcon />,
  },
  {
    title: 'Vorteil Numero tres',
    description: 'what a great feature',
    icon: <ThumbUpAltIcon />,
  },
];
