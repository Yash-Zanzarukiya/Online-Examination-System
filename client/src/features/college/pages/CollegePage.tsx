import { useEffect } from "react";
import { useCollegeCrud } from "../hooks/useCollegeCrud";
import { CollegeDialog, CollegeRow } from "../components";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Table, TableBody, TableHeader, TableHead, TableRow } from "@/components/ui/table";
import { useCollegeDialog } from "../hooks/useCollegeDialog";
import { UUID } from "crypto";
import TableSkeleton from "../components/Skeletons/TableSkeleton";

export default function CollegePage() {
  const { colleges, loading, loadColleges, createCollege, editCollege, removeCollege } =
    useCollegeCrud();

  const { isDialogOpen, openDialog, closeDialog, selectedCollege } = useCollegeDialog();

  useEffect(() => {
    loadColleges();
  }, [loadColleges]);

  const handleSubmitCollege = async (data: { name: string }) => {
    if (selectedCollege) {
      await editCollege(selectedCollege.id, data);
    } else {
      await createCollege(data);
    }
    closeDialog();
  };

  const handleDeleteClick = (collegeId: UUID) => removeCollege(collegeId);

  return (
    <Card className="w-full max-w-4xl mx-auto my-8">
      <CardHeader>
        <CardTitle className="text-2xl font-bold text-primary">College Management</CardTitle>
      </CardHeader>
      <CardContent>
        <div className="flex justify-end mb-4">
          <Button onClick={() => openDialog(null)}>Add New College</Button>
        </div>
        {loading && !colleges.length ? (
          <TableSkeleton />
        ) : (
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead>Name</TableHead>
                <TableHead className="text-right">Actions</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {colleges.map((college) => (
                <CollegeRow
                  key={college.id}
                  college={college}
                  onEdit={openDialog}
                  onDelete={handleDeleteClick}
                />
              ))}
            </TableBody>
          </Table>
        )}
        <CollegeDialog
          isOpen={isDialogOpen}
          onClose={closeDialog}
          onSubmit={handleSubmitCollege}
          initialData={selectedCollege}
        />
      </CardContent>
    </Card>
  );
}
