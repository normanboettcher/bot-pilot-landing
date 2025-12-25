import React from 'react';
import { getProductAdvantages } from './ProductAdvantages.tsx';
import AdvantagesCard from './AdvantagesCard.tsx';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Autoplay, Navigation, Pagination } from 'swiper/modules';
import { Box } from '@mui/material';

import 'swiper/css';
import 'swiper/css/navigation';
import 'swiper/css/pagination';

const AdvantagesCarousel = () => {
  const advantages = getProductAdvantages();
  return (
    <Box
      position={'relative'}
      sx={{
        '.swiper-button-next, .swiper-button-prev': {
          display: { xs: 'none', md: 'flex' },
          opacity: 0,
          color: 'secondary.main',
        },
        '&:hover .swiper-button-next, &:hover .swiper-button-prev': {
          opacity: 1,
        },

        '.swiper-pagination-bullet': {
          backgroundColor: 'secondary.main',
          opacity: 0.4,
        },
        '.swiper-pagination-bullet-active': {
          opacity: 1,
        },
      }}
    >
      <Swiper
        modules={[Autoplay, Navigation, Pagination]}
        autoplay={{ delay: 4000, disableOnInteraction: false }}
        navigation
        pagination={{ clickable: true }}
        loop
        style={{ width: '100%', maxWidth: 600 }}
      >
        {advantages.map((advantage) => (
          <SwiperSlide key={advantage.title}>
            <AdvantagesCard {...advantage} />
          </SwiperSlide>
        ))}
      </Swiper>
    </Box>
  );
};

export default AdvantagesCarousel;
