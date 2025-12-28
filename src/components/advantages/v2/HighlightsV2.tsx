import React from 'react';
import { Box, Grid } from '@mui/material';
import { getProductAdvantages } from '../ProductAdvantages.tsx';
import HighlightsCard from './HighlightsCard.tsx';
import ParagraphContainer from '../../containers/ParagraphContainer.tsx';
import AdvantagesHeading from '../AdvantagesHeading.tsx';

const HighlightsV2 = () => {
  const features = getProductAdvantages();
  function highlightV2() {
    return (
      <Box id={'highlights'}>
        <Grid
          container
          spacing={2}
          justifyContent={'center'}
          px={{
            md: 12,
            lg: 16,
          }}
        >
          {features.map((advantage, index) => (
            <Grid size={{ xs: 12, sm: 8, md: 4 }}>
              <HighlightsCard key={index} highlight={advantage} />
            </Grid>
          ))}
        </Grid>
      </Box>
    );
  }
  return (
    <Box sx={{ py: { xs: 8, sm: 12 } }} id={'highlights-v2'}>
      <ParagraphContainer heading={<AdvantagesHeading />} content={highlightV2()} />
    </Box>
  );
};

export default HighlightsV2;
