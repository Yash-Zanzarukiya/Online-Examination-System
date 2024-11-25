import React from "react";
import { BrowserRouter } from "react-router-dom";

const AppRouter = ({ children }: { children: React.ReactElement }) => {
  return <BrowserRouter>{children}</BrowserRouter>;
};

export default AppRouter;
