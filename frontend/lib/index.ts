import type { CategoryResponce, CommodityObject, CommodityResponce, MarketResponce } from "@/app/types";

export async function getCategories(page = 0, size = 10) {
	const response = await fetch(`http://localhost:8080/api/categories?page=${page}&size=${size}`, {
		headers: {},
		method: "GET",
	});
	return response.json() as unknown as CategoryResponce
}

export async function getCommodities(page = 0, size = 100) {
	const response = await fetch(`http://localhost:8080/api/commodities?page=${page}&size=${size}`, {
		headers: {},
		method: "GET",
	});
	return response.json() as unknown as CommodityResponce

}

export async function getCommodityPrices({
	page,
	size,
	market,
	commodity,
	category,
}: {
	page: number;
	size: number;
	market: string | null | undefined;
	commodity: string | null | undefined;
	category: string | null | undefined;
}) {
	// Initialize an array to hold query parameters
	const queryParams: string[] = [];

	// Check each parameter and append it to the query string if it's not null, undefined, or empty
	if (page !== undefined && page !== null) queryParams.push(`page=${page}`);
	if (size !== undefined && size !== null) queryParams.push(`size=${size}`);
	if (market) queryParams.push(`market=${market}`);
	if (commodity) queryParams.push(`commodity=${commodity}`);
	if (category) queryParams.push(`category=${category}`);

	// Build the query string
	const queryString = queryParams.length > 0 ? `?${queryParams.join('&')}` : '';

	// Make the fetch request with the dynamically constructed query string
	const response = await fetch(
		`http://localhost:8080/api/prices/filter${queryString}`,
		{
			headers: {},
			method: 'GET',
		}
	);

	return await response.json();
}


export async function getCommodityMarkets(
	page = 0, size = 10
) {
	const response = await fetch(`http://localhost:8080/api/markets?page=${page}&size=${size}`, {
		headers: {},
		method: "GET",
	});
	return response.json() as unknown as MarketResponce
}

export async function getPriceTrend({
	commodity,
	from, to,
	market
}: {
	commodity: string | null | undefined;
	from: string | null | undefined;
	to: string | null | undefined;
	market: string | null | undefined;
}) {
	// Initialize an array to hold query parameters
	const queryParams: string[] = [];

	// Check each parameter and append it to the query string if it's not null, undefined, or empty
	if (commodity) queryParams.push(`commodity=${commodity}`);
	if (from) queryParams.push(`from=${from}`);
	if (to) queryParams.push(`to=${to}`);
	if (market) queryParams.push(`market=${market}`);
	// Build the query string
	const queryString = queryParams.length > 0 ? `?${queryParams.join('&')}` : '';

	// Make the fetch request with the dynamically constructed query string
	const response = await fetch(
		`http://localhost:8080/api/prices/trend${queryString}`,
		{
			headers: {},
			method: 'GET',
		}
	);

	return await response.json();
}