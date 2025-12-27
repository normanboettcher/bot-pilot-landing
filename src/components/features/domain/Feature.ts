import React from 'react';

export interface Feature {
  imageUrl: string;
  title: string;
  content: React.ReactNode | string;
}
