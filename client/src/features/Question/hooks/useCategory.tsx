import { CategoryDTO } from "@/types/Category";
import { useEffect, useState } from "react";
import categoryApi from "../api/categoryApi";

export function useCategory() {
  const [categories, setCategories] = useState<CategoryDTO[] | []>([]);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    const fetchCategories = async () => {
      setIsLoading(true);
      const categoriesRes = await categoryApi.getAllCategories();
      setCategories(() => categoriesRes);
      setIsLoading(false);
    };
    fetchCategories();
  }, []);

  return { categories, isLoading };
}
