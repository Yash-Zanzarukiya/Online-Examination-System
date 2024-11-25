import "./App.css";
import { Toaster } from "@/components/ui/toaster";
import { InitialLoadingPage, LandingPage } from "./pages";
import { useToast } from "@/hooks/use-toast";
import { Route, Routes, useNavigate } from "react-router-dom";
import { setToastUtil } from "./utils/toastMessage";
import { setNavigationUtil } from "./utils/navigate";
import { useAuthRedirect, useInitialLoading } from "./hooks";
import { Login, SignUp } from "./features/Auth/pages";
import { AdminDashboard } from "./features/admin/pages";
import AuthLayout from "./routes/AuthLayout";
import { Roles } from "./types/Roles";
import CollegePage from "./features/college/pages/CollegePage";

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

        {/* Protected Routes */}
        <Route path="/admin" element={<AuthLayout allowedRoles={[Roles.ADMIN]} />}>
          <Route path="" element={<AdminDashboard />} />
          <Route path="colleges" element={<CollegePage />} />
        </Route>

        <Route element={<AuthLayout allowedRoles={[Roles.STUDENT]} />}>
          <Route path="/user" element={<h1>Welcome User</h1>} />
        </Route>

        <Route path="*" element={<div>404</div>} />
      </Routes>
    </>
  );
}

export default App;
