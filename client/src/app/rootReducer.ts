import { combineReducers } from "@reduxjs/toolkit";
import authReducer from "@/features/Auth/redux/authSlice";
import publicReducer from "@/features/Public/redux/publicSlice";
import collegeReducer from "@/features/college/redux/collegeSlice";
import studentProfileReducer from "@/features/student/redux/studentSlice";
import questionReducer from "@/features/QuestionBuilder/redux/questionSlice";
import questionPickerReducer from "@/features/QuestionPicker/redux/questionPickerSlice";
import examReducer from "@/features/Exam/redux/examSlice";

const rootReducer = combineReducers({
  public: publicReducer,
  auth: authReducer,
  college: collegeReducer,
  studentProfile: studentProfileReducer,
  questions: questionReducer,
  questionPicker: questionPickerReducer,
  exams: examReducer,
});

export default rootReducer;

export type RootState = ReturnType<typeof rootReducer>;
