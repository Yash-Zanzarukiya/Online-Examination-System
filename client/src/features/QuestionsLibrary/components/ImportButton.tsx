import { useAppDispatch } from "@/app/hooks";
import { Button } from "@/components/ui/button";
import { uploadQuestions } from "../redux/questionThunks";
import { Download } from "lucide-react";
import { useState } from "react";
import ImportQuestionDataDialog from "./ImportQuestionDataDialog";

export default function ImportButton() {
  const dispatch = useAppDispatch();
  const [isImportDialogOpen, setIsImportDialogOpen] = useState(false);

  const handleImport = (file: File) => {
    if (file) {
      const formData = new FormData();
      formData.append("file", file);
      console.log("FormData ready to be sent:", formData);
      dispatch(uploadQuestions(formData));
    }
  };

  return (
    <>
      <Button variant="outline" onClick={() => setIsImportDialogOpen(true)}>
        <Download className=" h-4 w-4" />
        Import
      </Button>
      <ImportQuestionDataDialog
        isOpen={isImportDialogOpen}
        onClose={() => setIsImportDialogOpen(false)}
        onImport={handleImport}
      />
    </>
  );
}

{
  /* <Button>
      Import Excel
      <Input
        id="import-file"
        type="file"
        accept=".xlsx, .xls"
        className="hidden"
        onChange={handleImport}
      />
    </Button> */
}
