import { Button } from "@/components/ui/button";
import { Link } from "react-router-dom";
import { useState } from "react";
import { TypographyH4 } from "@/components/ui/TypographyH4";
import { BaseExams, DraftedExams, ScheduledExams } from "../components";

export enum ExamTabs {
  SCHEDULED_EXAMS = "Scheduled Exams",
  BASE_EXAMS = "Base Exams",
  DRAFTED_EXAMS = "Drafted Exams",
}

function ExamsDashboard() {
  const [activeTab, setActiveTab] = useState(ExamTabs.SCHEDULED_EXAMS);

  return (
    <div className="container grow mx-auto p-10 pt-0">
      <div className="border-b">
        <div className="px-4 py-3">
          <div className="flex items-center justify-between">
            <div className="flex items-center gap-2">
              <TypographyH4>Manage Exams</TypographyH4>
            </div>
            <div className="flex items-center gap-2">
              <Button asChild size="sm" className="bg-green-600 hover:bg-green-700">
                <Link to="/admin/exams/create">Create Exam</Link>
              </Button>
            </div>
          </div>
          <div className="mt-4">
            <nav className="flex gap-8">
              {Object.values(ExamTabs).map((tab, index) => (
                <span
                  key={index}
                  onClick={() => setActiveTab(tab)}
                  className={`pb-1 text-sm font-medium cursor-pointer ${
                    activeTab.valueOf() === tab
                      ? " border-b-2 border-blue-600 text-foreground"
                      : " text-muted-foreground"
                  }`}
                >
                  {tab}
                </span>
              ))}
            </nav>
          </div>
        </div>
      </div>
      {activeTab == ExamTabs.BASE_EXAMS && <BaseExams />}
      {activeTab == ExamTabs.DRAFTED_EXAMS && <DraftedExams />}
      {activeTab == ExamTabs.SCHEDULED_EXAMS && <ScheduledExams />}
    </div>
  );
}

export default ExamsDashboard;
