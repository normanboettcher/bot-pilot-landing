import React from 'react';
import ParagraphContainer from '../containers/ParagraphContainer.tsx';
import AdvantagesHeading from './AdvantagesHeading.tsx';
import { Box } from '@mui/material';
import AdvantagesCard from './AdvantagesCard.tsx';
import ThumbUpAltIcon from '@mui/icons-material/ThumbUpAlt';
const card = (
  <Box display={'flex'} alignItems={'end'}>
    <AdvantagesCard
      title={'Vorteil Numero uno'}
      description={'what a great feature'}
      icon={<ThumbUpAltIcon />}
    />
  </Box>
);

const Advantages = () => {
  return (
    <Box>
      <ParagraphContainer heading={<AdvantagesHeading />} content={card} />
    </Box>
  );
};

export default Advantages;
