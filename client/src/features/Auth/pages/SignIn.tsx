import { Link } from "react-router-dom";
import { TypographyH1 } from "@/components/ui/TypographyH1";
import SignInForm from "../components/SignInForm";
import { useAuthRedirect } from "@/hooks";

function SignIn() {
  useAuthRedirect();

  return (
    <div className="flex justify-center items-center min-h-screen bg-gradient-to-r from-muted to-background">
      <div className="w-full max-w-xl p-8 px-10 pt-10 space-y-6 rounded-lg shadow-md bg-background/80">
        {/* Headers */}
        <div className="text-center">
          <TypographyH1>Welcome Back In</TypographyH1>
          <Link to={"/"} className={`text-4xl lg:text-[2.65rem] tracking-normal`}>
            ExamPro
          </Link>
        </div>

        {/* SignInForm */}
        <SignInForm />

        {/* OR */}
        <div className="flex items-center">
          <hr className="flex-grow border-t border-gray-300" />
          <span className="px-3 text-black font-bold dark:text-gray-200">OR</span>
          <hr className="flex-grow border-t border-gray-300" />
        </div>

        {/* Sign-up */}
        <div className="text-center">
          <p>
            Not a member yet?{" "}
            <Link to="/signup" className="text-blue-600 hover:text-blue-800">
              Sign-up now
            </Link>
          </p>
        </div>
      </div>
    </div>
  );
}

export default SignIn;
