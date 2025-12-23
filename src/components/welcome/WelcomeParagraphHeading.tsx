import React from 'react';
import ParagraphHeadingContainer from '../../containers/ParagraphHeadingContainer.tsx';
import { headings } from '../../../config/Headings.ts';
import WelcomeParagraphSubheading from './WelcomeParagraphSubheading.tsx';

const WelcomeParagraphHeading: React.FC = () => {
  return (
    <ParagraphHeadingContainer
      heading={headings.welcomeParagraph.heading}
      subheading={<WelcomeParagraphSubheading />}
    />
  );
};

export default WelcomeParagraphHeading;
