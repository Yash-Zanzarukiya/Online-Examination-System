import { useAppDispatch, useAppSelector } from "@/app/hooks";
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectLabel,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { ScheduledExamStatus } from "@/features/ActiveExam/types";
import { updateScheduleExamStatus } from "../redux/examThunks";
import { UUID } from "crypto";

interface ScheduledExamStatusSelectorProps {
  scheduledExamId: UUID;
}

function ScheduledExamStatusSelector({ scheduledExamId }: ScheduledExamStatusSelectorProps) {
  const dispatch = useAppDispatch();

  const scheduledExam = useAppSelector(({ exams }) => exams.scheduledExam);

  function handleStatusChange(newStatus: ScheduledExamStatus) {
    dispatch(updateScheduleExamStatus({ scheduledExamId, status: newStatus }));
  }

  if (!scheduledExam) return null;

  return (
    <Select defaultValue={scheduledExam.status} onValueChange={handleStatusChange}>
      <SelectTrigger className="text-xs">
        <SelectValue placeholder="Status" />
      </SelectTrigger>
      <SelectContent>
        <SelectGroup>
          <SelectLabel>Update Status</SelectLabel>
          {Object.values(ScheduledExamStatus).map((status) => (
            <SelectItem key={status} value={status}>
              {status.replace(/_/g, " ")}
            </SelectItem>
          ))}
        </SelectGroup>
      </SelectContent>
    </Select>
  );
}

export default ScheduledExamStatusSelector;
