"use client"

import { Skeleton } from "@/components/ui/skeleton";
import { TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { Table } from "lucide-react";

export function TableSkeleton() {
	return (
		<div className="w-full">
			{/* Table filters/actions skeleton */}
			<div className="mb-4 flex items-center justify-between">
				<Skeleton className="h-10 w-[250px]" />
				<div className="flex space-x-2">
					<Skeleton className="h-10 w-[100px]" />
					<Skeleton className="h-10 w-[100px]" />
				</div>
			</div>

			{/* Table skeleton */}
			<div className="rounded-md border">
				<Table>
					<TableHeader>
						<TableRow>
							{Array.from({ length: 9 }).map((_, index) => (
								<TableHead
									key={`header-${
										// biome-ignore lint/suspicious/noArrayIndexKey: <explanation>
										index
									}`}
								>
									<Skeleton className="h-5 w-full max-w-[100px]" />
								</TableHead>
							))}
						</TableRow>
					</TableHeader>
					<TableBody>
						{Array.from({ length: 10 }).map((_, rowIndex) => (
							<TableRow
								key={`row-${
									// biome-ignore lint/suspicious/noArrayIndexKey: <explanation>
									rowIndex
								}`}
							>
								{Array.from({ length: 9 }).map((_, colIndex) => (
									<TableCell
										key={`cell-${rowIndex}-${
											// biome-ignore lint/suspicious/noArrayIndexKey: <explanation>
											colIndex
										}`}
									>
										<Skeleton className="h-4 w-full" />
									</TableCell>
								))}
							</TableRow>
						))}
					</TableBody>
				</Table>
			</div>

			{/* Pagination skeleton */}
			<div className="mt-4 flex items-center justify-between">
				<Skeleton className="h-8 w-[150px]" />
				<div className="flex items-center space-x-2">
					<Skeleton className="h-8 w-8 rounded-md" />
					<Skeleton className="h-8 w-8 rounded-md" />
					<Skeleton className="h-8 w-8 rounded-md" />
				</div>
			</div>
		</div>
	);
}
