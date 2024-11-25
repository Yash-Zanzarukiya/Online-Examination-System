import { StudentProfileForm } from "../components/StudentProfileForm";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { useStudentProfile } from "../hooks/useStudentProfile";
import { useAuth } from "@/hooks";
import { UUID } from "crypto";

// BUG : On Refresh College is not getting populated
export default function StudentProfilePage() {
  const { authData } = useAuth();

  useStudentProfile(authData?.userId);

  return (
    <div className="container h-screen flex items-center justify-center">
      <Card className="w-full max-w-xl">
        <CardHeader>
          <CardTitle className="text-2xl font-bold text-primary">Student Profile</CardTitle>
          <CardDescription>Complete or update your student profile information</CardDescription>
        </CardHeader>
        <CardContent>
          <StudentProfileForm userId={authData?.userId as UUID} />
        </CardContent>
      </Card>
    </div>
  );
}
