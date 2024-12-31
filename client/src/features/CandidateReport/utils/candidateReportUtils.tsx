import { AlertCircle, CheckCircle2, CircleMinus, XCircle } from "lucide-react";
import { QuestionEvaluationStatus } from "../types";

export const getStatusIcon = (status: QuestionEvaluationStatus) => {
  switch (status) {
    case QuestionEvaluationStatus.CORRECT:
      return <CheckCircle2 className="h-5 w-5 text-green-500" />;
    case QuestionEvaluationStatus.PARTIALLY_CORRECT:
      return <AlertCircle className="h-5 w-5 text-yellow-500" />;
    case QuestionEvaluationStatus.NOT_ANSWERED:
      return <CircleMinus className="h-5 w-5 text-gray-500" />;
    default:
      return <XCircle className="h-5 w-5 text-red-500" />;
  }
};
