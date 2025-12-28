import { useState } from 'react';
import { features } from '../Features.tsx';
import { Box, Stack } from '@mui/material';
import React from 'react';
import ParagraphHeadingContainer from '../../containers/ParagraphHeadingContainer.tsx';
import ParagraphContainer from '../../containers/ParagraphContainer.tsx';
import FeatureButton from './FeatureButton.tsx';
import FeatureImageCard from './FeatureImageCard.tsx';
import useResponsive from '../../../core/useResponsive.ts';
import { MobileLayout } from './MobileLayout.tsx';

function heading() {
  return (
    <ParagraphHeadingContainer
      heading="BotPilot Features"
      subheading={'Was für wirklich tolle features'}
    ></ParagraphHeadingContainer>
  );
}

const FeaturesV2 = () => {
  const [selectedItemIndex, setSelectedItemIndex] = useState(0);
  const { isMobile } = useResponsive();
  const handleItemClick = (index: number) => {
    setSelectedItemIndex(index);
  };

  const selectedFeature = features[selectedItemIndex];

  function featuresV2() {
    return isMobile ? (
      <MobileLayout
        selectedItemIndex={selectedItemIndex}
        handleItemClick={handleItemClick}
        selectedFeature={selectedFeature}
      />
    ) : (
      <Stack direction="row" spacing={2} width={'100%'}>
        <FeatureImageCard imageUrl={selectedFeature.imageUrl} />
        <Stack direction={'column'} spacing={1} width={'100%'}>
          {features.map((feature, index) => (
            <FeatureButton
              selectedFeature={feature}
              index={index}
              handleItemClick={handleItemClick}
              selectedItemIndex={selectedItemIndex}
            />
          ))}
        </Stack>
      </Stack>
    );
  }
  return (
    <Box id={'features-v2'} sx={{ py: { xs: 8, sm: 12 } }}>
      <Box sx={{ width: { sm: '100%', md: '100%', lg: '70%' } }}>
        <ParagraphContainer heading={heading()} content={featuresV2()} />
      </Box>
    </Box>
  );
};

export default FeaturesV2;
