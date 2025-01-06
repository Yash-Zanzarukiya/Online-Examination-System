import { ModeToggle } from "@/components/Theming/mode-toggle";
import { Button } from "@/components/ui/button";
import { Card, CardHeader, CardTitle, CardDescription } from "@/components/ui/card";
import { useAuthRedirect } from "@/hooks";
import { navigateTo, toastMessage } from "@/utils";
import { CheckCircle, Lock, BarChart, Users } from "lucide-react";

const features = [
  {
    title: "Secure Exams",
    description: "Advanced proctoring and anti-cheating measures ensure exam integrity.",
    icon: Lock,
  },
  {
    title: "Real-time Analytics",
    description: "Get instant insights into student performance and exam statistics.",
    icon: BarChart,
  },
  {
    title: "User-friendly Interface",
    description: "Intuitive design for both exam creators and test-takers.",
    icon: Users,
  },
  {
    title: "Automated Grading",
    description: "Save time with instant grading for multiple-choice and short-answer questions.",
    icon: CheckCircle,
  },
];

export default function LandingPage() {
  useAuthRedirect();

  return (
    <div className="min-h-screen bg-background text-foreground">
      <header className="py-4 px-4 sm:px-6 lg:px-8 border-b border-border">
        <div className="container mx-auto flex justify-between items-center">
          <p className="text-2xl font-bold text-primary">OES</p>
          <nav className="hidden md:flex space-x-4">
            <p className="text-muted-foreground hover:text-primary">Features</p>
            <p className="text-primary">Home</p>
            <p className="text-muted-foreground hover:text-primary">About</p>
          </nav>
          <div className="flex space-x-2">
            <ModeToggle />
            <Button variant="ghost" onClick={() => navigateTo("/login")}>
              Login
            </Button>
            <Button onClick={() => navigateTo("/signup")}>Sign Up</Button>
          </div>
        </div>
      </header>
      <main>
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
                <p className="">Elevate your exams</p>
                {/* Title */}
                <div className="mt-4 max-w-2xl">
                  <h1 className="scroll-m-20 text-4xl font-extrabold tracking-tight lg:text-5xl">
                    Revolutionize Online Exams
                  </h1>
                </div>
                {/* End Title */}
                <div className="mt-5 max-w-3xl">
                  <p className="text-xl text-muted-foreground">
                    Secure, efficient, and user-friendly online examination platform for educational
                    institutions and businesses.
                  </p>
                </div>
                {/* Buttons */}
                <div className="mt-8 gap-3 flex justify-center">
                  <Button size={"lg"} onClick={() => navigateTo("/features")}>
                    Get started
                  </Button>
                  <Button
                    size={"lg"}
                    variant={"outline"}
                    onClick={() => toastMessage("No More Learning Needed 🤨")}
                  >
                    Learn more
                  </Button>
                </div>
                {/* End Buttons */}
              </div>
            </div>
          </div>
        </div>

        <section id="features" className="py-20 px-4 sm:px-6 lg:px-8 bg-background">
          <div className="container mx-auto">
            <h2 className="text-3xl font-bold text-center mb-12">Key Features</h2>
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8">
              {features.map((feature, index) => (
                <Card key={index}>
                  <CardHeader>
                    <feature.icon className="w-10 h-10 mb-4 text-primary" />
                    <CardTitle>{feature.title}</CardTitle>
                    <CardDescription>{feature.description}</CardDescription>
                  </CardHeader>
                </Card>
              ))}
            </div>
          </div>
        </section>

        <section className="py-20 px-4 sm:px-6 lg:px-8 bg-background text-secondary-foreground">
          <div className="container mx-auto text-center">
            <h2 className="text-3xl font-bold mb-4">Ready to Transform Your Exams?</h2>
            <p className="text-xl mb-8 max-w-2xl mx-auto">
              Join thousands of institutions already using OES to conduct secure and efficient
              online examinations.
            </p>
            <Button size="lg" variant="default">
              Start Your Free Trial
            </Button>
          </div>
        </section>
      </main>
    </div>
  );
}
