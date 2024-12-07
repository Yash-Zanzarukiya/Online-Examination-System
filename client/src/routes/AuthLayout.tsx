import { Outlet } from "react-router-dom";
import { useAuth } from "@/hooks";
import { Roles } from "@/types/Roles";
import { navigateTo } from "@/utils";

interface IAuthLayoutProps {
  allowedRoles?: Roles[];
  authentication?: boolean;
  children?: React.ReactNode;
}

function AuthLayout({ authentication = true, allowedRoles, children }: IAuthLayoutProps) {
  const { isAuthenticated, authData } = useAuth();

  if (!isAuthenticated && authentication) {
    navigateTo("/login");
  } else if (!authData && authentication) {
    navigateTo("/");
  } else if (authData && allowedRoles && !allowedRoles.includes(authData.role)) {
    navigateTo("/");
  }

  if (children) return children;

  return <Outlet />;
}

export default AuthLayout;
