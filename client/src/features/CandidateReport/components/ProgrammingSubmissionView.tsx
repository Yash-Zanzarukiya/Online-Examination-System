import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { useProgrammingSubmission } from "../hooks";
import { UUID } from "crypto";
import { Badge } from "@/components/ui/badge";
import AdvancedCodeEditor from "@/components/custom/AdvancedCodeEditor";
import ScoreInput from "./ScoreInput";

function ProgrammingSubmissionView({ questionId }: { questionId: UUID }) {
  const { isLoading, programmingSubmission } = useProgrammingSubmission(questionId);

  if (isLoading || !programmingSubmission) {
    return <div>Loading...</div>;
  }

  const { question, submittedCode, score, id } = programmingSubmission;

  return (
    <Card>
      <CardHeader className="py-4">
        <CardTitle>
          <div className="flex justify-between">
            <div className="text-sm text-muted-foreground flex gap-2">
              <Badge variant="outline" className="border-green-600">
                {question.question.type}
              </Badge>
              <Badge variant="outline" className="border-red-600">
                {question.question.difficulty}
              </Badge>
            </div>
            {/* make it updatable */}
            <div className="flex items-center gap-2">
              <span>Score:</span>
              <ScoreInput
                initialScore={score}
                maxScore={question.question.marks}
                programmingSubmissionId={id}
              />
              <span> / {question.question.marks}</span>
            </div>
          </div>
        </CardTitle>
      </CardHeader>
      <CardContent className="space-y-2 overflow-auto">
        <div className="space-y-2">
          <h4 className="font-semibold">Question</h4>
          <div className="whitespace-pre-wrap rounded-lg bg-muted p-2">
            {question.question.questionText}
          </div>
        </div>

        <Tabs defaultValue="submitted" className="max-h-full">
          <TabsList>
            <TabsTrigger value="submitted">Submitted Code</TabsTrigger>
            <TabsTrigger value="reference">Reference Solution</TabsTrigger>
          </TabsList>
          <TabsContent value="submitted">
            <div className="mt-2">
              <div className="w-full h-96">
                <AdvancedCodeEditor
                  initialMarkdown={
                    submittedCode ||
                    `\`\`\`javascript\n\/\/ Candidate has not written any code\n\`\`\``
                  }
                />
              </div>
            </div>
          </TabsContent>
          <TabsContent value="reference" className="h-full">
            <div className="mt-2">
              <div className="w-full h-96">
                <AdvancedCodeEditor
                  initialMarkdown={
                    question.referenceAnswer ||
                    `\`\`\`javascript\n\/\/ No Reference answer found\n\`\`\``
                  }
                />
              </div>
            </div>
          </TabsContent>
        </Tabs>
      </CardContent>
    </Card>
  );
}

export default ProgrammingSubmissionView;
