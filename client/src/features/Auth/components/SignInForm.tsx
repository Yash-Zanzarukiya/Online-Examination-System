import { Button } from "@/components/ui/button";
import { Form } from "@/components/ui/form";
import { Loader2 } from "lucide-react";
import { Checkbox } from "@/components/ui/checkbox";
import { Label } from "@/components/ui/label";
import { FormInput } from "@/components/ui/FormInput";
import { useState } from "react";
import { useSignInForm } from "../hooks/useSignInForm";
import { Link } from "react-router-dom";

function SignInForm() {
  const { form, isSubmitting, handleSignIn } = useSignInForm();
  const [showPassword, setShowPassword] = useState(false);

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(handleSignIn)} className="space-y-6 px-6">
        <FormInput
          control={form.control}
          name="identifier"
          label="Username or Email"
          placeholder="Enter username or email"
        />

        <FormInput
          control={form.control}
          name="password"
          label="Password"
          placeholder="Enter your password"
          type={showPassword ? "text" : "password"}
        />

        <div className="flex items-center justify-between space-x-2">
          <div className="flex items-center space-x-2">
            <Checkbox id="show-password" onClick={() => setShowPassword((pre) => !pre)} />
            <Label htmlFor="show-password">Show Password </Label>
          </div>
          <p>
            <Link to="/forgot-password" className="text-blue-600 hover:text-blue-800">
              forgot password?
            </Link>
          </p>
        </div>

        {/* Submit button */}
        <Button type="submit" disabled={isSubmitting} className="w-full">
          {isSubmitting ? (
            <>
              <Loader2 className="mr-2 h-4 w-4 animate-spin" />
              Please wait
            </>
          ) : (
            "Sign In"
          )}
        </Button>
      </form>
    </Form>
  );
}

export default SignInForm;
