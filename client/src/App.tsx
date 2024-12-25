import "./App.css";
import { Toaster } from "@/components/ui/toaster";
import { InitialLoadingPage, LandingPage } from "./pages";
import { useToast } from "@/hooks/use-toast";
import { Outlet, Route, Routes, useNavigate } from "react-router-dom";
import { setToastUtil } from "./utils/toastMessage";
import { setNavigationUtil } from "./utils/navigate";
import { useInitialLoading } from "./hooks";
import { Login, SignUp } from "./features/Auth/pages";
import { AdminDashboard } from "./features/admin/pages";
import AuthLayout from "./routes/AuthLayout";
import { Roles } from "./types/Roles";
import CollegePage from "./features/college/pages/CollegePage";
import { StudentDashboard, StudentProfile } from "./features/student/pages";
import QuestionCreator from "./features/QuestionBuilder/pages/QuestionCreator";
import AllQuestions from "./features/QuestionBuilder/pages/AllQuestions";
import QuestionPicker from "./features/QuestionPicker/pages/QuestionPicker";
import {
  CreateExamPage,
  ExamsManagementPage,
  ManageExam,
  ScheduleExam,
} from "./features/Exam/pages";
import ActiveExam from "./features/ActiveExam/pages/ActiveExam";
import AdminLayout from "./components/layout/Admin/AdminLayout";
import ExamAuthPage from "./features/ExamSetup/pages/ExamAuthPage";
import ExamSetup from "./features/ExamSetup/pages/ExamSetup";

function App() {
  const { toast } = useToast();
  const navigate = useNavigate();

  setToastUtil(toast);
  setNavigationUtil(navigate);

  const isLoading = useInitialLoading();

  if (isLoading) {
    return <InitialLoadingPage />;
  }

  return (
    <>
      <Toaster />
      <Routes>
        {/* Public Routes */}
        <Route path="/" element={<LandingPage />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/exam/auth/:examId" element={<ExamAuthPage />} />
        <Route path="/exam/set-up/:examId" element={<ExamSetup />} />

        {/* Protected Routes */}

        {/* Admin Routes */}
        <Route
          path="/admin"
          element={
            <AuthLayout allowedRoles={[Roles.ADMIN]}>
              <AdminLayout>
                <Outlet />
              </AdminLayout>
            </AuthLayout>
          }
        >
          <Route path="" element={<AdminDashboard />} />
          <Route path="colleges" element={<CollegePage />} />
          <Route path="questions/build" element={<QuestionCreator />} />
          <Route path="questions/all" element={<AllQuestions />} />
          <Route path="questions/pick" element={<QuestionPicker />} />
          <Route path="exams" element={<ExamsManagementPage />} />
          <Route path="exams/create" element={<CreateExamPage />} />
          <Route path="exams/manage" element={<ManageExam />} />
          <Route path="exams/manage/:examId/pick" element={<QuestionPicker />} />
          <Route path="exams/schedule" element={<ScheduleExam />} />
        </Route>

        <Route path="/student" element={<AuthLayout allowedRoles={[Roles.STUDENT]} />}>
          <Route path="" element={<StudentDashboard />} />
          <Route path="profile" element={<StudentProfile />} />
        </Route>

        {/* Exam Routes */}
        <Route path="/active-exam" element={<AuthLayout authentication={false} />}>
          <Route path=":examId" element={<ActiveExam />} />
        </Route>

        <Route path="*" element={<div>404</div>} />
      </Routes>
    </>
  );
}

export default App;
