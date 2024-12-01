import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { PlusCircle, List, Calendar, BarChart } from "lucide-react";
import { Link } from "react-router-dom";

export default function ExamsManagementPage() {
  const examCards = [
    {
      title: "Create Exam",
      subtitle: "Design and set up a new examination",
      description: "Quickly create a new exam by defining its metadata.",
      link: "create",
      linkText: "Get Started",
      icon: <PlusCircle className="mr-2 size-6" />,
    },
    {
      title: "Manage Exams",
      subtitle: "View and edit your existing exams",
      description: "Access your exam library, make changes, or review exam details.",
      link: "manage",
      linkText: "View Exams",
      icon: <List className="mr-2 size-6" />,
    },
    {
      title: "Schedule Exam",
      subtitle: "Set dates and times for your exams",
      description: "Plan your exam timeline and manage scheduling conflicts.",
      link: "schedule",
      linkText: "Schedule Now",
      icon: <Calendar className="mr-2 size-6" />,
    },
    {
      title: "View Reports",
      subtitle: "Analyze exam results and statistics",
      description: "Get insights into exam performance and student progress.",
      link: "results",
      linkText: "View Reports",
      icon: <BarChart className="mr-2 size-6" />,
    },
  ];

  return (
    <div className="container mx-auto p-6 max-w-6xl">
      <h1 className="text-4xl font-bold mb-10 text-center">Exam Management System</h1>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        {examCards.map((card) => (
          <ExamCards key={card.title} {...card} />
        ))}
      </div>
    </div>
  );
}

interface ExamCardsProps {
  title: string;
  subtitle: string;
  description: string;
  link: string;
  linkText: string;
  icon: React.ReactElement;
}

function ExamCards({ title, subtitle, description, link, linkText, icon }: ExamCardsProps) {
  return (
    <Card>
      <CardHeader>
        <CardTitle className="flex items-center">
          {icon}
          {title}
        </CardTitle>
        <CardDescription>{subtitle}</CardDescription>
      </CardHeader>
      <CardContent>
        <p className="mb-4">{description}</p>
        <Button asChild>
          <Link to={link}>{linkText}</Link>
        </Button>
      </CardContent>
    </Card>
  );
}
