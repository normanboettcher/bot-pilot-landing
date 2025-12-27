import { useState } from 'react';
import { features } from '../Features.tsx';
import { Box } from '@mui/material';
import React from 'react';
import ParagraphContainer from '../../containers/ParagraphContainer.tsx';
import ParagraphHeadingContainer from '../../containers/ParagraphHeadingContainer.tsx';

function featuresV2() {
  return <Box></Box>;
}
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
  const handleItemClick = (index: number) => {
    setSelectedItemIndex(index);
  };

  const selectedFeature = features[selectedItemIndex];
  return (
    <Box id={'features-v2'} sx={{ py: { xs: 8, sm: 16 } }}>
      <Box sx={{ width: { sm: '100%', md: '60%' } }}>
        <ParagraphContainer
          heading={heading()}
          content={featuresV2()}
        ></ParagraphContainer>
      </Box>
    </Box>
  );
};

export default FeaturesV2;
