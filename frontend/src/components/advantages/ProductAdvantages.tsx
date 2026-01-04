import React from 'react';
import type { Advantage } from './domain/Advantage.ts';
import SentimentSatisfiedAltIcon from '@mui/icons-material/SentimentSatisfiedAlt';
import ChatIcon from '@mui/icons-material/Chat';
import InsertInvitationIcon from '@mui/icons-material/InsertInvitation';
import MoreTimeIcon from '@mui/icons-material/MoreTime';
import BrushIcon from '@mui/icons-material/Brush';
import EditNoteIcon from '@mui/icons-material/EditNote';
export const getProductAdvantages = (): Advantage[] => [
  {
    title: 'Erhöhung Ihrer Kundenzufriedenheit',
    description:
      'Stehen Sie Ihren Kunden jederzeit für Fragen und Anliegen zur Verfügung',
    icon: <SentimentSatisfiedAltIcon />,
  },
  {
    title: 'Beantwortung häufig gestellter Fragen',
    description:
      'Lassen Sie häufig gestellte Fragen schnell und automatisch beantworten - und das rund um die Uhr',
    icon: <ChatIcon />,
  },
  {
    title: 'Höhere Erreichbarkeit',
    description:
      'Optimieren Sie Ihr Zeitmanagemant und lassen Sie Ihren digitalen Assistenten Ihre Aufgaben erledigen. Entlasten Sie Ihre Angestellten und erhöhen Sie Ihre Produktivität',
    icon: <MoreTimeIcon />,
  },
  {
    title: 'Terminbuchung über Ihren Chatbot',
    description: 'Vergeben Sie unkompliziert und automatisch Termine',
    icon: <InsertInvitationIcon />,
  },
  {
    title: 'Individuelle Anpassung der Benutzeroberfläche',
    description:
      'Gestalten Sie Ihren Chatbot aktiv mit und verwalten Sie Änderungen am Aussehen selbst. Ob Firmenlogo, Farben oder Begrüßungstexte - Passen Sie Ihren Chatbot an Ihr Unternehmen an',
    icon: <BrushIcon />,
  },
  {
    title: 'Ihr Unternehmen - Ihr Wording',
    description:
      'Passen Sie die Antworten und Formulierungen Ihres Chatbots an den Stil und die Sprache Ihres Unternehmens und Zielgruppe an',
    icon: <EditNoteIcon />,
  },
];
