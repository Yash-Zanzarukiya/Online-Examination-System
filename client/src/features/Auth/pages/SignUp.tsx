import { Link } from "react-router-dom";
import SignUpForm from "../components/SignUpForm";
import { TypographyH1 } from "@/components/ui/TypographyH1";

function SignUp() {
  return (
    <div className="flex justify-center items-center min-h-screen bg-gradient-to-r from-muted to-background">
      <div className="w-full max-w-xl p-8 pt-10 space-y-6 bg-background rounded-lg shadow-md">
        {/* Headers */}
        <div className="text-center">
          <TypographyH1>
            Join <Link to={"/"}>OES</Link>
          </TypographyH1>
          <p>Sign up to start giving exam</p>
        </div>

        {/* SignUpForm */}
        <SignUpForm />

        {/* OR */}
        <div className="flex items-center">
          <hr className="flex-grow border-t border-gray-300" />
          <span className="px-3 text-black font-bold dark:text-gray-200">OR</span>
          <hr className="flex-grow border-t border-gray-300" />
        </div>

        {/* Sign In */}
        <div className="text-center mt-4">
          <p>
            Already a member?{" "}
            <Link to="/login" className="text-blue-600 hover:text-blue-800">
              Sign in
            </Link>
          </p>
        </div>
      </div>
    </div>
  );
}

export default SignUp;
