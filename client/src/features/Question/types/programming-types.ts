import { UUID } from "crypto";
import { Question } from "./question-types";

export interface ProgrammingQuestion {
  id: UUID;
  question: Question;
  referenceAnswer: string;
}
