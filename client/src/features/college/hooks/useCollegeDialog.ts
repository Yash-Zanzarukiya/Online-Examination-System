import { useState } from "react";
import { CollegeDTO } from "../types";

export function useCollegeDialog() {
  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const [selectedCollege, setSelectedCollege] = useState<CollegeDTO | null>();

  const openDialog = (college: CollegeDTO | null = null) => {
    setSelectedCollege(college);
    setIsDialogOpen(true);
  };

  const closeDialog = () => {
    setSelectedCollege(null);
    setIsDialogOpen(false);
  };

  return {
    isDialogOpen,
    setIsDialogOpen,
    openDialog,
    closeDialog,
    selectedCollege,
    setSelectedCollege,
  };
}
