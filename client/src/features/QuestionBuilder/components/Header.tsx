import { Button } from "@/components/ui/button";
import { Import, Upload } from "lucide-react";

export default function Header() {
  return (
    <>
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold text-primary">Welcome back, James</h1>
        <div className="space-x-2">
          <Button variant="outline" className="text-primary">
            <Import className="w-4 h-4 mr-2" />
            Import
          </Button>
          <Button className="bg-primary text-primary-foreground">
            <Upload className="w-4 h-4 mr-2" />
            Publish
          </Button>
        </div>
      </div>
      <p className="text-muted-foreground mb-6">Build and publish your tests in no time.</p>
    </>
  );
}
