"use client";

import type { ColumnDef } from "@tanstack/react-table";

import { ArrowUpDown, Copy, MoreHorizontal } from "lucide-react";
import { format } from "date-fns";
import type { PriceObject } from "@/app/types";
import { Checkbox } from "@/components/ui/checkbox";
import { Button } from "@/components/ui/button";
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuLabel, DropdownMenuSeparator, DropdownMenuTrigger } from "@/components/ui/dropdown-menu";
import { Badge } from "@/components/ui/badge";

export interface CommodityPrice {
	id: string;
	createdAt: string;
	updatedat: string;
	unit: string;
	price: number;
	usdprice: number;
	pricetype: string;
	priceflag: string;
	category_name: string;
	commodity_name: string;
	market_name: string;
}

export const columns: ColumnDef<PriceObject>[] = [
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
		accessorKey: "commodity_name",
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
			return (
				<span className="capitalize">{row.getValue("commodity_name")}</span>
			);
		},
	},
	{
		accessorKey: "category_name",
		header: ({ column }) => {
			return (
				<Button
					variant="ghost"
					onClick={() => column.toggleSorting(column.getIsSorted() === "asc")}
				>
					<div className="capitalize">Category</div>
					<ArrowUpDown className="ml-2 h-4 w-4" />
				</Button>
			);
		},

		cell: ({ row }) => {
			const name: string = row.getValue("category_name");
			const color: string = stringToColour(name);
			return (
				<Badge
					variant="outline"
					className="lowercase text-sm font-light rounded-full"
					style={{ backgroundColor: color, color: "#fff" }}
				>
					{name}
				</Badge>
			);
		},
	},
	{
		accessorKey: "market_name",
		header: ({ column }) => {
			return (
				<Button
					variant="ghost"
					onClick={() => column.toggleSorting(column.getIsSorted() === "asc")}
				>
					<div className="capitalize">Market</div>
					<ArrowUpDown className="ml-2 h-4 w-4" />
				</Button>
			);
		},

		cell: ({ row }) => {
			const name: string = row.getValue("market_name");
			return (
				<span className="capitalize px-2 py-1 text-sm font-light rounded-full">
					{name}
				</span>
			);
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
		accessorKey: "priceflag",
		header: () => <div className="capitalize"> PriceFlag</div>,
		cell: ({ row }) => {
			return <span className="lowercase">{row.getValue("priceflag")}</span>;
		},
	},
	{
		accessorKey: "pricetype",
		header: () => <div className="capitalize"> Price Type</div>,
		cell: ({ row }) => {
			const name: string = row.getValue("pricetype");
			const color: string = stringToColour(name);
			return (
				<Badge
					variant="outline"
					className="lowercase text-sm font-light rounded-full"
					style={{ backgroundColor: color, color: "#fff" }}
				>
					{name}
				</Badge>
			);
		},
	},
	{
		accessorKey: "price",
		header: () => <div className="capitalize">PRICE</div>,
		cell: ({ row }) => {
			const amount = Number.parseFloat(row.getValue("price"));
			const formatted = new Intl.NumberFormat("en-US", {
				style: "currency",
				currency: "KES",
			}).format(amount);
			return <div className="font-medium">{formatted}</div>;
		},
	},
	{
		accessorKey: "usdprice",
		header: () => <div className="capitalize"> USD PRICE</div>,
		cell: ({ row }) => {
			const amount = Number.parseFloat(row.getValue("usdprice"));
			const formatted = new Intl.NumberFormat("en-US", {
				style: "currency",
				currency: "USD",
			}).format(amount);
			return <div className="font-medium">{formatted}</div>;
		},
	},

	{
		header: () => <div className="capitalize"> actions</div>,
		id: "actions",
		cell: ({ row }) => {
			const price = row.original;
			return (
				<DropdownMenu>
					<DropdownMenuTrigger>
						<Button variant="ghost" className="h-8 w-8">
							<span className="sr-only">Open menu</span>
							<MoreHorizontal className="h-4 w-4" />
						</Button>
					</DropdownMenuTrigger>
					<DropdownMenuContent align="end">
						<DropdownMenuLabel>Actions</DropdownMenuLabel>
						<DropdownMenuItem
							onClick={() =>
								navigator.clipboard.writeText(JSON.stringify(price))
							}
						>
							Copy
						</DropdownMenuItem>
						<DropdownMenuSeparator />
					</DropdownMenuContent>
				</DropdownMenu>
			);
		},
	},
];

function stringToColour(str: string) {
	const hash = [...str].reduce(
		(acc, char) => char.charCodeAt(0) + ((acc << 5) - acc),
		0,
	);

	return `#${[0, 1, 2]
		.map((i) => (((hash >> (i * 8)) & 0xff) % 106) + 150) // Keep colors in the light range
		.map((value) => value.toString(16).padStart(2, "0"))
		.join("")}`;
}
