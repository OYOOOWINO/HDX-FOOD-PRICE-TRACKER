import {
	ChartArea,
} from "lucide-react";

export const menu = [
	{
		title: "Prices",
		url: "#",
		icon: ChartArea,
		isActive: true,
		items: [
			{
				title: "Commodity Prices",
				url: "/dashboard/prices",
			},

		],
	},
	{
		title: "Trend",
		url: "#",
		icon: ChartArea,
		isActive: true,
		items: [
			{
				title: "Commodities",
				url: "/dashboard/trends/commodity",
			},
			{
				title: "Markets",
				url: "/dashboard/trends/market",
			},
			{
				title: "Categories",
				url: "/dashboard/trends/category",
			},
		],
	},
];
