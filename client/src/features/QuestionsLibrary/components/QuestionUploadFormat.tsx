import { Table, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { Card, CardContent } from "@/components/ui/card";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";

export default function QuestionUploadFormat() {
  return (
    <div className="w-full max-w-4xl mx-auto p-4 space-y-4">
      <Tabs defaultValue="questions" className="w-full">
        <TabsList className="grid w-full grid-cols-2">
          <TabsTrigger value="questions">Questions Sheet</TabsTrigger>
          <TabsTrigger value="options">Options Sheet</TabsTrigger>
        </TabsList>
        <TabsContent value="questions">
          <Card>
            <CardContent>
              <Table>
                <TableHeader>
                  <TableRow>
                    <TableHead>Sr. No.</TableHead>
                    <TableHead>Type</TableHead>
                    <TableHead>Category ID</TableHead>
                    <TableHead>Difficulty</TableHead>
                    <TableHead>Question Text</TableHead>
                    <TableHead>Marks</TableHead>
                    <TableHead>Reference Answer</TableHead>
                  </TableRow>
                </TableHeader>
              </Table>
            </CardContent>
          </Card>
        </TabsContent>
        <TabsContent value="options">
          <Card>
            <CardContent>
              <Table>
                <TableHeader>
                  <TableRow>
                    <TableHead className="w-[100px]">Sr. No.</TableHead>
                    <TableHead>Option Text</TableHead>
                    <TableHead>Is Correct</TableHead>
                  </TableRow>
                </TableHeader>
              </Table>
            </CardContent>
          </Card>
        </TabsContent>
      </Tabs>
    </div>
  );
}
