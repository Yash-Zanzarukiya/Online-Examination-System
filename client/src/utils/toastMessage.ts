let toastUtil: any;

export const setToastUtil = (toast: any) => {
  toastUtil = toast;
};

const toastMessage = (title: string, description = "", variant = true, duration = 3000) => {
  if (!toastUtil) return console.error("Toast function is not initialized.");

  toastUtil({
    title: title || "",
    description: description,
    variant: variant ? "default" : "destructive",
    duration,
  });
};

export default toastMessage;
