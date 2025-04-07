"use client";

import type { ColumnDef } from "@tanstack/react-table";

import { ArrowUpDown, ChartArea, MoreHorizontal } from "lucide-react";
import { format } from "date-fns";
import type { CommodityObject } from "@/app/types";
import { Checkbox } from "@/components/ui/checkbox";
import { Button } from "@/components/ui/button";

export const columns: ColumnDef<CommodityObject>[] = [
	{
		id: "select",
		header: ({ table }) => (
			<Checkbox
				checked={
					table.getIsAllPageRowsSelected() ||
					(table.getIsSomePageRowsSelected() && "indeterminate")
				}
				onCheckedChange={(value) => table.toggleAllPageRowsSelected(!!value)}
				aria-label="Select all"
			/>
		),
		cell: ({ row }) => (
			<Checkbox
				checked={row.getIsSelected()}
				onCheckedChange={(value) => row.toggleSelected(!!value)}
				aria-label="Select row"
			/>
		),
		enableSorting: false,
		enableHiding: false,
	},

	{
		accessorKey: "createdAt",
		header: ({ column }) => {
			return (
				<Button
					variant="ghost"
					onClick={() => column.toggleSorting(column.getIsSorted() === "asc")}
				>
					<div className="capitalize">Date</div>
					<ArrowUpDown className="ml-2 h-4 w-4" />
				</Button>
			);
		},
		cell: ({ row }) => {
			const date = new Date(row.getValue("createdAt")).toISOString();
			const dt = format(date, "yyyy-MM-dd");
			return <span className="capitalize">{dt}</span>;
		},
	},
	{
		accessorKey: "name",
		header: ({ column }) => {
			return (
				<Button
					variant="ghost"
					onClick={() => column.toggleSorting(column.getIsSorted() === "asc")}
				>
					<div className="capitalize">Commodity</div>
					<ArrowUpDown className="ml-2 h-4 w-4" />
				</Button>
			);
		},
		cell: ({ row }) => {
			return <span className="capitalize">{row.getValue("name")}</span>;
		},
	},
	{
		accessorKey: "unit",
		header: () => <div className="capitalize"> Unit</div>,
		cell: ({ row }) => {
			return <span className="lowercase">{row.getValue("unit")}</span>;
		},
	},
	{
		header: () => <div className="capitalize"> Trend</div>,
		id: "trend",
		cell: ({ row }) => {
			const id = row.original.id;
			return (
				<Button variant={"ghost"}>
					<ChartArea /> Trend
				</Button>
			);
		},
	},
];
