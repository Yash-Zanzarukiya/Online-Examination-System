import { SidebarInset, SidebarProvider, SidebarTrigger } from "@/components/ui/sidebar";
import AdminSidebar from "@/components/layout/Admin/Sidebar";

export default function AdminLayout({ children }: { children: React.ReactNode }) {
  return (
    <SidebarProvider>
      <AdminSidebar />
      <SidebarInset className="w-full">
        <main className="relative size-full flex flex-col">
          <SidebarTrigger className="absolute top-2 left-2" />
          {children}
        </main>
      </SidebarInset>
    </SidebarProvider>
  );
}
