import { combineReducers } from "@reduxjs/toolkit";
import authReducer from "@/features/Auth/redux/authSlice";
import publicReducer from "@/features/Public/redux/publicSlice";
import collegeReducer from "@/features/college/redux/collegeSlice";

const rootReducer = combineReducers({
  public: publicReducer,
  auth: authReducer,
  college: collegeReducer,
});

export default rootReducer;

export type RootState = ReturnType<typeof rootReducer>;
