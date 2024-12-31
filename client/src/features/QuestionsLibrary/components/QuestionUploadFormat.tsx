import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
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
            <CardHeader>
              <CardTitle>Questions Format</CardTitle>
            </CardHeader>
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
                <TableBody>
                  <TableRow>
                    <TableCell>1</TableCell>
                    <TableCell>MCQ</TableCell>
                    <TableCell>7946a8df...</TableCell>
                    <TableCell>EASY</TableCell>
                    <TableCell>What is Solidity?</TableCell>
                    <TableCell>1</TableCell>
                    <TableCell></TableCell>
                  </TableRow>
                  <TableRow>
                    <TableCell>2</TableCell>
                    <TableCell>PROGRAMMING</TableCell>
                    <TableCell></TableCell>
                    <TableCell>MEDIUM</TableCell>
                    <TableCell>Reverse an array.</TableCell>
                    <TableCell>10</TableCell>
                    <TableCell className="font-mono text-xs">void reverse(int[] num)</TableCell>
                  </TableRow>
                </TableBody>
              </Table>
            </CardContent>
          </Card>
        </TabsContent>
        <TabsContent value="options">
          <Card>
            <CardHeader>
              <CardTitle>Options Format</CardTitle>
            </CardHeader>
            <CardContent>
              <Table>
                <TableHeader>
                  <TableRow>
                    <TableHead className="w-[100px]">Sr. No.</TableHead>
                    <TableHead>Option Text</TableHead>
                    <TableHead>Is Correct</TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody>
                  <TableRow>
                    <TableCell>1</TableCell>
                    <TableCell>Language</TableCell>
                    <TableCell>TRUE</TableCell>
                  </TableRow>
                  <TableRow>
                    <TableCell>1</TableCell>
                    <TableCell>Property</TableCell>
                    <TableCell>FALSE</TableCell>
                  </TableRow>
                  <TableRow>
                    <TableCell>3</TableCell>
                    <TableCell>Quick Sort</TableCell>
                    <TableCell>TRUE</TableCell>
                  </TableRow>
                </TableBody>
              </Table>
            </CardContent>
          </Card>
        </TabsContent>
      </Tabs>
    </div>
  );
}
