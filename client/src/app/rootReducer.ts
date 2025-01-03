import { combineReducers } from "@reduxjs/toolkit";
import authReducer from "@/features/Auth/redux/authSlice";
import publicReducer from "@/features/Public/redux/publicSlice";
import collegeReducer from "@/features/college/redux/collegeSlice";
import studentProfileReducer from "@/features/student/redux/studentSlice";
import questionReducer from "@/features/QuestionBuilder/redux/questionSlice";
import questionPickerReducer from "@/features/QuestionPicker/redux/questionPickerSlice";
import examReducer from "@/features/Exam/redux/examSlice";
import activeExamReducer from "@/features/ActiveExam/redux/activeExamSlice";
import examCandidatesReducer from "@/features/ExamCandidates/redux/examCandidatesSlice";
import candidateReportReducer from "@/features/CandidateReport/redux/candidateReportSlice";
import questionLibraryReducer from "@/features/QuestionsLibrary/redux/questionLibrarySlice";

const rootReducer = combineReducers({
  public: publicReducer,
  auth: authReducer,
  college: collegeReducer,
  studentProfile: studentProfileReducer,
  questions: questionReducer,
  questionPicker: questionPickerReducer,
  exams: examReducer,
  activeExam: activeExamReducer,
  examCandidates: examCandidatesReducer,
  candidateReport: candidateReportReducer,
  questionLibrary: questionLibraryReducer,
});

export default rootReducer;

export type RootState = ReturnType<typeof rootReducer>;
