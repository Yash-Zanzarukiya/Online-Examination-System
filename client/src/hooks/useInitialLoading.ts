import { useAppDispatch } from "@/app/hooks";
import { getCurrentUserThunk } from "@/features/Auth/redux/authThunks";
import { checkHealthThunk } from "@/features/Public/redux/publicThunks";
import { useEffect, useState } from "react";

function useInitialLoading() {
  const dispatch = useAppDispatch();
  const [initialLoading, setInitialLoading] = useState(true);

  useEffect(() => {
    const initializeApp = async () => {
      await dispatch(checkHealthThunk());
      await dispatch(getCurrentUserThunk());
      setInitialLoading(false);
    };

    initializeApp();
  }, []);

  return initialLoading;
}

export default useInitialLoading;
