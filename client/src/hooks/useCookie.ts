import { useState, useEffect } from "react";

const useCookie = (cookieName: string): string | null => {
  const [cookieValue, setCookieValue] = useState<string | null>(null);

  useEffect(() => {
    const getCookie = (name: string): string | null => {
      const cookieString = document.cookie;
      const cookies = cookieString.split("; ");
      for (const cookie of cookies) {
        const [key, value] = cookie.split("=");
        if (key === name) {
          return decodeURIComponent(value);
        }
      }
      return null;
    };

    setCookieValue(getCookie(cookieName));
  }, [cookieName]);

  return cookieValue;
};

export default useCookie;
