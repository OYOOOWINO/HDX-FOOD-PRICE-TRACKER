"use client";

import * as React from "react";
import {
	Bar,
	BarChart,
	CartesianGrid,
	Line,
	LineChart,
	XAxis,
	AreaChart,
	Area,
} from "recharts";

import {
	Card,
	CardContent,
	CardDescription,
	CardHeader,
	CardTitle,
} from "@/components/ui/card";
import {
	type ChartConfig,
	ChartContainer,
	ChartLegend,
	ChartLegendContent,
	ChartTooltip,
	ChartTooltipContent,
} from "@/components/ui/chart";
import type { PriceTrend } from "@/app/types";

const chartConfig = {
	avgPriceKES: {
		label: "KES",
		color: "#2563eb",
	},
	avgPriceUSD: {
		label: "USD",
		color: "#60a5fa",
	},
} satisfies ChartConfig;

export function TrendChart({
	chartData,
}: {
	chartData: PriceTrend[];
}) {
	return (
		
			<ChartContainer config={chartConfig} className="min-h-[200px] w-full">
				<AreaChart accessibilityLayer data={chartData}>
					<CartesianGrid vertical={false} />
					<XAxis
						dataKey="date"
						tickLine={false}
						tickMargin={10}
						axisLine={false}
						tickFormatter={(value) => {
							const date = new Date(value);
							return date.toLocaleDateString("en-US", {
								month: "short",
								day: "numeric",
							});
						}}
					/>
					<ChartTooltip content={<ChartTooltipContent />} />
					<ChartLegend content={<ChartLegendContent />} />

					<Area
						dataKey="avgPriceKES"
						stroke="var(--color-avgPriceKES)"
						strokeWidth={2}
						type="linear"
						dot={false}
					/>
					<Area
						dataKey="avgPriceUSD"
						stroke="var(--color-avgPriceUSD)"
						strokeWidth={2}
						type="linear"
						dot={false}
					/>
				</AreaChart>
			</ChartContainer>
		
	);
}
