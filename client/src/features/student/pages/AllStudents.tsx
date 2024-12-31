import { Suspense } from "react";
import { columns } from "../components/student-data-columns";
import useStudentData from "../hooks/useStudentData";
import { StudentDataTable } from "../components/StudentDataTable";
import { TypographyH3 } from "@/components/ui/TypographyH3";

function AllStudents() {
  const { students } = useStudentData();

  return (
    <div className="container mx-auto p-10">
      <TypographyH3>All Candidates</TypographyH3>
      <Suspense fallback={<div>Loading...</div>}>
        <StudentDataTable columns={columns} data={students} />
      </Suspense>
    </div>
  );
}

export default AllStudents;
