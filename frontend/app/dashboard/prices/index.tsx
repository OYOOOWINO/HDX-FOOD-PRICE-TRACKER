"use client";

import { columns } from "./columns";
import { DataTable } from "./data-table";
import type { PriceResponse } from "../../types";
import { useQuery } from "@tanstack/react-query";
import { useQueryState } from "nuqs";
import { getCommodityPrices } from "@/lib";
export default function PriceDashboard() {
	const [size, setSize] = useQueryState("size", {
		defaultValue: 10,
		parse: (value) => Number.parseInt(value || "10"),
		serialize: (value) => value.toString(),
		history: "replace",
		throttleMs: 500,
		clearOnDefault: false,
	});
	const [page, setPage] = useQueryState("page", {
		defaultValue: 0,
		parse: (value) => Number.parseInt(value || "0"),
		serialize: (value) => value.toString(),
		history: "replace",
		throttleMs: 500,
		clearOnDefault: false,
	});
	const [category, setCategory] = useQueryState("category", {
		defaultValue: "",
		history: "replace",
		throttleMs: 500,
		clearOnDefault: false,
	});

	const [market, setMarket] = useQueryState("market", {
		defaultValue: "",
		history: "replace",
		throttleMs: 500,
		clearOnDefault: false,
	});

	const [commodity, setCommodity] = useQueryState("commodity", {
		defaultValue: "",
		history: "replace",
		throttleMs: 500,
		clearOnDefault: false,
	});

	const { data: prices, isLoading } = useQuery({
		queryKey: ["prices", page, size],
		queryFn: async () =>
			(await getCommodityPrices({
				page,
				size,
				market,
				commodity,
				category,
			})) as PriceResponse,
		staleTime: Number.POSITIVE_INFINITY,
	});

	function setFilters(
		market: string | null | undefined,
		commodity: string | null | undefined,
		category: string | null | undefined,
	) {
		if (category) setCategory(category);
		if (market) setMarket(market);
		if (commodity) setCommodity(commodity);
	}
	return (
		<div className="flex flex-1 flex-col h-[calc(100vh-100px)]">
			{" "}
			{/* adjust 100px as needed */}
			<div className="@container/main flex flex-1 flex-col gap-2 overflow-hidden">
				<div className="flex flex-col gap-4 py-4 md:gap-6 md:py-6 flex-1 overflow-hidden">
					<DataTable
						isLoading={isLoading}
						data={prices?.content ?? []}
						columns={columns}
						pageIndex={prices?.number ?? 0}
						pageSize={prices?.size ?? 0}
						totalPages={prices?.totalPages ?? 0}
						setSize={setSize}
						setPage={setPage}
						setFilters={setFilters}
					/>
				</div>
			</div>
		</div>
	);
}
