import { Outlet } from "react-router-dom";
import { useAuth } from "@/hooks";
import { Roles } from "@/types/Roles";
import { navigateTo } from "@/utils";
import AdminLayout from "@/components/layout/Admin/AdminLayout";

interface IAuthLayoutProps {
  allowedRoles?: Roles[];
  authentication?: boolean;
}

function AuthLayout({ authentication = true, allowedRoles }: IAuthLayoutProps) {
  const { isAuthenticated, authData } = useAuth();

  if (!isAuthenticated && authentication) {
    navigateTo("/login");
  } else if (!authData && authentication) {
    navigateTo("/");
  } else if (authData && allowedRoles && !allowedRoles.includes(authData.role)) {
    navigateTo("/");
  }

  return (
    <AdminLayout>
      <Outlet />
    </AdminLayout>
  );
}

export default AuthLayout;
