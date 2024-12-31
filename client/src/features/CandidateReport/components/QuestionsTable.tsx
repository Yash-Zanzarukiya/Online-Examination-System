import { useCallback, useState } from "react";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { QuestionsAnalysis, QuestionPerformance, QuestionEvaluationStatus } from "../types";
import {
  McqSubmissionView,
  ProgrammingSubmissionView,
} from "@/features/CandidateReport/components";
import { getStatusIcon } from "../utils/candidateReportUtils";
import { UUID } from "crypto";
import { QuestionType } from "@/types/QuestionType";
import { formatter } from "@/utils";

interface QuestionTableProps {
  questionsAnalysis: QuestionsAnalysis[];
}

export default function QuestionsTable({ questionsAnalysis }: QuestionTableProps) {
  const [selectedQuestion, setSelectedQuestion] = useState<{ id: UUID; type: QuestionType }>();
  const [showDialog, setShowDialog] = useState(false);

  const handleQuestionClick = useCallback((question: QuestionPerformance, sectionName: string) => {
    const type = sectionName === "MCQ" ? QuestionType.MCQ : QuestionType.PROGRAMMING;
    setSelectedQuestion({ id: question.questionId, type });
    setShowDialog(true);
  }, []);

  return (
    <>
      <div>
        {questionsAnalysis.map((section) => (
          <div key={section.sectionName} className="mb-8">
            <div className="flex items-center justify-between mb-4">
              <h3 className="text-lg font-semibold">{section.sectionName}</h3>
              <div className="text-sm text-muted-foreground">
                <span>Total Questions: {section.totalQuestions}</span>
                <span className="mx-2">|</span>
                <span>Attempted: {section.attemptedQuestions}</span>
                <span className="mx-2">|</span>
                <span>Time Taken: {formatter.formatMinSecDuration(section.timeTaken)}</span>
                <span className="mx-2">|</span>
                <span>
                  Score: {section.score}/{section.totalMarks}
                </span>
              </div>
            </div>
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead className="text-center">No.</TableHead>
                  <TableHead>Question</TableHead>
                  <TableHead className="text-center">Category</TableHead>
                  <TableHead className="text-center">Score</TableHead>
                  <TableHead className="text-center">Time Taken</TableHead>
                  <TableHead className="text-center">Status</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {section.questionPerformances.map((question, index) => (
                  <TableRow
                    key={question.questionId}
                    className="cursor-pointer hover:bg-muted/50"
                    onClick={() =>
                      question.status !== QuestionEvaluationStatus.NOT_ANSWERED &&
                      handleQuestionClick(question, section.sectionName)
                    }
                  >
                    <TableCell className="text-center">{index + 1}</TableCell>
                    <TableCell>
                      <div className="max-w-60">
                        <div className="font-medium truncate">{question.questionText}</div>
                      </div>
                    </TableCell>
                    <TableCell className="text-center">{question.category || "-"}</TableCell>
                    <TableCell className="text-center">
                      {question.score}/{question.totalMarks}
                    </TableCell>
                    <TableCell className="text-center">
                      {formatter.formatMinSecDuration(question.timeTaken)}
                    </TableCell>
                    <TableCell className="flex justify-center">
                      {getStatusIcon(question.status)}
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </div>
        ))}
      </div>

      <Dialog open={showDialog} onOpenChange={setShowDialog}>
        <DialogContent
          className={`max-w-3xl max-h-[90vh] overflow-y-auto ${
            selectedQuestion?.type == QuestionType.PROGRAMMING && "max-w-4xl"
          }`}
        >
          <DialogHeader>
            <DialogTitle>{selectedQuestion?.type.valueOf()} SUBMISSION</DialogTitle>
          </DialogHeader>
          {selectedQuestion &&
            (selectedQuestion.type === QuestionType.MCQ ? (
              <McqSubmissionView questionId={selectedQuestion.id} />
            ) : (
              <ProgrammingSubmissionView questionId={selectedQuestion.id} />
            ))}
        </DialogContent>
      </Dialog>
    </>
  );
}
