import React from 'react';
import { Box, Card, CardContent, IconButton, Stack, Typography } from '@mui/material';

interface Props {
  title: string;
  description: string;
  icon: React.ReactNode;
}

const AdvantagesCard: React.FC<Props> = ({ title, description, icon }) => {
  return (
    <Box
      sx={{
        width: '100%',
        height: '100%',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
      }}
    >
      <Card
        sx={{
          width: '100%',
          maxWidth: 600,
          height: 250,
        }}
      >
        <CardContent>
          <Typography variant={'h5'} component={'div'}>
            {title}
          </Typography>
          <Stack direction={'row'} alignItems={'center'} width={'100%'}>
            <IconButton>{icon}</IconButton>
            <Typography variant={'body2'}>{description}</Typography>
          </Stack>
        </CardContent>
      </Card>
    </Box>
  );
};

export default AdvantagesCard;
