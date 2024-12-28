import { Card, CardContent, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { MoveRight } from "lucide-react";
import { ScheduledExam } from "../types";
import { Button } from "@/components/ui/button";
import { Link } from "react-router-dom";
import { formatter } from "@/utils";
import { ScheduledExamStatus } from "@/features/ActiveExam/types";
import { Badge } from "@/components/ui/badge";

interface ScheduledExamCardProps {
  scheduledExam: ScheduledExam;
}

export default function ScheduledExamCard({ scheduledExam }: ScheduledExamCardProps) {
  return (
    <>
      <Card key={scheduledExam.id} className="relative">
        <CardHeader>
          <CardTitle>
            <Link to={`scheduled/${scheduledExam.id}/${scheduledExam.examId}`}>
              {scheduledExam.name}
            </Link>
          </CardTitle>
        </CardHeader>
        <CardContent>
          {scheduledExam.status === ScheduledExamStatus.SCHEDULED && (
            <p className="text-sm text-muted-foreground">
              Starting in: {formatter.formatFutureTimestamp(scheduledExam.startingAt)}
            </p>
          )}
          {scheduledExam.status === ScheduledExamStatus.LIVE && (
            <p className="text-sm text-muted-foreground">Exam is live</p>
          )}
          {scheduledExam.status === ScheduledExamStatus.CLOSED && (
            <p className="text-sm text-muted-foreground">Exam is closed</p>
          )}
          <p className="text-sm text-muted-foreground">
            Scheduled: {formatter.formatTimestamp(scheduledExam.createdAt)}
          </p>
        </CardContent>
        <CardFooter className="flex justify-between items-center">
          <Button variant="outline" size="sm" asChild>
            <Link to={`scheduled/${scheduledExam.id}/${scheduledExam.examId}/pick`}>
              Manage Questions
            </Link>
          </Button>
          {scheduledExam.status === ScheduledExamStatus.SCHEDULED && (
            <Badge variant="outline">{scheduledExam.status}</Badge>
          )}
          {scheduledExam.status === ScheduledExamStatus.LIVE && (
            <Badge variant="destructive">{scheduledExam.status}</Badge>
          )}
          {scheduledExam.status === ScheduledExamStatus.CLOSED && (
            <Badge variant="default">{scheduledExam.status}</Badge>
          )}
          <div className="space-x-2">
            <Button size="icon" variant="outline" asChild>
              <Link to={`scheduled/${scheduledExam.id}/${scheduledExam.examId}`}>
                <MoveRight />
              </Link>
            </Button>
          </div>
        </CardFooter>
      </Card>
    </>
  );
}
