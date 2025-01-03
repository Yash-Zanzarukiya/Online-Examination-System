export function formatTimestamp(timestamp: string) {
  const now = new Date();
  const date = new Date(timestamp);
  const diffInSeconds = Math.floor((now.getTime() - date.getTime()) / 1000);

  if (diffInSeconds < 60) {
    return "Just now";
  } else if (diffInSeconds < 3600) {
    return `${Math.floor(diffInSeconds / 60)} minutes ago`;
  } else if (diffInSeconds < 86400) {
    return `${Math.floor(diffInSeconds / 3600)} hours ago`;
  } else if (diffInSeconds < 604800) {
    return `${Math.floor(diffInSeconds / 86400)} days ago`;
  } else if (diffInSeconds < 2592000) {
    return `${Math.floor(diffInSeconds / 604800)} weeks ago`;
  } else {
    return `${Math.floor(diffInSeconds / 2592000)} months ago`;
  }
}

export function formatFutureTimestamp(timestamp: string) {
  const now = new Date();
  const date = new Date(timestamp);

  const diffInSeconds = Math.floor((date.getTime() - now.getTime()) / 1000);

  if (diffInSeconds < 60) {
    return `${diffInSeconds} seconds`;
  } else if (diffInSeconds < 3600) {
    return `${Math.floor(diffInSeconds / 60)} minutes`;
  } else if (diffInSeconds < 86400) {
    return `${Math.floor(diffInSeconds / 3600)} hours`;
  } else if (diffInSeconds < 604800) {
    return `${Math.floor(diffInSeconds / 86400)} days`;
  } else if (diffInSeconds < 2592000) {
    return `${Math.floor(diffInSeconds / 604800)} weeks`;
  } else {
    return `${Math.floor(diffInSeconds / 2592000)} months`;
  }
}

function formatHMSDuration(totalSecs: number) {
  let totalSeconds = Math.floor(totalSecs);

  let seconds = totalSeconds % 60 < 9 ? "0" + (totalSeconds % 60) : totalSeconds % 60;
  let minutes = Math.floor(totalSeconds / 60);
  let hours = Math.floor(minutes / 60);

  if (hours > 0) {
    return `${hours}:${minutes}:${seconds}`;
  } else {
    return `${minutes}:${seconds}`;
  }
}

function formatMinSecDuration(totalMS: number) {
  const totalSeconds = Math.floor(totalMS / 1000);
  const minutes = Math.floor(totalSeconds / 60);
  const seconds = totalSeconds % 60;

  const formattedMinutes = minutes < 10 ? "0" + minutes : minutes;
  const formattedSeconds = seconds < 10 ? "0" + seconds : seconds;

  if (minutes > 0) {
    return `${formattedMinutes} min ${formattedSeconds} sec`;
  } else {
    return `${formattedSeconds} sec`;
  }
}

function formatDate(timestamp: string) {
  const date = new Date(timestamp);
  const days = date.getDate();
  const months = date.getMonth() + 1;

  const day = days < 10 ? "0" + days : days;
  const month = months < 10 ? "0" + months : months;

  return day + "-" + month + "-" + date.getFullYear();
}

function formatScheduleDate(date: Date): string {
  return date.toLocaleDateString("en-US", {
    year: "numeric",
    month: "long",
    day: "numeric",
  });
}

function formatScheduleTime(date: Date): string {
  return date.toLocaleTimeString("en-US", {
    hour: "2-digit",
    minute: "2-digit",
    hour12: false,
  });
}

const formatter = {
  formatTimestamp,
  formatHMSDuration,
  formatDate,
  formatMinSecDuration,
  formatScheduleDate,
  formatScheduleTime,
  formatFutureTimestamp,
};

export default formatter;
