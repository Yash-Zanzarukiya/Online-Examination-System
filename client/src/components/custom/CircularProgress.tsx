import { cn } from "@/lib/utils";

interface CircularProgressProps {
  value: number;
  max: number;
  size?: number;
  strokeWidth?: number;
  className?: string;
  textClassName?: string;
}

export default function CircularProgress({
  value = 0, // Add default value
  max = 100, // Add default value
  size = 120,
  strokeWidth = 8,
  className,
  textClassName,
}: CircularProgressProps) {
  // Ensure value is a number and handle undefined/null cases
  const safeValue = Number(value) || 0;
  const safeMax = Number(max) || 100;

  const percentage = (safeValue / safeMax) * 100;
  const radius = (size - strokeWidth) / 2;
  const circumference = radius * 2 * Math.PI;
  const offset = circumference - (percentage / 100) * circumference;

  return (
    <div className={cn("relative inline-flex items-center justify-center", className)}>
      <svg
        width={size}
        height={size}
        viewBox={`0 0 ${size} ${size}`}
        className="transform -rotate-90"
      >
        {/* Background circle */}
        <circle
          cx={size / 2}
          cy={size / 2}
          r={radius}
          strokeWidth={strokeWidth}
          className="fill-none stroke-muted"
        />
        {/* Progress circle */}
        <circle
          cx={size / 2}
          cy={size / 2}
          r={radius}
          strokeWidth={strokeWidth}
          strokeDasharray={circumference}
          strokeDashoffset={offset}
          className="fill-none stroke-primary transition-all duration-300 ease-in-out"
          strokeLinecap="round"
        />
      </svg>
      {/* Percentage text */}
      <div className="absolute inset-0 flex items-center justify-center">
        <span className={`text-2xl ${textClassName} font-medium tabular-nums`}>
          {percentage.toFixed(1)}
        </span>
      </div>
    </div>
  );
}
