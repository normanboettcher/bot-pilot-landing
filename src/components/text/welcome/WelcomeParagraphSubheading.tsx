import React from 'react';
import ParagraphHeading from '../ParagraphHeading.tsx';
import TextDecoration from '../../decoration/TextDecoration.tsx';

const WelcomeParagraphSubheading: React.FC = () => {
  return (
    <ParagraphHeading type={'subheading'}>
      <span>
        MEHR ZEIT FÜR <TextDecoration>DAS WESENTLICHE</TextDecoration>
      </span>
    </ParagraphHeading>
  );
};

export default WelcomeParagraphSubheading;
