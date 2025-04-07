"use client";

import { useQueries, useQuery } from "@tanstack/react-query";
import type { MarketObject, PriceTrend } from "@/app/types";
import { useParams } from "next/navigation";
import { TrendChart } from "./chart";
import {
	Check,
	ChevronsUpDown,
	Equal,
	FilterIcon,
	ListFilter,
	LoaderIcon,
	SeparatorVertical,
} from "lucide-react";
import {
	getCategories,
	getCommodities,
	getCommodityMarkets,
	getPriceTrend,
} from "@/lib";
import { useQueryState } from "nuqs";
import { DateRangePicker } from "@/app/blocks/date-range";
import {
	Command,
	CommandEmpty,
	CommandGroup,
	CommandInput,
	CommandItem,
	CommandList,
} from "@/components/ui/command";
import {
	Popover,
	PopoverContent,
	PopoverTrigger,
} from "@/components/ui/popover";
import { Button } from "@/components/ui/button";
import { useState } from "react";
import { cn } from "@/lib/utils";
import { Separator } from "@radix-ui/react-separator";

export default function PriceDashboard() {
	const [markets] = useQueries({
		queries: [
			{
				queryKey: ["markets"],
				staleTime: Number.POSITIVE_INFINITY,
				queryFn: async () => {
					return await getCommodityMarkets();
				},
			},
		],
	});
	const { id: commodity } = useParams<{ id: string }>();
	const [open, setOpen] = useState(false);
	const [marketObject, setMarketObject] = useState<
		MarketObject | null | undefined
	>();

	const [market, setMarket] = useQueryState("market", {
		defaultValue: "",
		history: "replace",
		throttleMs: 500,
		clearOnDefault: false,
	});

	const [from, setFrom] = useQueryState("from", {
		defaultValue: new Date("2000-1-1").toISOString().split("T")[0],
		history: "replace",
		throttleMs: 500,
		clearOnDefault: false,
	});
	const [to, setTo] = useQueryState("to", {
		defaultValue: new Date(Date.now()).toISOString().split("T")[0],
		history: "replace",
		throttleMs: 500,
		clearOnDefault: false,
	});

	console.log(commodity);
	const { data: trend, isLoading } = useQuery({
		queryKey: ["trend", commodity, to, from, market],
		queryFn: async () =>
			((await getPriceTrend({ commodity, from, to, market })) as PriceTrend[]) ?? [],
	});

	function getDates(fromDate: Date, toDate: Date) {
		console.log({ fromDate, toDate });
		setFrom(fromDate.toISOString().split("T")[0]);
		setTo(toDate.toISOString().split("T")[0]);
	}

	console.log({ trend });

	return (
		<div className="grid grid-rows-[auto_1fr_auto] h-[700px] border rounded-md overflow-hidden">
			<div className="flex justify-between bg-white p-2 border-b z-20 sticky top-0">
				<div className="flex">
					<div className="pl-2 ml-2">
						<DateRangePicker getDates={getDates} />
					</div>
					<div className="flex-grow">
						<Popover open={open} onOpenChange={setOpen}>
							<PopoverTrigger asChild>
								<Button variant="outline">
									<FilterIcon /> Market <Equal />{" "}
									{marketObject?.name ?? "All Markets"}
								</Button>
							</PopoverTrigger>

							<PopoverContent className="w-[200px] p-0">
								<Command>
									<CommandInput
										placeholder="Search Market..."
										className="h-9"
									/>
									<CommandList>
										<CommandEmpty>"No Market found"</CommandEmpty>
										<CommandGroup>
											{markets.data?.content?.map((item) => (
												<CommandItem
													key={item.id}
													value={item.name}
													onSelect={(currentValue) => {
														setMarketObject({
															...item,
														});
														setMarket(item.id);
														setOpen(false);
													}}
												>
													{item.name}
													<Check
														className={cn(
															"ml-auto",
															setMarketObject.name === item.name
																? "opacity-100"
																: "opacity-0",
														)}
													/>
												</CommandItem>
											))}
										</CommandGroup>
									</CommandList>
								</Command>
							</PopoverContent>
						</Popover>
					</div>
				</div>
			</div>
			<div className="flex flex-col gap-4 py-4 md:gap-6 md:py-6 flex-1 overflow-hidden">
				{!isLoading && (trend ?? [])?.length > 0 && (
					<TrendChart chartData={trend ?? []} />
				)}

				{isLoading && <LoaderIcon />}
			</div>
		</div>
	);
}
