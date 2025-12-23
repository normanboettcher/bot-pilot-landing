import React from 'react';
import TextDecoration from '../../decoration/TextDecoration.tsx';
import ParagraphHeading from '../../containers/ParagraphHeading.tsx';

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
