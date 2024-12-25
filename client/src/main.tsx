import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./index.css";
import App from "./App.tsx";
import { ThemeProvider } from "@/components/Theming/theme-provider.tsx";
import AppRouter from "./AppRouter.tsx";
import { Provider } from "react-redux";
import { store } from "./app/store.ts";
import { WebSocketProvider } from "./features/ExamWebSocket/WebSocketProvider.tsx";

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <Provider store={store}>
      <AppRouter>
        <ThemeProvider defaultTheme="dark" storageKey="vite-ui-theme">
          <WebSocketProvider>
            <App />
          </WebSocketProvider>
        </ThemeProvider>
      </AppRouter>
    </Provider>
  </StrictMode>
);
