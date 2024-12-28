import {
  ChartNoAxesCombined,
  Layers,
  LayoutDashboard,
  MessageCircleQuestion,
  School,
  Users,
  Wrench,
} from "lucide-react";
import {
  SidebarGroup,
  SidebarGroupContent,
  SidebarGroupLabel,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
} from "@/components/ui/sidebar";
import { Link } from "react-router-dom";

const items = [
  {
    title: "Dashboard",
    url: "",
    icon: LayoutDashboard,
  },
  {
    title: "Question Creator",
    url: "questions/build",
    icon: Wrench,
  },
  {
    title: "Questions",
    url: "questions/all",
    icon: MessageCircleQuestion,
  },
  {
    title: "Exams",
    url: "exams",
    icon: Layers,
  },
  {
    title: "Reports",
    url: "",
    icon: ChartNoAxesCombined,
  },
  {
    title: "Candidates",
    url: "candidates",
    icon: Users,
  },
  {
    title: "Colleges",
    url: "colleges",
    icon: School,
  },
];

function AdminSidebarContent() {
  return (
    <SidebarGroup>
      <SidebarGroupLabel>Application</SidebarGroupLabel>
      <SidebarGroupContent>
        <SidebarMenu>
          {items.map((item) => (
            <SidebarMenuItem key={item.title}>
              <SidebarMenuButton asChild tooltip={item.title}>
                <Link to={item.url}>
                  <item.icon />
                  <span>{item.title}</span>
                </Link>
              </SidebarMenuButton>
            </SidebarMenuItem>
          ))}
        </SidebarMenu>
      </SidebarGroupContent>
    </SidebarGroup>
  );
}

export default AdminSidebarContent;
