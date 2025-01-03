import { useMemo, useState } from "react";
import { ArrowUpDown } from "lucide-react";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Button } from "@/components/ui/button";
import { CandidateExamActivity } from "../types";

export default function ExamActivityTable({ activities }: { activities: CandidateExamActivity[] }) {
  const [isAsc, setIsAsc] = useState(true);

  const sortedActivities = useMemo(() => {
    return [...activities].sort((a, b) => {
      const timeA = new Date(a.createdAt).getTime();
      const timeB = new Date(b.createdAt).getTime();
      return isAsc ? timeA - timeB : timeB - timeA;
    });
  }, [activities, isAsc]);

  return (
    <div className="rounded-md border">
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead>
              <Button
                variant="ghost"
                onClick={() => setIsAsc((prev) => !prev)}
                className="flex items-center gap-1 font-medium"
              >
                Time <ArrowUpDown />
              </Button>
            </TableHead>
            <TableHead>Event</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {sortedActivities.map((activity, index) => (
            <TableRow key={`${activity.createdAt}-${index}`}>
              <TableCell className="font-medium">
                {new Date(activity.createdAt).toLocaleString("en-US", {
                  year: "numeric",
                  month: "short",
                  day: "numeric",
                  hour: "2-digit",
                  minute: "2-digit",
                  second: "2-digit",
                  hour12: true,
                })}
              </TableCell>
              <TableCell>{activity.description}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  );
}
