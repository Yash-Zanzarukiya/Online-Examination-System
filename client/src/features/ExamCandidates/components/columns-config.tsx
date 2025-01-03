import { ColumnDef } from "@tanstack/react-table";
import { ArrowUpDown } from "lucide-react";
import { Button } from "@/components/ui/button";
import { HoverCard, HoverCardContent, HoverCardTrigger } from "@/components/ui/hover-card";
import { Badge } from "@/components/ui/badge";
import { CandidateState, ExamAttemptStatus } from "../types";
import { format } from "date-fns";
import { getStatusColor, getTimelineText } from "../utils/candidateTableUtils";

export const columns: ColumnDef<CandidateState>[] = [
  {
    accessorKey: "candidateName",
    header: ({ column }) => {
      return (
        <Button
          variant="ghost"
          onClick={() => column.toggleSorting(column.getIsSorted() === "asc")}
        >
          Candidate
          <ArrowUpDown className="ml-2 h-4 w-4" />
        </Button>
      );
    },
    cell: ({ row }) => (
      <HoverCard>
        <HoverCardTrigger asChild>
          <Button variant="link" className="p-0">
            {row.getValue("candidateName")}
          </Button>
        </HoverCardTrigger>
        <HoverCardContent className="w-80">
          <div className="flex justify-between space-x-4">
            <div className="space-y-1">
              <h4 className="text-sm font-semibold">{row.getValue("candidateName")}</h4>
              <p className="text-sm text-muted-foreground">{row.original.email}</p>
            </div>
          </div>
        </HoverCardContent>
      </HoverCard>
    ),
  },
  {
    accessorKey: "status",
    header: "Status",
    cell: ({ row }) => {
      const status = row.getValue("status") as ExamAttemptStatus;
      return (
        <Badge variant="outline" className={`${getStatusColor(status)}`}>
          {status.replace("_", " ")}
        </Badge>
      );
    },
    filterFn: (row, id, value) => {
      if (value === "all") return true;
      return value.includes(row.getValue(id));
    },
  },
  {
    accessorKey: "score",
    header: ({ column }) => {
      return (
        <Button
          variant="ghost"
          onClick={() => column.toggleSorting(column.getIsSorted() === "asc")}
        >
          Score
          <ArrowUpDown className="ml-2 h-4 w-4" />
        </Button>
      );
    },
    cell: ({ row }) => {
      const score = row.original.score;
      const total = row.original.totalMarks;
      const percentage = ((score / total) * 100).toFixed(2);
      return (
        <div className="font-medium">
          {percentage}% ({score}/{total})
        </div>
      );
    },
    filterFn: (row, _id, value) => {
      const score = row.original.score;
      const total = row.original.totalMarks;
      const percentage = (score / total) * 100;
      const [min, max] = value;
      return percentage >= min && percentage <= max;
    },
  },
  {
    accessorKey: "timeline",
    header: "Timeline",
    cell: ({ row }) => <div>{getTimelineText(row.original)}</div>,
  },
  {
    accessorKey: "endTime",
    header: ({ column }) => {
      return (
        <Button
          variant="ghost"
          onClick={() => column.toggleSorting(column.getIsSorted() === "asc")}
        >
          Completed on
          <ArrowUpDown className="ml-2 h-4 w-4" />
        </Button>
      );
    },
    cell: ({ row }) => {
      const endTime = row.getValue("endTime") as string;
      return endTime ? format(new Date(endTime), "dd MMM yyyy, HH:mm") : "-";
    },
  },
];
