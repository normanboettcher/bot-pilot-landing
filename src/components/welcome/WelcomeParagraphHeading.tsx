import React from 'react';
import WelcomeParagraphSubheading from './WelcomeParagraphSubheading.tsx';
import ParagraphHeadingContainer from '../containers/ParagraphHeadingContainer.tsx';
import { headings } from '../../config/Headings.ts';

const WelcomeParagraphHeading: React.FC = () => {
  return (
    <ParagraphHeadingContainer
      heading={headings.welcomeParagraph.heading}
      subheading={<WelcomeParagraphSubheading />}
    />
  );
};

export default WelcomeParagraphHeading;
