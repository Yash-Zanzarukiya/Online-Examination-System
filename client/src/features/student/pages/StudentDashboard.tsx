import { Button } from "@/components/ui/button";
import { useAuth } from "@/hooks";
import { navigateTo } from "@/utils";

function StudentDashboard() {
  const { authData, handleLogout } = useAuth();

  return (
    <div className="relative flex items-center justify-center overflow-hidden py-24 lg:py-32">
      {/* Gradients */}
      <div
        aria-hidden="true"
        className="flex absolute -top-96 start-1/2 transform -translate-x-1/2"
      >
        <div className="bg-gradient-to-r from-background/50 to-background blur-3xl w-[25rem] h-[44rem] rotate-[-60deg] transform -translate-x-[10rem]" />
        <div className="bg-gradient-to-tl blur-3xl w-[90rem] h-[50rem] rounded-full origin-top-left -rotate-12 -translate-x-[15rem] from-primary-foreground via-primary-foreground to-background" />
      </div>
      {/* End Gradients */}
      <div className="relative z-10">
        <div className="container py-10 lg:py-16">
          <div className="max-w-2xl text-center mx-auto">
            <p className="">Give your exams</p>
            {/* Title */}
            <div className="mt-4 max-w-2xl">
              <h1 className="scroll-m-20 text-4xl font-extrabold tracking-tight lg:text-5xl">
                Welcome back, {authData?.fullName}!!!
              </h1>
            </div>
            {/* End Title */}
            <div className="mt-5 max-w-3xl">
              <p className="text-xl text-muted-foreground">
                You can give your exams and view your results here.
              </p>
            </div>
            {/* Buttons */}
            <div className="mt-8 gap-3 flex justify-center">
              <Button
                size={"lg"}
                onClick={() => navigateTo(`/student/profile`)}
              >
                View Profile
              </Button>
              <Button size={"lg"} variant={"outline"} onClick={() => handleLogout()}>
                Logout
              </Button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default StudentDashboard;
