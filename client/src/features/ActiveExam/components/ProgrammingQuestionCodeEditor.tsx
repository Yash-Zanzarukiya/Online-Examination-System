import React, { useCallback } from "react";
import AdvancedCodeEditor from "@/components/custom/AdvancedCodeEditor";
import { UUID } from "crypto";

interface ProgrammingQuestionCodeEditor {
  questionId: UUID;
  submittedCode: string | null;
  onChange: (questionId: UUID, code: string) => void;
}

const defaultCode = `\`\`\`javascript\n\/\/Write your code here...\n\`\`\``;

const ProgrammingQuestionCodeEditor: React.FC<ProgrammingQuestionCodeEditor> = React.memo(
  ({ questionId, submittedCode, onChange }) => {
    const handleEditorChange = useCallback(
      (value: string) => onChange(questionId, value.toString()),
      [onChange, questionId]
    );

    return (
      <div className="w-full h-96">
        <AdvancedCodeEditor
          initialMarkdown={submittedCode || defaultCode}
          onChange={handleEditorChange}
        />
      </div>
    );
  }
);

export default ProgrammingQuestionCodeEditor;
