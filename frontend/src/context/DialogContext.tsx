import { createContext, useContext, useState } from 'react';
import React from 'react';

export const DialogContext = createContext<{
  isOpen: boolean;
  onClose: () => void;
  setOpen: (open: boolean) => void;
}>({
  isOpen: false,
  onClose: () => {},
  setOpen: () => {},
});

export const DialogContextProvider: React.FC<{ children: React.ReactNode }> = ({
  children,
}) => {
  const [open, setOpen] = useState<boolean>(false);

  const onClose = () => {
    setOpen(false);
  };

  return (
    <DialogContext.Provider
      value={{
        isOpen: open,
        onClose: onClose,
        setOpen: setOpen,
      }}
    >
      {children}
    </DialogContext.Provider>
  );
};
export const useContactDialog = () => {
  const context = useContext(DialogContext);
  if (!context) {
    throw new Error('useDialogContent() must be used within a DialogContextProvider');
  }
  return context;
};
