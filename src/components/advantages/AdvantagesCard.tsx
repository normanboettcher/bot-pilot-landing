import React from 'react';
import { Card, CardContent, IconButton, Stack, Typography } from '@mui/material';

interface Props {
  title: string;
  description: string;
  icon: React.ReactNode;
}

const AdvantagesCard: React.FC<Props> = ({ title, description, icon }) => {
  return (
    <Card>
      <CardContent>
        <Typography variant={'h5'} component={'div'}>
          {title}
        </Typography>
        <Stack direction={'row'} alignItems={'center'}>
          <IconButton>{icon}</IconButton>
          <Typography variant={'body2'}>{description}</Typography>
        </Stack>
      </CardContent>
    </Card>
  );
};

export default AdvantagesCard;
