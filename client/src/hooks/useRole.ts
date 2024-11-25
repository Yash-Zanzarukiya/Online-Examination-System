import { Roles } from "@/types/Roles";
import { useAuth } from "./";
import { navigateTo } from "@/utils";

const useRole = (allowedRoles: Roles[], redirect = false) => {
  const { authData } = useAuth();

  if (!authData || !authData?.role || !allowedRoles.includes(authData?.role)) {
    if (redirect) navigateTo("/");
    return false;
  }

  return true;
};

export default useRole;
