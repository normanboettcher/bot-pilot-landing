import React from 'react';
import ParagraphHeading from '../ParagraphHeading.tsx';
import TextDecoration from '../../decoration/TextDecoration.tsx';
import { Typography } from '@mui/material';

const WelcomeParagraphSubheading: React.FC = () => {
  return (
    <ParagraphHeading type={'subheading'}>
      <span>
        MEHR ZEIT FÜR <TextDecoration text={'DAS WESENTLICHE'} color={'#7593A2'} />
      </span>
    </ParagraphHeading>
  );
};

export default WelcomeParagraphSubheading;
