import { useEffect, useCallback } from "react";
import { useWebSocket } from "@/hooks";
import { MessageType, ExamEventType } from "@/features/ExamWebSocket/types/message-types";
import useWarningDialog from "./useWarningDialog";
import { WarningDialog } from "../components";

let lastCopy: number | null = null;
let lastPaste: number | null = null;
let lastTabSwitch: number | null = null;

const useExamSecurity = () => {
  const client = useWebSocket();
  const { isOpen, config, openDialog, closeDialog } = useWarningDialog();

  const logToServer = useCallback(
    (subtype: ExamEventType, message?: string) => {
      client.send({
        type: MessageType.ExamEvent,
        subtype,
        payload: message,
      });
    },
    [client]
  );

  const debounce = (func: (...args: any[]) => void, delay: number) => {
    let timer: NodeJS.Timeout;
    return (...args: any[]) => {
      clearTimeout(timer);
      timer = setTimeout(() => func(...args), delay);
    };
  };

  // Preventing copy-paste and text selection
  useEffect(() => {
    const examContainer = document;

    const handleCopy = (e: ClipboardEvent) => {
      if (e.isTrusted) {
        e.preventDefault();
        // if (lastCopy && Date.now() - lastCopy < 5000) {
        //   logToServer(ExamEventType.COPY, "Copy action detected");
        //   lastCopy = Date.now();
        // }
      }
    };

    const handlePaste = (e: ClipboardEvent) => {
      if (e.isTrusted) {
        e.preventDefault();
        // if (lastPaste && Date.now() - lastPaste < 5000) {
        //   logToServer(ExamEventType.PASTE, "Paste action detected");
        //   lastPaste = Date.now();
        // }
      }
    };

    const handleSelection = (e: MouseEvent) => {
      e.preventDefault();
    };

    const timer = setTimeout(() => {
      examContainer?.addEventListener("copy", handleCopy);
      examContainer?.addEventListener("paste", handlePaste);
      examContainer?.addEventListener("cut", handleCopy);
      examContainer?.addEventListener("mousedown", handleSelection);
    }, 2000);

    return () => {
      clearTimeout(timer);
      examContainer?.removeEventListener("copy", handleCopy);
      examContainer?.removeEventListener("paste", handlePaste);
      examContainer?.removeEventListener("cut", handleCopy);
      examContainer?.removeEventListener("mousedown", handleSelection);
    };
  }, [logToServer, openDialog]);

  // Monitoring tab changes and browser focus
  useEffect(() => {
    const logTabSwitch = debounce(() => {
      logToServer(ExamEventType.TAB_SWITCH);
      openDialog({
        title: "Tab Switch Detected",
        description:
          "Switching tabs during the exam is not allowed. This action has been logged and may result in disqualification.",
      });
    }, 2000);

    const logBlur = debounce(() => {
      logToServer(ExamEventType.TAB_SWITCH);
      openDialog({
        title: "Browser Lost Focus",
        description:
          "Leaving the exam window is not allowed. This action has been logged and may result in disqualification.",
      });
    }, 2000);

    const handleVisibilityChange = () => {
      if (document.hidden) {
        logTabSwitch();
      }
    };

    const handleBlur = () => {
      logBlur();
      lastTabSwitch = Date.now();
    };

    document.addEventListener("visibilitychange", handleVisibilityChange);
    window.addEventListener("blur", handleBlur);

    return () => {
      document.removeEventListener("visibilitychange", handleVisibilityChange);
      window.removeEventListener("blur", handleBlur);
    };
  }, [logToServer, openDialog]);

  // Disable specific keys
  useEffect(() => {
    const disableKeys = (e: KeyboardEvent) => {
      const forbiddenKeys = ["Control", "Alt", "Shift", "Meta", "Tab"];
      if (forbiddenKeys.includes(e.key)) {
        e.preventDefault();
      }
    };

    window.addEventListener("keydown", disableKeys);

    return () => {
      window.removeEventListener("keydown", disableKeys);
    };
  }, []);

  // Warning before closing browser or tab
  useEffect(() => {
    const handleBeforeUnload = (e: BeforeUnloadEvent) => {
      e.preventDefault();
      logToServer(ExamEventType.CLOSE_ATTEMPT, "Attempt to close browser or tab");
    };

    window.addEventListener("beforeunload", handleBeforeUnload);

    return () => {
      window.removeEventListener("beforeunload", handleBeforeUnload);
    };
  }, [logToServer]);

  return {
    WarningDialogComponent: (
      <WarningDialog
        isOpen={isOpen}
        onClose={closeDialog}
        title={config?.title ?? ""}
        description={config?.description ?? ""}
      />
    ),
  };
};

export default useExamSecurity;
