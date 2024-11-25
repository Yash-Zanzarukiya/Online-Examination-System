import { useEffect } from "react";
import { useAuth } from "./";
import { Roles } from "@/types/Roles";
import { useNavigate } from "react-router-dom";

function useAuthRedirect() {
  const navigate = useNavigate();

  const { authData, isAuthenticated } = useAuth();

  useEffect(() => {
    if (isAuthenticated && authData?.role === Roles.ADMIN) {
      navigate("/admin");
    } else if (isAuthenticated && authData?.role === Roles.STUDENT) {
      navigate("/user");
    }
  }, [authData?.role, isAuthenticated, navigate]);
}

export default useAuthRedirect;
