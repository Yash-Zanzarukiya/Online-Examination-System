import { useEffect } from "react";
import { useCollegeCrud } from "./useCollegeCrud";

export function useColleges() {
  const { colleges, loadColleges, loading } = useCollegeCrud();

  useEffect(() => {
    loadColleges();
  }, [loadColleges]);

  return { colleges, isLoading: loading };
}
