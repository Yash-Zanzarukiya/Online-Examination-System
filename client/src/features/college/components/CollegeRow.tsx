import { TableCell, TableRow } from "@/components/ui/table";
import { CollegeDTO } from "../types";
import { Button } from "@/components/ui/button";
import { Pencil, Trash2 } from "lucide-react";
import ConfirmDialog from "@/components/custom/ConfirmDialog";
import { UUID } from "crypto";

interface ICollegeRowProps {
  college: CollegeDTO;
  onEdit: (college: CollegeDTO) => void;
  onDelete: (collegeId: UUID) => void;
}

const CollegeRow = ({ college, onEdit, onDelete }: ICollegeRowProps) => (
  <TableRow>
    <TableCell>{college.name}</TableCell>
    <TableCell className="text-right">
      <Button variant="ghost" size="icon" onClick={() => onEdit(college)}>
        <Pencil className="h-4 w-4" />
      </Button>
      <ConfirmDialog onConfirm={() => onDelete(college.id)}>
        <Button variant="ghost" size="icon">
          <Trash2 className="h-4 w-4 text-destructive" />
        </Button>
      </ConfirmDialog>
    </TableCell>
  </TableRow>
);

export default CollegeRow;
