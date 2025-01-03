import { Button } from "@/components/ui/button";
import { TypographyH4 } from "@/components/ui/TypographyH4";
import { UUID } from "crypto";
import { NavLink, Outlet, useParams } from "react-router-dom";
import ExamInviteButton from "./ExamInviteButton";
import ScheduledExamStatusSelector from "./ScheduledExamStatusSelector";

interface ManageExamHeaderProps {
  title?: string;
  examTabs?: { name: string; to: string }[];
  normal?: boolean;
}

function ManageExamHeader({ title, examTabs, normal }: ManageExamHeaderProps) {
  const { scheduledExamId } = useParams();

  return (
    <div className="container grow mx-auto p-7 pt-1">
      <div className="border-b">
        <div className="px-4 py-3">
          <div className="flex items-center justify-between">
            <TypographyH4>{title}</TypographyH4>
            <div className="flex items-center gap-2">
              <Button variant="outline" size="sm">
                Try Test
              </Button>
              {!normal && <ExamInviteButton scheduledExamId={scheduledExamId as UUID} />}
              {!normal && <ScheduledExamStatusSelector scheduledExamId={scheduledExamId as UUID} />}
            </div>
          </div>
          <div className="mt-4">
            <nav className="flex gap-8">
              {examTabs?.map((tab, index) => (
                <NavLink
                  key={index}
                  to={tab.to}
                  end
                  className={({ isActive }) =>
                    `pb-1 text-sm font-medium ${
                      isActive
                        ? " border-b-2 border-blue-600 text-foreground"
                        : " text-muted-foreground"
                    }`
                  }
                >
                  {tab.name}
                </NavLink>
              ))}
            </nav>
          </div>
        </div>
      </div>
      <Outlet />
    </div>
  );
}

export default ManageExamHeader;
