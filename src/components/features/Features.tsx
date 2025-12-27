import React from 'react';
import type { Feature } from './domain/Feature.ts';
import TextDecoration from '../decoration/TextDecoration.tsx';
import { Stack } from '@mui/material';
import useResponsive from '../../core/useResponsive.ts';
import FeaturesCard from './FeaturesCard.tsx';
import featureImage from '../../assets/images/feature1.png';
import { DevicesRounded } from '@mui/icons-material';

export const features: Feature[] = [
  {
    title: 'Gestalten Sie die Sprache Ihres Bots individuell nach Ihren Wünschen',
    imageUrl: featureImage,
    content: (
      <span>
        Was für ein <TextDecoration>tolles Feature</TextDecoration>
      </span>
    ),
    icon: <DevicesRounded />,
  },
  {
    title: 'Feature2',
    imageUrl: featureImage,
    content: (
      <span>
        Was für ein <TextDecoration>tolles Feature</TextDecoration>
      </span>
    ),
    icon: <DevicesRounded />,
  },
  {
    title: 'Feature3',
    imageUrl: featureImage,
    content: (
      <span>
        Was für ein <TextDecoration>tolles Feature</TextDecoration>
      </span>
    ),
    icon: <DevicesRounded />,
  },
];

const Features = () => {
  const { isMobile } = useResponsive();
  return (
    <Stack direction={'column'} p={1} spacing={1} id={'features'}>
      {features.map((feature, index) => (
        <FeaturesCard
          key={index}
          direction={isMobile ? 'column' : index % 2 === 0 ? 'row' : 'row-reverse'}
          feature={feature}
        />
      ))}
    </Stack>
  );
};

export default Features;
