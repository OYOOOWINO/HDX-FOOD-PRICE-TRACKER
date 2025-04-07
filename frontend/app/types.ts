export interface CategoryObject {
    id: string;
    name: string
}

export interface MarketObject {
    id: string;
    name: string;
    lat: number;
    lng: number;
}

export interface CommodityObject {
    id: string;
    name: string
    unit: string
}

export interface CategoryResponce {
    content: CategoryObject[];
    pageable: Pageable;
    totalPages: number;
    totalElements: number;
    last: boolean;
    numberOfElements: number;
    first: boolean;
    size: number;
    number: number;
    sort: Sort;
    empty: boolean;
}

export interface MarketResponce {
    content: MarketObject[];
    pageable: Pageable;
    totalPages: number;
    totalElements: number;
    last: boolean;
    numberOfElements: number;
    first: boolean;
    size: number;
    number: number;
    sort: Sort;
    empty: boolean;
}

export interface CommodityResponce {
    content: CommodityObject[];
    pageable: Pageable;
    totalPages: number;
    totalElements: number;
    last: boolean;
    numberOfElements: number;
    first: boolean;
    size: number;
    number: number;
    sort: Sort;
    empty: boolean;
}

export interface PriceResponse {
    content: PriceObject[];
    pageable: Pageable;
    totalPages: number;
    totalElements: number;
    last: boolean;
    numberOfElements: number;
    first: boolean;
    size: number;
    number: number;
    sort: Sort;
    empty: boolean;
}

export interface PriceTrend {
    date: string;
    avgPriceKES: number;
    avgPriceUSD: number;
}

export interface PriceObject {
    id: string;
    createdAt: Date;
    updatedAt: Date;
    unit: Unit;
    price: number;
    usdprice: number;
    pricetype: Pricetype;
    priceflag: Priceflag;
    commodity_name: string;
    category_name: string;
    market_name: string;
}



export enum Priceflag {
    Actual = "ACTUAL",
}

export enum Pricetype {
    Retail = "RETAIL",
    Wholesale = "WHOLESALE",
}

export enum Unit {
    Kg = "KG",
    The90Kg = "90 KG",
}

export interface Pageable {
    pageNumber: number;
    pageSize: number;
    sort: Sort;
    offset: number;
    paged: boolean;
    unpaged: boolean;
}

export interface Sort {
    sorted: boolean;
    unsorted: boolean;
    empty: boolean;
}
