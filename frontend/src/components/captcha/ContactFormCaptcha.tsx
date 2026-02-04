import { Turnstile } from '@marsidev/react-turnstile';
import React from 'react';

const ContactFormCaptcha: React.FC<{ onVerify: (token: string) => void }> = ({
  onVerify,
}) => {
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
};

export default ContactFormCaptcha;
