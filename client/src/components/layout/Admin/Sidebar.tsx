import { Sidebar, SidebarContent, SidebarFooter, SidebarHeader } from "@/components/ui/sidebar";
import { NavUser } from "./NavUser";
import AdminSidebarContent from "./SidebarContent";
import AdminSidebarHeader from "./SidebarHeader";

export default function AdminSidebar() {
  return (
    <Sidebar variant="floating" collapsible="icon">
      <SidebarHeader>
        <AdminSidebarHeader />
      </SidebarHeader>
      <SidebarContent>
        <AdminSidebarContent />
      </SidebarContent>
      <SidebarFooter>
        <NavUser />
      </SidebarFooter>
    </Sidebar>
  );
}
