import CircularProgress from "@/components/custom/CircularProgress";
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectLabel,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { CandidateState, ExamAttemptStatus } from "@/features/ExamCandidates/types";
import { getStatusColor } from "@/features/ExamCandidates/utils/candidateTableUtils";

const updatableStatus = [
  ExamAttemptStatus.TO_EVALUATE,
  ExamAttemptStatus.PASSED,
  ExamAttemptStatus.FAILED,
  ExamAttemptStatus.QUALIFIED,
];

interface CandidateHeaderProps {
  candidateState: CandidateState;
  handleStatusChange: (status: ExamAttemptStatus) => void;
}

function CandidateHeader({ candidateState, handleStatusChange }: CandidateHeaderProps) {
  return (
    <div className="flex items-center justify-between p-6 border-b">
      <div className="flex items-center gap-4">
        <CircularProgress
          value={candidateState.score}
          max={candidateState.totalMarks}
          size={50}
          strokeWidth={6}
          textClassName="text-sm"
        />
        <div>
          <h2 className="text-xl font-bold">{candidateState.candidateName}</h2>
          <div className="flex items-center gap-2">
            <span className="text-muted-foreground"></span>
          </div>
        </div>
      </div>
      <div className="flex gap-2">
        <Select
          defaultValue={candidateState.status}
          disabled={!updatableStatus.includes(candidateState.status)}
          onValueChange={handleStatusChange}
        >
          <SelectTrigger className={`${getStatusColor(candidateState.status)}`}>
            <SelectValue placeholder="Status" />
          </SelectTrigger>
          <SelectContent>
            <SelectGroup>
              <SelectLabel>Update Status</SelectLabel>
              {!updatableStatus.includes(candidateState.status) && (
                <SelectItem value={candidateState.status} disabled>
                  {candidateState.status.replace(/_/g, " ")}
                </SelectItem>
              )}
              {updatableStatus.map((status) => (
                <SelectItem key={status} value={status} className={`${getStatusColor(status)}`}>
                  {status.replace(/_/g, " ")}
                </SelectItem>
              ))}
            </SelectGroup>
          </SelectContent>
        </Select>
      </div>
    </div>
  );
}

export default CandidateHeader;
