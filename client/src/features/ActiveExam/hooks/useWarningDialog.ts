import { useState, useCallback } from "react";

interface WarningDialogConfig {
  title: string;
  description: string;
}

function useWarningDialog() {
  const [isOpen, setIsOpen] = useState(false);
  const [config, setConfig] = useState<WarningDialogConfig | null>(null);

  const openDialog = useCallback((newConfig: WarningDialogConfig) => {
    setConfig(newConfig);
    setIsOpen(true);
  }, []);

  const closeDialog = useCallback(() => {
    setIsOpen(false);
  }, []);

  return { isOpen, config, openDialog, closeDialog };
}

export default useWarningDialog;