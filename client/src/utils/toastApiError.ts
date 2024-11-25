import toastMessage from "./toastMessage";

export default function toastApiError(title?: string, error?: any, duration = 3000) {
  console.log("toastErr: " + error);

  const errMessage = error?.response?.data?.message;

  toastMessage(
    title || "Oops! Something went wrong...",
    errMessage || "There was a problem with your request. please try again",
    false,
    duration
  );
}
