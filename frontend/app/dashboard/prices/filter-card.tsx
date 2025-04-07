"use client";

import { useState } from "react";
import {
	Plus,
	Equal,
	ListFilter,
	Check,
	ChevronsUpDown,
	DeleteIcon,
	SaveIcon,
} from "lucide-react";
import { useQueries } from "@tanstack/react-query";

import { usePathname, useRouter, useSearchParams } from "next/navigation";
import { getCategories, getCommodities, getCommodityMarkets } from "@/lib";
import { Button } from "@/components/ui/button";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Popover, PopoverContent, PopoverTrigger } from "@/components/ui/popover";
import { Command, CommandEmpty, CommandGroup, CommandInput, CommandItem, CommandList } from "@/components/ui/command";
import { cn } from "@/lib/utils";

export interface FilterObject {
	name: string;
	key: string;
	value: string;
}
export default function FilterUI({
	initFilters
}:{
	initFilters: (
		market: string | null | undefined,
		commodity: string | null | undefined,
		category: string | null | undefined,
	) => void,}
) {
	const pathname = usePathname();
	const router = useRouter();
	const searchParams = useSearchParams();
	const [filter, setFilter] = useState<FilterObject | null>({
		name: "category",
	} as FilterObject);

	const [open, setOpen] = useState(false);

	const [filters, setFilters] = useState<FilterObject[]>([]);

	const [categories, markets, commodities] = useQueries({
		queries: [
			{
				queryKey: ["categories"],
				staleTime: Number.POSITIVE_INFINITY,
				queryFn: async ()=>{ return  await getCategories()},
			},
			{
				queryKey: ["markets"],
				staleTime: Number.POSITIVE_INFINITY,
				queryFn: async ()=>{ return  await getCommodityMarkets()},
			},
			{
				queryKey: ["commodities"],
				staleTime: Number.POSITIVE_INFINITY,
				queryFn: async ()=>{ return  await getCommodities()},
			},
		],
	});

	const addFilter = () => {
		setFilter({
			name: "category",
		} as FilterObject);
	};

	const applyFilters = () => {
		const params = new URLSearchParams(searchParams);
		// biome-ignore lint/complexity/noForEach: <explanation>
		filters.forEach((element) => {
			params.set(element.name, element.key);
		});
		router.replace(`${pathname}?${params.toString()}`);
	};

	const updateFilter = (filter: FilterObject) => {
		setFilters((prevFilters) => {
			const exists = prevFilters.some((f) => f.name === filter.name);

			if (exists) {
				return prevFilters.map((f) =>
					f.name === filter.name ? { ...f, ...filter } : f,
				);
			}

			return [...prevFilters, filter];
		});
	};

	const clearFilters = () => {
		setFilters([]);
	};

	return (
		<div className="w-full mx-auto rounded-node shadow-none border">
			<div className="p-4 border-b">
				<h2 className="text-sm  text-gray-800">In this view show records</h2>
			</div>
			{filters.length > 0 && (
				<div className="px-4 py-2 border-b">
					{filters.map((item) => {
						return (
							<div
								key={item.key}
								className="grid grid-cols-5 gap-2 mb-4 items-center"
							>
								<div className="text-sm">Where</div>
								<div className="border px-2 py-1 truncate rounded-md">
									{item.name}
								</div>

								<div className="flex justify-center rounded-md">
									<Equal />
								</div>

								<div className="border px-2 py-1 truncate rounded-md">
									{item.value as string}
								</div>

								<div className="flex justify-end">
									<Button variant="outline">
										<DeleteIcon />
									</Button>
								</div>
							</div>
						);
					})}
				</div>
			)}

			<div className="px-4 py-2">
				{filter && (
					<div className="flex items-center gap-2 mb-4">
						<div className="text-sm">Where</div>
						<div className="relative flex-shrink-0 w-[200px]">
							<Select
								value={filter?.name}
								onValueChange={(value) => {
									setFilter({
										name: value,
									} as FilterObject);
								}}
							>
								<SelectTrigger className="w-full  h-10 px-3 flex items-center gap-2">
									<ListFilter />
									<SelectValue placeholder="Select Parameter" />
								</SelectTrigger>
								<SelectContent>
									<SelectItem value="category">Category</SelectItem>
									<SelectItem value="market">Market</SelectItem>
									<SelectItem value="commodity">Commodity</SelectItem>
								</SelectContent>
							</Select>
						</div>

						<div className="relative justify-center flex-shrink-0 w-fit">
							<Equal />
						</div>
						<div className="flex-grow">
							<Popover open={open} onOpenChange={setOpen}>
								<PopoverTrigger asChild>
									<Button
										variant="outline"
										// biome-ignore lint/a11y/useSemanticElements: <explanation>
										role="combobox"
										aria-expanded={open}
										className="w-[200px] justify-between"
									>
										{(filter?.value as string) ?? `Select ${filter?.name}...`}
										<ChevronsUpDown className="opacity-50" />
									</Button>
								</PopoverTrigger>

								<PopoverContent className="w-[200px] p-0">
									<Command>
										<CommandInput
											placeholder={`Search ${filter?.name}...`}
											className="h-9"
										/>
										<CommandList>
											<CommandEmpty>{`No ${filter?.name} found`}</CommandEmpty>
											{filter?.name === "category" && (
												<CommandGroup>
													{categories.data?.content?.map((item) => (
														<CommandItem
															key={item.id}
															value={item.name}
															onSelect={(currentValue) => {
																setFilter({
																	...filter,
																	value: item.name,
																	key: item.id,
																});
																setOpen(false);
															}}
														>
															{item.name}
															<Check
																className={cn(
																	"ml-auto",
																	filter.value === item.name
																		? "opacity-100"
																		: "opacity-0",
																)}
															/>
														</CommandItem>
													))}
												</CommandGroup>
											)}
											{filter?.name === "market" && (
												<CommandGroup>
													{markets.data?.content?.map((item) => (
														<CommandItem
															key={item.id}
															value={item.name}
															onSelect={(currentValue) => {
																setFilter({
																	...filter,
																	value: item.name,
																	key: item.id,
																});
																setOpen(false);
															}}
														>
															{item.name}
															<Check
																className={cn(
																	"ml-auto",
																	filter.value === item.name
																		? "opacity-100"
																		: "opacity-0",
																)}
															/>
														</CommandItem>
													))}
												</CommandGroup>
											)}
											{filter?.name === "commodity" && (
												<CommandGroup>
													{commodities.data?.content.map((item) => (
														<CommandItem
															key={item.id}
															value={item.name}
															onSelect={(currentValue) => {
																setFilter({
																	...filter,
																	value: item.name,
																	key: item.id,
																});
																setOpen(false);
															}}
														>
															{item.name}
															<Check
																className={cn(
																	"ml-auto",
																	filter.value === item.name
																		? "opacity-100"
																		: "opacity-0",
																)}
															/>
														</CommandItem>
													))}
												</CommandGroup>
											)}
										</CommandList>
									</Command>
								</PopoverContent>
							</Popover>
						</div>
						<div className="flex-grow">
							<Button
								disabled={
									!filter || !filter.key || !filter.name || !filter.value
								}
								onClick={() => {
									if (!filter) return;
									updateFilter(filter);
									setFilter(null);
								}}
								variant={"outline"}
							>
								<SaveIcon /> Add
							</Button>
						</div>
					</div>
				)}

				<div className="flex justify-between mt-4">
					<Button
						variant="ghost"
						className="flex items-center gap-1 px-2"
						onClick={addFilter}
					>
						<Plus className="h-5 w-5" />
						Add filter
					</Button>

					<Button variant="ghost" className="" onClick={clearFilters}>
						Clear all
					</Button>
					<Button className="" onClick={applyFilters}>
						Apply
					</Button>
				</div>
			</div>
		</div>
	);
}
