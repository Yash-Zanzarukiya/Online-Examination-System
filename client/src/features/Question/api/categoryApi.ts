import { CategoryDTO } from "@/types/Category";
import { axiosInstance, toastApiError } from "@/utils";

const getAllCategories = async (): Promise<CategoryDTO[] | []> => {
  try {
    const apiRes = await axiosInstance.get("/categories");
    return apiRes.data.data;
  } catch (error: any) {
    toastApiError("Failed to fetch categories", error);
    return [];
  }
};

const categoryApi = {
  getAllCategories,
};

export default categoryApi;
