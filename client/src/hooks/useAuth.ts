import { useAppDispatch, useAppSelector } from "@/app/hooks";
import { logoutThunk } from "@/features/Auth/redux/authThunks";
import { navigateTo } from "@/utils";

const useAuth = () => {
  const dispatch = useAppDispatch();

  const { authData, status, isLoading } = useAppSelector(({ auth }) => auth);

  const handleLogout = async () => {
    if (!authData && status == false) return;

    await dispatch(logoutThunk());

    if (!status) navigateTo("/login");
  };

  return {
    authData,
    isAuthenticated: status,
    isLoading,
    handleLogout,
  };
};

export default useAuth;
