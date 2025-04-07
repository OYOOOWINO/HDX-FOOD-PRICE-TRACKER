"use client";

import { Button } from "@/components/ui/button";
import { DropdownMenu, DropdownMenuCheckboxItem, DropdownMenuContent, DropdownMenuTrigger } from "@/components/ui/dropdown-menu";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Skeleton } from "@/components/ui/skeleton";
import { TableHeader,Table, TableRow, TableHead, TableBody, TableCell } from "@/components/ui/table";
import { Select, SelectTrigger, SelectValue, SelectContent, SelectItem } from "@radix-ui/react-select";
import {
	type SortingState,
	getSortedRowModel,
	type ColumnDef,
	useReactTable,
	getCoreRowModel,
	flexRender,
	getPaginationRowModel,
	type ColumnFiltersState,
	getFilteredRowModel,
	type VisibilityState,
} from "@tanstack/react-table";

import {
	ChevronsLeftIcon,
	ChevronLeftIcon,
	ChevronRightIcon,
	ChevronsRightIcon,
	FilterIcon,
	Columns3
} from "lucide-react";

import { useState } from "react";

interface DataTableProps<TData, TValue> {
	columns: ColumnDef<TData, TValue>[];
	data: TData[];
	totalPages: number;
	pageIndex: number;
	pageSize: number;
	isLoading: boolean;
	setSize: (size: number) => void;
	setPage: (size: number) => void;
}


