import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogClose,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";

interface ConfirmDialogProps {
  children: React.ReactNode;
  onConfirm: () => void;
  title?: string;
  description?: string;
  confirmText?: string;
  onCancel?: () => void;
  onConfirmClose?: boolean;
}

function ConfirmDialog({
  children,
  title,
  description,
  confirmText,
  onConfirm,
  onCancel = () => {},
  onConfirmClose = true,
}: ConfirmDialogProps) {
  return (
    <Dialog>
      <DialogTrigger asChild>{children}</DialogTrigger>
      <DialogContent className="sm:max-w-[425px]">
        <DialogHeader>
          <DialogTitle>{title || "Are you absolutely sure?"}</DialogTitle>
          <DialogDescription>
            {description ||
              "This action cannot be undone. Are you sure you want to permanently delete this from our servers?"}
          </DialogDescription>
        </DialogHeader>
        <DialogFooter>
          <DialogClose asChild>
            <Button variant="secondary" onClick={onCancel}>
              Cancel
            </Button>
          </DialogClose>
          <DialogClose asChild={onConfirmClose}>
            <Button onClick={onConfirm} variant="destructive">
              {confirmText || "Yes Proceed"}
            </Button>
          </DialogClose>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
}

export default ConfirmDialog;
