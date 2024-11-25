import { useEffect, useState } from "react";
import { checkUniqueUsername } from "../api/authApi";
import { toastMessage } from "@/utils";
import { usernameValidation } from "../validators";
import { useDebounced } from "@/hooks";

export const useUniqueUsername = () => {
  const [isAvailable, setIsAvailable] = useState<boolean>(true);
  const [isChecking, setIsChecking] = useState<boolean>(false);
  const [username, setUsername] = useState<string>("");
  const [usernameMessage, setUsernameMessage] = useState<string>("");
  const { debouncedValue: debouncedUsername } = useDebounced(username, 700);

  useEffect(() => {
    const checkUsername = async () => {
      if (!validate()) return;

      setIsChecking(true);

      try {
        const res = await checkUniqueUsername(username);
        setIsAvailable(() => res.data);
        setIsChecking(false);
        setUsernameMessage(res.message);
      } catch (error) {
        toastMessage("Failed to check Username");
        setUsernameMessage("failed to check username");
        setIsAvailable(false);
      } finally {
        setIsChecking(false);
      }
    };

    if (username) checkUsername();
  }, [debouncedUsername]);

  function validate(): boolean {
    if (!debouncedUsername) {
      setUsernameMessage("");
      setIsAvailable(true);
      return false;
    }

    const validationResult = usernameValidation.safeParse(debouncedUsername);
    if (!validationResult.success) {
      setUsernameMessage(validationResult.error.errors[0].message);
      setIsAvailable(false);
      return false;
    }

    return true;
  }

  return { isAvailable, isChecking, usernameMessage, username, setUsername };
};
