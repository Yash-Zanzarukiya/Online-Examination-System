import { useState, useEffect } from "react";
import Editor from "@monaco-editor/react";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";

const parseMarkdown = (markdown: string) => {
  const match = markdown.match(/```(\w+)\n([\s\S]*?)```/);
  if (match) {
    const detectedLanguage = match[1];
    const extractedCode = match[2].trim();
    return { detectedLanguage, extractedCode };
  }
  return { detectedLanguage: "javascript", extractedCode: markdown };
};

const generateMarkdown = (lang: string, codeContent: string) => {
  return `\`\`\`${lang}\n${codeContent}\n\`\`\``;
};

const supportedLanguages = ["java", "javascript", "python", "cpp", "csharp", "typescript"];

interface AdvancedCodeEditorProps {
  initialMarkdown: string;
  onChange?: (markdownCode: string) => void;
}

const AdvancedCodeEditor = function AdvancedCodeEditor({
  initialMarkdown = "",
  onChange,
}: AdvancedCodeEditorProps) {
  const [code, setCode] = useState("");
  const [language, setLanguage] = useState("javascript");

  useEffect(() => {
    const { detectedLanguage, extractedCode } = parseMarkdown(initialMarkdown);
    setLanguage(detectedLanguage);
    setCode(extractedCode);
  }, [initialMarkdown]);

  const handleEditorChange = (value: string | undefined) => {
    if (value !== undefined) {
      setCode(value);
      onChange && onChange(generateMarkdown(language, value));
    }
  };

  const handleLanguageChange = (newLanguage: string) => {
    setLanguage(newLanguage);
    onChange && onChange(generateMarkdown(newLanguage, code));
  };

  // console.log("initialCode: ", initialCode);
  return (
    <div className="space-y-2 h-full w-full">
      <Select onValueChange={handleLanguageChange} value={language}>
        <SelectTrigger className="w-fit">
          <SelectValue placeholder="Select Language" />
        </SelectTrigger>
        <SelectContent>
          {supportedLanguages.map((lang) => (
            <SelectItem key={lang} value={lang}>
              {lang.charAt(0).toUpperCase() + lang.slice(1)}
            </SelectItem>
          ))}
        </SelectContent>
      </Select>
      <Editor
        className="h-full"
        language={language}
        value={code}
        // defaultValue={initialCode}
        onChange={handleEditorChange}
        theme="vs-dark"
        options={{
          minimap: { enabled: false },
          fontSize: 14,
          lineNumbers: "on",
          automaticLayout: true,
          tabSize: 2,
        }}
      />
    </div>
  );
};

export default AdvancedCodeEditor;
