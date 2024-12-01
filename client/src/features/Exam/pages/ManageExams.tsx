import ConfirmDialog from "@/components/custom/ConfirmDialog";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { TypographyH2 } from "@/components/ui/TypographyH2";
import { Pencil, Trash2 } from "lucide-react";
import { Link } from "react-router-dom";

function ManageExams() {
  const exams = [
    { id: 1, name: "Mathematics Final", date: "2023-12-15", status: "Scheduled" },
    { id: 2, name: "Physics Midterm", date: "2023-12-18", status: "Draft" },
    { id: 3, name: "English Literature Quiz", date: "2023-12-20", status: "Completed" },
    { id: 4, name: "History Test", date: "2023-12-22", status: "Scheduled" },
  ];

  return (
    <div className="container grow mx-auto p-10">
      <Button asChild className="mb-4">
        <Link to={"/admin/exams"}>← Back to Dashboard</Link>
      </Button>
      <TypographyH2>Manage Exams</TypographyH2>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mt-2">
        {exams.map((exam) => (
          <Card key={exam.id} className="relative">
            <CardHeader>
              <CardTitle>{exam.name}</CardTitle>
            </CardHeader>
            <CardContent>
              <p className="text-sm text-gray-500">Date: {exam.date}</p>
              <p className="text-sm text-gray-500">Status: {exam.status}</p>
            </CardContent>
            <CardFooter className="flex justify-between items-center">
              <Button variant="outline">Manage Questions</Button>
              <div className="space-x-2">
                <Button size="icon" variant="outline">
                  <Pencil className="h-4 w-4" />
                </Button>
                <ConfirmDialog onConfirm={() => {}}>
                  <Button size="icon" variant="outline">
                    <Trash2 className="h-4 w-4 text-destructive" />
                  </Button>
                </ConfirmDialog>
              </div>
            </CardFooter>
          </Card>
        ))}
      </div>
    </div>
  );
}

export default ManageExams;
