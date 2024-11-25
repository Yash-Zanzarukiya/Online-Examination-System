import { FormField, FormItem, FormLabel, FormControl, FormMessage } from "@/components/ui/form";
import { Input } from "@/components/ui/input";

interface Props {
  control: any;
  name: string;
  label: string;
  placeholder: string;
  type?: string;
}

export const FormInput = ({
  control,
  name,
  label,
  placeholder,
  type = "text",
  ...restProps
}: Props) => (
  <FormField
    control={control}
    name={name}
    render={({ field }) => (
      <FormItem>
        <FormLabel>{label}</FormLabel>
        <FormControl>
          <Input {...field} placeholder={placeholder} type={type} {...restProps} />
        </FormControl>
        <FormMessage />
      </FormItem>
    )}
  />
);
