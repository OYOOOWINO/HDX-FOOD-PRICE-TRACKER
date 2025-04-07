"use client";


import { columns } from "./columns";
import { DataTable } from "./data-table";
import { useQuery } from "@tanstack/react-query";
import { useQueryState } from "nuqs";
import type { CategoryResponce } from "@/app/types";
import { getCategories } from "@/lib";
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

	const { data: categories, isLoading } = useQuery({
		queryKey: ["categories", page, size],
		queryFn: async () =>
			(await getCategories( page, size )) as CategoryResponce,
		staleTime: Number.POSITIVE_INFINITY,
	});

	return (
		<div className="flex flex-1 flex-col h-[calc(100vh-100px)]">
			{" "}
			{/* adjust 100px as needed */}
			<div className="@container/main flex flex-1 flex-col gap-2 overflow-hidden">
				<div className="flex flex-col gap-4 py-4 md:gap-6 md:py-6 flex-1 overflow-hidden">
					<DataTable
						isLoading={isLoading}
						data={categories?.content ?? []}
						columns={columns}
						pageIndex={categories?.number ?? 0}
						pageSize={categories?.size ?? 0}
						totalPages={categories?.totalPages ?? 0}
						setSize={setSize}
						setPage={setPage}
					/>
				</div>
			</div>
		</div>
	);
}
