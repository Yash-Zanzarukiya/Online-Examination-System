function InitialLoadingPage() {
  return (
    <div className="flex justify-center items-center h-screen">
      <div className="flex justify-between w-20">
        <div className="size-4 bg-primary rounded-full animate-grow-shrink delay-100"></div>
        <div className="size-4 bg-primary rounded-full animate-grow-shrink delay-300"></div>
        <div className="size-4 bg-primary rounded-full animate-grow-shrink delay-500"></div>
      </div>
    </div>
  );
}

export default InitialLoadingPage;
