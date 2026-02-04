/// <reference types="vite/client" />

declare module '*.jpg' {
  const value: string;
  export default value;
}

declare module '*.png' {
  const value: string;
  export default value;
}

interface ImportMetaEnv {
  VITE_TURNSTILE_SITE_KEY: string;
}