export function DataTable<TData, TValue>({
	columns,
	data,
	totalPages,
	pageIndex,
	pageSize,
	isLoading,
	setSize,
	setPage,
}: DataTableProps<TData, TValue>) {
	const [sorting, setSorting] = useState<SortingState>([]);
	const [columnFilters, setColumnFilters] = useState<ColumnFiltersState>([]);
	const [rowSelection, setRowSelection] = useState({});
	const [columnVisibility, setColumnVisibility] = useState<VisibilityState>({});

	const table = useReactTable({
		data,
		columns,
		getCoreRowModel: getCoreRowModel(),
		getPaginationRowModel: getPaginationRowModel(),
		onSortingChange: setSorting,
		getSortedRowModel: getSortedRowModel(),
		onColumnVisibilityChange: setColumnVisibility,
		onColumnFiltersChange: setColumnFilters,
		getFilteredRowModel: getFilteredRowModel(),
		onRowSelectionChange: setRowSelection,
		manualPagination: true,
		pageCount: totalPages,
		state: {
			sorting,
			columnFilters,
			columnVisibility,
			rowSelection,
			pagination: {
				pageIndex,
				pageSize,
			},
		},
	});

	return (
		<div className="grid grid-rows-[auto_1fr_auto] h-[700px] border rounded-md overflow-hidden">
			<div className="flex justify-between bg-white p-2 border-b z-20 sticky top-0">
				<div className="flex px-2 min-w-20">
					<Button
						variant="outline"
						className="ml-auto border-r-0 rounded-r-none"
					>
						<FilterIcon />
						Market
					</Button>
					<Input
						placeholder={"Search Market..."}
						value={
							(table
								.getColumn("name")
								?.getFilterValue() as string) ?? ""
						}
						onChange={(event) =>
							table
								.getColumn("name")
								?.setFilterValue(event.target.value)
						}
						className="max-w-44 min-w-44 border-l-0 rounded-l-none"
					/>
				</div>
				<div className="flex">
					<DropdownMenu>
						<DropdownMenuTrigger asChild>
							<Button variant="outline" className="ml-auto">
								<Columns3 /> Columns
							</Button>
						</DropdownMenuTrigger>
						<DropdownMenuContent align="end">
							{table
								.getAllColumns()
								.filter((column) => column.getCanHide())
								.map((column) => {
									return (
										<DropdownMenuCheckboxItem
											key={column.id}
											className="capitalize"
											checked={column.getIsVisible()}
											onCheckedChange={(value) =>
												column.toggleVisibility(!!value)
											}
										>
											{column.id.replace("_", " ").replace("name", "")}
										</DropdownMenuCheckboxItem>
									);
								})}
						</DropdownMenuContent>
					</DropdownMenu>
				</div>
			</div>
			<div className="overflow-y-auto">
				<Table>
					<TableHeader className="sticky top-0 z-20 bg-muted">
						{table.getHeaderGroups().map((headerGroup) => (
							<TableRow key={headerGroup.id}>
								{headerGroup.headers.map((header) => {
									return (
										<TableHead key={header.id}>
											{header.isPlaceholder
												? null
												: flexRender(
														header.column.columnDef.header,
														header.getContext(),
													)}
										</TableHead>
									);
								})}
							</TableRow>
						))}
					</TableHeader>
					<TableBody>
						{isLoading ? (
							<>
								{Array.from({ length: pageSize + 1 }).map((_, idx) => (
									// biome-ignore lint/suspicious/noArrayIndexKey: <explanation>
									<TableRow key={idx}>
										{Array.from({ length: columns.length }).map((_, index) => (
											// biome-ignore lint/suspicious/noArrayIndexKey: <explanation>
											<TableCell key={index}>
												<Skeleton className="h-6 w-full" />
											</TableCell>
										))}
									</TableRow>
								))}
							</>
						) : (
							<>
								{table.getRowModel().rows?.length ? (
									table.getRowModel().rows.map((row) => (
										<TableRow
											key={row.id}
											data-state={row.getIsSelected() && "selected"}
										>
											{row.getVisibleCells().map((cell) => (
												<TableCell key={cell.id}>
													{flexRender(
														cell.column.columnDef.cell,
														cell.getContext(),
													)}
												</TableCell>
											))}
										</TableRow>
									))
								) : (
									<TableRow>
										<TableCell
											colSpan={columns.length}
											className="h-24 text-center"
										>
											No results.
										</TableCell>
									</TableRow>
								)}
							</>
						)}
					</TableBody>
				</Table>
			</div>
			<div className="flex justify-end bg-white p-2 border-t z-20 sticky bottom-0">
				<div className="flex-1 text-sm text-muted-foreground">
					{table.getFilteredSelectedRowModel().rows.length} of{" "}
					{table.getFilteredRowModel().rows.length} row(s) selected.
				</div>
				<div className="flex w-full items-center gap-8 lg:w-fit">
					<div className="hidden items-center gap-2 lg:flex">
						<Label className="text-sm font-medium">Rows per page</Label>
						<Select
							value={`${pageSize}`}
							onValueChange={(value) => {
								setSize(Number(value));
							}}
						>
							<SelectTrigger className="w-20" id="rows-per-page">
								<SelectValue
									placeholder={table.getState().pagination.pageSize}
								/>
							</SelectTrigger>
							<SelectContent side="top">
								{[10, 20, 30, 40, 50].map((s) => (
									<SelectItem key={s} value={`${s}`}>
										{s}
									</SelectItem>
								))}
							</SelectContent>
						</Select>
					</div>
					<div className="flex w-fit items-center justify-center text-sm font-medium">
						Page {table.getState().pagination.pageIndex} of{" "}
						{table.getPageCount()}
					</div>
					<div className="ml-auto flex items-center gap-2 lg:ml-0">
						<Button
							variant="outline"
							className="hidden h-8 w-8 p-0 lg:flex"
							disabled={!table.getCanPreviousPage()}
							onClick={() => setPage(0)}
						>
							<ChevronsLeftIcon />
						</Button>
						<Button
							variant="outline"
							className="size-8"
							size="icon"
							onClick={() => {
								setPage(pageIndex - 1);
							}}
							disabled={!table.getCanPreviousPage()}
						>
							<ChevronLeftIcon />
						</Button>
						<Button
							variant="outline"
							className="size-8"
							size="icon"
							onClick={() => {
								setPage(pageIndex + 1);
							}}
							disabled={!table.getCanNextPage()}
						>
							<ChevronRightIcon />
						</Button>
						<Button
							variant="outline"
							className="hidden size-8 lg:flex"
							size="icon"
							onClick={() => setPage(table.getPageCount() - 1)}
							disabled={!table.getCanNextPage()}
						>
							<ChevronsRightIcon />
						</Button>
					</div>
				</div>
			</div>
		</div>
	);
}
