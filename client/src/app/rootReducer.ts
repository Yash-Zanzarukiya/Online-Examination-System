import { combineReducers } from "@reduxjs/toolkit";
import authReducer from "@/features/Auth/redux/authSlice";
import publicReducer from "@/features/Public/redux/publicSlice";

const rootReducer = combineReducers({
  public: publicReducer,
  auth: authReducer,
});

export default rootReducer;

export type RootState = ReturnType<typeof rootReducer>;
