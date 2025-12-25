import React from 'react';
import ParagraphContainer from '../containers/ParagraphContainer.tsx';
import AdvantagesHeading from './AdvantagesHeading.tsx';
import { Box } from '@mui/material';
import AdvantagesCard from './AdvantagesCard.tsx';
import { getProductAdvantages } from './ProductAdvantages.tsx';
import type { Advantage } from './domain/Advantage.ts';
import useResponsive from '../../core/useResponsive.ts';
import AdvantagesCarousel from './AdvantagesCarousel.tsx';

const advantages: Advantage[] = getProductAdvantages();

const card = (
  <Box display={'flex'} alignItems={'end'}>
    {advantages.map((advantage: Advantage) => (
      <AdvantagesCard
        title={advantage.title}
        description={advantage.description}
        icon={advantage.icon}
      />
    ))}
  </Box>
);

const Advantages = () => {
  const { isMobile } = useResponsive();
  return (
    <Box>
      <ParagraphContainer
        heading={<AdvantagesHeading />}
        content={isMobile ? <AdvantagesCarousel /> : card}
      />
    </Box>
  );
};

export default Advantages;
