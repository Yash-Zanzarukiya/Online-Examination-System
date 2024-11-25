import { useUniqueUsername } from "../hooks/useUniqueUsername";
import { useSignUpForm } from "../hooks/useSignUpForm";
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { Loader2 } from "lucide-react";
import { FormInput } from "@/components/custom/FormInput";
import { useState } from "react";
import { Checkbox } from "@/components/ui/checkbox";
import { Label } from "@/components/ui/label";
import { Button } from "@/components/ui/button";

function SignUpForm() {
  const [showPassword, setShowPassword] = useState(false);

  const { form, handleSignUp, isSubmitting } = useSignUpForm();

  const { isAvailable, isChecking, setUsername, username, usernameMessage } = useUniqueUsername();

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(handleSignUp)} className="space-y-6 px-6">
        {/* Username */}
        <FormField
          control={form.control}
          name="username"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Username</FormLabel>
              {/* Input field */}
              <FormControl>
                <Input
                  {...field}
                  onChange={(e) => {
                    field.onChange(e);
                    setUsername(e.target.value);
                  }}
                  placeholder="Choose your username"
                />
              </FormControl>

              {/* Unique username check response */}
              <FormDescription>
                {isChecking ? (
                  <Loader2 className=" animate-spin" />
                ) : (
                  username &&
                  usernameMessage && (
                    <p className={`text-sm ${isAvailable ? "text-green-500" : "text-red-500"}`}>
                      {usernameMessage}
                    </p>
                  )
                )}
              </FormDescription>
              <FormMessage />
            </FormItem>
          )}
        />

        {/* Email */}
        <FormInput
          name="email"
          control={form.control}
          label="Email"
          type="email"
          placeholder="Enter your email"
        />

        {/* Full Name */}
        <FormInput
          name="fullName"
          control={form.control}
          label="Full Name"
          type="text"
          placeholder="Enter your full name"
        />

        {/* Password */}
        <FormInput
          name="password"
          control={form.control}
          label="Password"
          type={showPassword ? "text" : "password"}
          placeholder="Create your password"
        />

        <div className="flex items-center justify-between space-x-2">
          <div className="flex items-center space-x-2">
            <Checkbox id="show-password" onClick={() => setShowPassword((pre) => !pre)} />
            <Label htmlFor="show-password" className=" cursor-pointer">
              Show Password
            </Label>
          </div>
        </div>

        {/* Submit button */}
        <Button type="submit" disabled={isSubmitting || !isAvailable} className="w-full">
          {isSubmitting ? (
            <>
              <Loader2 className="mr-2 h-4 w-4 animate-spin" />
              Please wait
            </>
          ) : (
            "Sign Up"
          )}
        </Button>
      </form>
    </Form>
  );
}

export default SignUpForm;
