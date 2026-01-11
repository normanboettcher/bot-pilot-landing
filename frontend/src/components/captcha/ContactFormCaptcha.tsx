import Turnstile from '@marsidev/react-turnstile';
import React from 'react';

export function ContactFormCaptcha({
  onVerify,
}: {
  onVerify: (token: string) => void;
}) {
  return (
    <Turnstile
      siteKey={import.meta.env.VITE_TURNSTILE_SITE_KEY}
      onSuccess={onVerify}
      options={{
        theme: 'light',
        size: 'invisible',
      }}
    />
  );
}
