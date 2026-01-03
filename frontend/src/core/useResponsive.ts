import { useMediaQuery } from '@mui/material';

export interface Responsive {
  isMobile: boolean;
}

const useResponsive = () => {
  const isMobile = useMediaQuery('(max-width: 600px)');
  return {
    isMobile,
  };
};
export default useResponsive;
