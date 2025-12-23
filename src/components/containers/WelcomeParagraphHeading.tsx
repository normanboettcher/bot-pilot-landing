import React from 'react';
import ParagraphHeadingContainer from './ParagraphHeadingContainer.tsx';
import { headings } from '../../config/Headings.ts';
import WelcomeParagraphSubheading from '../text/welcome/WelcomeParagraphSubheading.tsx';

const WelcomeParagraphHeading: React.FC = () => {
  return (
    <ParagraphHeadingContainer
      heading={headings.welcomeParagraph.heading}
      subheading={<WelcomeParagraphSubheading />}
    />
  );
};

export default WelcomeParagraphHeading;
