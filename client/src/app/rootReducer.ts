import { combineReducers } from "@reduxjs/toolkit";
import authReducer from "@/features/Auth/redux/authSlice";
import publicReducer from "@/features/Public/redux/publicSlice";
import collegeReducer from "@/features/college/redux/collegeSlice";
import studentProfileReducer from "@/features/student/redux/studentSlice";

const rootReducer = combineReducers({
  public: publicReducer,
  auth: authReducer,
  college: collegeReducer,
  studentProfile: studentProfileReducer,
});

export default rootReducer;

export type RootState = ReturnType<typeof rootReducer>;
