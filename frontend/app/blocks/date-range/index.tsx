"use client";

import * as React from "react";
import { addDays, format, startOfMonth, startOfDay } from "date-fns";
import { ArrowBigRight, CalendarIcon } from "lucide-react";

import type { DateRange } from "react-day-picker";
import { Button } from "@/components/ui/button";
import {
	Popover,
	PopoverContent,
	PopoverTrigger,
} from "@/components/ui/popover";
import { cn } from "@/lib/utils";
import { Calendar } from "@/components/ui/calendar";
export function DateRangePicker({
	className,
	getDates,
}: {
	className?: React.HTMLAttributes<HTMLDivElement>;
	getDates: (from: Date, to: Date) => void;
}) {
	const [open, setOpen] = React.useState<boolean>(false);
	const [date, setDate] = React.useState<DateRange | undefined>({
		from: startOfMonth(Date.now()),
		to: startOfDay(Date.now()),
	});

	
	// useEffect(() => {
	// 	getDates(date?.from as Date, date?.to as Date);
	// }, [date]);

	return (
		<div className={cn("grid", className)}>
			<Popover open={open} onOpenChange={setOpen}>
				<PopoverTrigger asChild>
					<Button
						id="date"
						variant={"outline"}
						className={cn(
							"w-fit justify-start text-left font-normal",
							!date && "text-muted-foreground",
						)}
						onClick={() => {
							setOpen(true);
						}}
					>
						<CalendarIcon />
						{date?.from ? (
							date.to ? (
								<>
									{format(date.from, "LLL dd, y")} <ArrowBigRight />
									{format(date.to, "LLL dd, y")}
								</>
							) : (
								format(date.from, "LLL dd, y")
							)
						) : (
							<span>Pick a date</span>
						)}
					</Button>
				</PopoverTrigger>
				<PopoverContent className="w-auto p-0" align="start">
					<div className="grid grid-cols-1 grid-rows-2 gap-3 p-4">
						<Calendar
							className="col-span-1"
							initialFocus
							mode="range"
							defaultMonth={date?.from}
							selected={date}
							onSelect={setDate}
							numberOfMonths={2}
						/>
						<div className="col-span-1 justify-end flex">
							<Button
								onClick={() => {
									getDates(date?.from as Date, date?.to as Date);
									setOpen(false);
								}}
							>
								Apply
							</Button>
						</div>
					</div>
				</PopoverContent>
			</Popover>
		</div>
	);
}
