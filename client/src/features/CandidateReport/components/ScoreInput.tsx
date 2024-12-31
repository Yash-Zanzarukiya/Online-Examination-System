import { useAppDispatch } from "@/app/hooks";
import { useState, useCallback } from "react";
import { updateProgrammingMarks } from "../redux/candidateReportThunks";
import { UUID } from "crypto";
import { Input } from "@/components/ui/input";

interface ScoreInputProps {
  initialScore: number;
  maxScore: number;
  programmingSubmissionId: UUID;
}

function ScoreInput({ initialScore, maxScore, programmingSubmissionId }: ScoreInputProps) {
  const dispatch = useAppDispatch();
  const [inputScore, setInputScore] = useState(initialScore);
  const [currentScore, setCurrentScore] = useState(initialScore);

  const handleApiCall = useCallback(
    (marks: number) => {
      dispatch(updateProgrammingMarks({ programmingSubmissionId, marks }));
    },
    [dispatch, programmingSubmissionId]
  );

  const handleInputChange = useCallback((event: React.ChangeEvent<HTMLInputElement>) => {
    setInputScore(Number(event.target.value));
  }, []);

  const handleBlurOrEnter = useCallback(() => {
    const validScore = Math.max(0, Math.min(inputScore, maxScore));
    if (validScore !== currentScore) {
      setCurrentScore(validScore);
      handleApiCall(validScore);
    }
  }, [inputScore, maxScore, currentScore]);

  const handleKeyDown = useCallback(
    (event: React.KeyboardEvent<HTMLInputElement>) => {
      if (event.key === "Enter") handleBlurOrEnter();
    },
    [handleBlurOrEnter]
  );

  return (
    <Input
      type="number"
      value={inputScore}
      onChange={handleInputChange}
      onBlur={handleBlurOrEnter}
      onKeyDown={handleKeyDown}
      min={0}
      max={maxScore}
      className="w-fit"
    />
  );
}

export default ScoreInput;
