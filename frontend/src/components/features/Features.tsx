import React from 'react';
import type { Feature } from './domain/Feature.ts';
import TextDecoration from '../decoration/TextDecoration.tsx';
import { Stack } from '@mui/material';
import useResponsive from '../../core/useResponsive.ts';
import FeaturesCard from './FeaturesCard.tsx';
import featureImage from '../../assets/images/feature1.png';
import { EventAvailableRounded, QuestionAnswerRounded, TuneRounded } from '@mui/icons-material';

export const features: Feature[] = [
  {
    title: '24/7 FAQ-Beantwortung',
    imageUrl: featureImage,
    content: (
      <span>
        Ihr Chatbot kennt Ihre häufigsten Fragen und beantwortet sie sofort – ob zu
        Öffnungszeiten, Dokumentenanforderungen oder allgemeinen Abläufen. Kein Anruf
        bleibt <TextDecoration>unbeantwortet</TextDecoration>.
      </span>
    ),
    icon: <QuestionAnswerRounded />,
  },
  {
    title: 'Automatische Terminvergabe',
    imageUrl: featureImage,
    content: (
      <span>
        Mandanten buchen Termine direkt über den Chatbot – ohne Warteschleife, ohne
        E-Mail-Ping-Pong. BotPilot synchronisiert Ihren Kalender und bestätigt{' '}
        <TextDecoration>vollautomatisch</TextDecoration>.
      </span>
    ),
    icon: <EventAvailableRounded />,
  },
  {
    title: 'Maßgeschneidert für Sie',
    imageUrl: featureImage,
    content: (
      <span>
        Logo, Farben, Sprache, Inhalte – alles wird auf Ihre Kanzlei oder Praxis
        abgestimmt. Ob Steuerberatung, Anwaltskanzlei oder Arztpraxis:{' '}
        <TextDecoration>Ihr Chatbot, Ihre Identität</TextDecoration>.
      </span>
    ),
    icon: <TuneRounded />,
  },
];

const Features = () => {
  const { isMobile } = useResponsive();
  return (
    <Stack direction={'column'} p={1} spacing={1} id={'features'}>
      {features.map((feature, index) => (
        <FeaturesCard
          key={index}
          direction={isMobile ? 'column' : index % 2 === 0 ? 'row' : 'row-reverse'}
          feature={feature}
        />
      ))}
    </Stack>
  );
};

export default Features;
