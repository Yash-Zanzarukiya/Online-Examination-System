import { NavigateFunction } from "react-router-dom";

let navigate: NavigateFunction;

export const setNavigationUtil = (navigator: NavigateFunction) => {
  navigate = navigator;
};

export default function navigateTo(url: string) {
  navigate(url);
}
