import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Code, List } from "lucide-react";
import { Link } from "react-router-dom";

const States = {
  ADD_QUESTION: "ADD_QUESTION",
  QUESTION_TYPES: "QUESTION_TYPES",
  MCQ_QUESTION: "MCQ_QUESTION",
  PROGRAMMING_QUESTION: "PROGRAMMING_QUESTION",
};

function AddQuestionButton() {
  const [state, setState] = useState(States.ADD_QUESTION);
  return (
    <div>
      {/* Add content */}
      {state === States.ADD_QUESTION && (
        <Button className="w-fit" size="sm" onClick={() => setState(States.QUESTION_TYPES)}>
          Add Question
        </Button>
      )}

      {/* Content options */}
      {state === States.QUESTION_TYPES && (
        <div className="flex items-center gap-2">
          <Button asChild variant="outline" size="sm">
            <Link to={"/admin/questions/mcq"}>
              <List className="h-4 w-4 mr-2 text-purple-500" />
              MCQ
            </Link>
          </Button>
          <Button asChild variant="outline" size="sm">
            <Link to={"/admin/questions/programming"}>
              <Code className="h-4 w-4 mr-1 text-yellow-400" />
              Programming
            </Link>
          </Button>
          <Button
            variant="outline"
            size="sm"
            className="text-destructive"
            onClick={() => setState(States.ADD_QUESTION)}
          >
            Cancel
          </Button>
        </div>
      )}
    </div>
  );
}

export default AddQuestionButton;
