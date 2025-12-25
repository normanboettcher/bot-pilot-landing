import React from 'react';
import { Stack } from '@mui/material';
import HeadingDecoration from '../decoration/HeadingDecoration.tsx';
import ParagraphHeading from './ParagraphHeading.tsx';

interface Props {
  heading: string;
  subheading?: string | React.ReactNode;
}

const ParagraphHeadingContainer: React.FC<Props> = ({ heading, subheading }) => {
  return (
    <Stack direction={'row'} alignItems={'center'}>
      <HeadingDecoration />
      <Stack direction={'column'} pl={1} spacing={-1} justifyContent={'center'}>
        <ParagraphHeading type={'heading'} content={heading} />
        {subheading}
      </Stack>
    </Stack>
  );
};

export default ParagraphHeadingContainer;
