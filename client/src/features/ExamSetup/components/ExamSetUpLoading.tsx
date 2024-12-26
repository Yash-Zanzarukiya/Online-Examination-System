import { useEffect, useState } from "react";

function ExamSetUpLoading() {
  const [text, setText] = useState("");
  const fullText = "Setting up your Exam";

  useEffect(() => {
    if (text.length < fullText.length) {
      setTimeout(() => {
        setText(fullText.slice(0, text.length + 1));
      }, 100);
    }
  }, [text]);

  return (
    <div className="min-h-screen flex flex-col items-center justify-center">
      {bouncingDots}
      <h1 key="setup-message" className="scroll-m-20 text-3xl font-bold tracking-tight lg:text-3xl">
        {text}
        <span className="animate-pulse"> |</span>
      </h1>
    </div>
  );
}

export default ExamSetUpLoading;

const bouncingDots = (
  <svg
    key="bouncingDots"
    xmlns="http://www.w3.org/2000/svg"
    viewBox="0 0 100 100"
    height="110"
    className="block"
  >
    <g>
      <g transform="matrix(1,0,0,1,20,50)">
        <circle fill="#4285F4" r="2" cy="0" cx="0" className="animate-breathing delay-200" />
      </g>
      <g transform="matrix(1,0,0,1,40,50)">
        <circle fill="#DB4437" r="2" cy="0" cx="0" className="animate-breathing delay-400" />
      </g>
      <g transform="matrix(1,0,0,1,60,50)">
        <circle fill="#F4B400" r="2" cy="0" cx="0" className="animate-breathing delay-600" />
      </g>
      <g transform="matrix(1,0,0,1,80,50)">
        <circle fill="#0F9D58" r="2" cy="0" cx="0" className="animate-breathing delay-800" />
      </g>
    </g>
  </svg>
);
