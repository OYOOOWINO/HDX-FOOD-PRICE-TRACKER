"use client";

import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import PriceDashboard from ".";
export default function Dashboard() {
	const queryClient = new QueryClient();

	return (
		<QueryClientProvider client={queryClient}>
			<PriceDashboard />
		</QueryClientProvider>
	);
}
