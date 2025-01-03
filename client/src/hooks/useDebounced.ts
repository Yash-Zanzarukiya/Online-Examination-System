import { useEffect, useState } from "react";

function useDebounced(value: any, timeout = 500) {
  const [debouncedValue, setDebouncedValue] = useState<any>();

  useEffect(() => {
    const handler = setTimeout(() => {
      setDebouncedValue(value);
    }, timeout);

    return () => clearTimeout(handler);
  }, [value, timeout]);

  return { debouncedValue, setDebouncedValue };
}

export default useDebounced;
