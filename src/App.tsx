import ParagraphContainer from '@/components/containers/ParagraphContainer.tsx';
import { ThemeProvider } from '@mui/material';
import theme from '@/components/Theme.ts';
import WelcomeTextHeading from '@/components/decoration/TextDecoration.tsx';

function App() {
  return (
    <>
      <ThemeProvider theme={theme}>
        <ParagraphContainer />
      </ThemeProvider>
    </>
  );
}

export default App;
