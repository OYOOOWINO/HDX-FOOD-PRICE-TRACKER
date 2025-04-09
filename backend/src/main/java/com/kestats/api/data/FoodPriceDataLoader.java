package com.kestats.api.data;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kestats.api.models.AdminLevel1;
import com.kestats.api.models.AdminLevel2;
import com.kestats.api.models.Category;
import com.kestats.api.models.Commodity;
import com.kestats.api.models.CommodityPrice;
import com.kestats.api.models.Market;
import com.kestats.api.models.Unit;
import com.kestats.api.repository.AdminLevel1Repository;
import com.kestats.api.repository.AdminLevel2Repository;
import com.kestats.api.repository.CategoryRepository;
import com.kestats.api.repository.CommodityPriceRepository;
import com.kestats.api.repository.CommodityRepository;
import com.kestats.api.repository.MarketRepository;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
public class FoodPriceDataLoader implements CommandLineRunner {
    @Autowired
    private final AdminLevel1Repository admin1Repository;
    private final AdminLevel2Repository admin2Repository;
    private final MarketRepository marketRepository;
    private final CategoryRepository categoryRepository;
    private final CommodityRepository commodityRepository;
    private final CommodityPriceRepository commodityPriceRepository;

    public FoodPriceDataLoader(AdminLevel1Repository admin1Repository,
            AdminLevel2Repository admin2Repository, MarketRepository marketRepository,
            CategoryRepository categoryRepository,
            CommodityRepository commodityRepository,
            CommodityPriceRepository commodityPriceRepository

    ) {
        this.admin1Repository = admin1Repository;
        this.admin2Repository = admin2Repository;
        this.categoryRepository = categoryRepository;
        this.marketRepository = marketRepository;
        this.commodityPriceRepository = commodityPriceRepository;
        this.commodityRepository = commodityRepository;

    }

    @Override
    public void run(String... args) throws Exception {
        this.loadData();
    }

    @Transactional
    public void loadData() {
        List<CSVRecord> records = readCSV("/Users/admin/Documents/STAT/backend/src/main/resources/data/data.csv");
        List<AdminLevel1> admin1s = this.getAdmin1s(records);
        List<AdminLevel2> admin2s = this.getAdmin2s(records);
        List<Category> categories = this.getCategories(records);
        List<Commodity> commodities = this.getCommodities(records);
        List<Market> markets = this.getMarkets(records);
        List<CommodityPrice> commodityPrices = this.getCommodityPrices(records);

        if (this.admin1Repository.count() >= 1) {
            System.out.println("Not Loading Data, Records Exists");
            return;
        }

        try {
            this.admin1Repository.saveAll(admin1s);
            this.admin2Repository.saveAll(admin2s);
            this.categoryRepository.saveAll(categories);
            this.commodityRepository.saveAll(commodities);
            this.marketRepository.saveAll(markets);
            this.commodityPriceRepository.saveAll(commodityPrices);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @SuppressWarnings("deprecation")
    List<CSVRecord> readCSV(String filePath) {
        System.out.println("Reading Data: " + filePath);
        try (Reader reader = new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8)) {
            CSVParser csvParser = CSVFormat.DEFAULT
                    .builder()
                    .setHeader(FoodPriceCSVHeaders.class)
                    .setSkipHeaderRecord(true)
                    .setIgnoreEmptyLines(true) // Ignore blank lines
                    .setIgnoreSurroundingSpaces(true) // Trim spaces
                    .setTrim(true) // Trim leading/trailing spaces
                    .build()
                    .parse(reader);
            return csvParser.getRecords();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("Failed Reading CSV");
        }
    }

    List<Market> getMarkets(List<CSVRecord> records) {
        List<Market> markets = new ArrayList<Market>();
        Set<String> marketIds = new HashSet<>();
        LocalDateTime now = LocalDateTime.now();

        for (CSVRecord record : records) {
            String marketName = record.get(FoodPriceCSVHeaders.market);
            UUID id = UUID.nameUUIDFromBytes(marketName.getBytes(StandardCharsets.UTF_8));
            // Skip duplicate markets
            if (marketIds.contains(id.toString())) {
                continue;
            }
            marketIds.add(id.toString());
            Float latitude = Float.valueOf(record.get(FoodPriceCSVHeaders.latitude));
            Float longitude = Float.valueOf(record.get(FoodPriceCSVHeaders.longitude));
            String admin1 = record.get(FoodPriceCSVHeaders.admin1);
            String admin2 = record.get(FoodPriceCSVHeaders.admin2);
            UUID admin1id = UUID.nameUUIDFromBytes(admin1.getBytes(StandardCharsets.UTF_8));
            UUID admin2id = UUID.nameUUIDFromBytes(admin2.getBytes(StandardCharsets.UTF_8));

            Market market = new Market();
            market.setId(id);
            market.setName(marketName);
            market.setCreatedAt(now);
            market.setUpdatedAt(now);
            AdminLevel1 adminLevel1 = new AdminLevel1();
            adminLevel1.setId(admin1id);
            market.setAdminLevel1(adminLevel1);
            AdminLevel2 adminLevel2 = new AdminLevel2();
            adminLevel2.setId(admin2id);
            market.setAdminLevel2(adminLevel2);
            market.setLat(latitude);
            market.setLng(longitude);
            markets.add(market);
        }
        return markets;
    }

    List<Category> getCategories(List<CSVRecord> records) {
        List<Category> categories = new ArrayList<Category>();
        Set<String> categoryIds = new HashSet<>();
        LocalDateTime now = LocalDateTime.now();

        for (CSVRecord record : records) {
            String name = record.get(FoodPriceCSVHeaders.category);
            UUID id = UUID.nameUUIDFromBytes(name.getBytes(StandardCharsets.UTF_8));
            // Skip duplicates
            if (categoryIds.contains(id.toString())) {
                continue;
            }
            categoryIds.add(id.toString());

            Category category = new Category();
            category.setId(id);
            category.setCreatedAt(now);
            category.setName(name);
            category.setUpdatedAt(now);
            categories.add(category);
        }
        return categories;
    }

    List<AdminLevel1> getAdmin1s(List<CSVRecord> records) {
        List<AdminLevel1> admins = new ArrayList<AdminLevel1>();
        Set<String> ids = new HashSet<>();
        LocalDateTime now = LocalDateTime.now();
        for (CSVRecord record : records) {
            String name = record.get(FoodPriceCSVHeaders.admin1);
            if (name.isEmpty()) {
                System.out.println("🚨 Skipping empty admin1 record: " + record.toString());
                continue;
            }
            UUID id;
            try {
                id = UUID.nameUUIDFromBytes(name.getBytes(StandardCharsets.UTF_8));
            } catch (Exception e) {
                System.err.println("Failed to generate UUID for name: " + name);
                e.printStackTrace();
                continue;
            }
            // Skip duplicates
            if (ids.contains(id.toString())) {
                continue;
            }
            ids.add(id.toString());

            AdminLevel1 admin = new AdminLevel1();
            admin.setId(id);
            admin.setName(name);
            admin.setCreatedAt(now);
            admin.setUpdatedAt(now);
            admins.add(admin);

        }
        return admins;
    }

    List<AdminLevel2> getAdmin2s(List<CSVRecord> records) {
        List<AdminLevel2> admins = new ArrayList<AdminLevel2>();
        Set<String> ids = new HashSet<>();
        LocalDateTime now = LocalDateTime.now();

        for (CSVRecord record : records) {
            try {
                String name = record.get(FoodPriceCSVHeaders.admin2);
                String admin1 = record.get(FoodPriceCSVHeaders.admin1);
                UUID id = UUID.nameUUIDFromBytes(name.getBytes(StandardCharsets.UTF_8));
                UUID admin1Id = UUID.nameUUIDFromBytes(admin1.getBytes(StandardCharsets.UTF_8));
                if (ids.contains(id.toString())) {
                    continue;
                }
                ids.add(id.toString());
                AdminLevel2 admin = new AdminLevel2();
                admin.setCreatedAt(now);
                admin.setUpdatedAt(now);
                admin.setName(name);
                admin.setId(id);
                AdminLevel1 adminLevel1 = new AdminLevel1();
                adminLevel1.setId(admin1Id);
                admin.setAdminLevel1(adminLevel1);
                admins.add(admin);
                // this.admin2Repository.save(admin);
            } catch (Exception e) {
                System.err.println(e);
                throw new RuntimeException();
            }

        }
        return admins;
    }

    List<Commodity> getCommodities(List<CSVRecord> records) {
        List<Commodity> commodities = new ArrayList<>();
        Set<String> ids = new HashSet<>();
        LocalDateTime now = LocalDateTime.now();

        for (CSVRecord record : records) {
            String name = record.get(FoodPriceCSVHeaders.commodity);
            String unitName = record.get(FoodPriceCSVHeaders.unit);
            String categoryName = record.get(FoodPriceCSVHeaders.category);

            UUID id = UUID.nameUUIDFromBytes((name.trim() + unitName.trim()).getBytes(StandardCharsets.UTF_8));
            UUID categoryid = UUID.nameUUIDFromBytes(categoryName.getBytes(StandardCharsets.UTF_8));
            UUID unitId = UUID.nameUUIDFromBytes(unitName.getBytes(StandardCharsets.UTF_8));
            // Skip duplicates
            if (ids.contains(id.toString())) {
                continue;
            }
            ids.add(id.toString());
            Commodity commodity = new Commodity();
            commodity.setId(id);
            commodity.setName((name + " "+ unitName).trim());
            Unit unit = new Unit();
            unit.setId(unitId);
            commodity.setUnit(unitName);
            commodity.setCreatedAt(now);
            commodity.setUpdatedAt(now);
            Category category = new Category();
            category.setId(categoryid);
            commodity.setCategory(category);
            commodities.add(commodity);
        }

        return commodities;
    }

    List<CommodityPrice> getCommodityPrices(List<CSVRecord> records) {
        List<CommodityPrice> prices = new ArrayList<>();

        for (CSVRecord record : records) {
            String commodity = record.get(FoodPriceCSVHeaders.commodity);
            String admin2 = record.get(FoodPriceCSVHeaders.admin2);
            String admin1 = record.get(FoodPriceCSVHeaders.admin1);
            String market = record.get(FoodPriceCSVHeaders.market);
            String unitName = record.get(FoodPriceCSVHeaders.unit);
            String category = record.get(FoodPriceCSVHeaders.category);
            Float price = Float.parseFloat(record.get(FoodPriceCSVHeaders.price));
            Float usdprice = Float.parseFloat(record.get(FoodPriceCSVHeaders.usdprice));
            String pricetype = record.get(FoodPriceCSVHeaders.pricetype).toUpperCase();
            String priceflag = record.get(FoodPriceCSVHeaders.priceflag).toUpperCase();
            LocalDate created_at = LocalDate.parse(record.get(FoodPriceCSVHeaders.date));
            UUID id = UUID.randomUUID();
            UUID marketid = UUID.nameUUIDFromBytes(market.getBytes(StandardCharsets.UTF_8));
            UUID categoryid = UUID.nameUUIDFromBytes(category.getBytes(StandardCharsets.UTF_8));
            UUID admin1id = UUID.nameUUIDFromBytes(admin1.getBytes(StandardCharsets.UTF_8));
            UUID admin2id = UUID.nameUUIDFromBytes(admin2.getBytes(StandardCharsets.UTF_8));
            UUID commodityid = UUID.nameUUIDFromBytes((commodity.trim() + unitName.trim()).getBytes(StandardCharsets.UTF_8));
            //(name.trim() + unitName.trim()
            CommodityPrice commodityPrice = new CommodityPrice();
            commodityPrice.setId(id);
            commodityPrice.setUsdprice(usdprice);
            commodityPrice.setPriceflag(priceflag);
            commodityPrice.setPricetype(pricetype);
            // Unit unit = new Unit();
            // unit.setId(unitId);
            commodityPrice.setUnit(unitName);
            commodityPrice.setPrice(price);
            commodityPrice.setCommodity_name(commodity);
            commodityPrice.setCategory_name(category);
            commodityPrice.setMarket_name(market);
            commodityPrice.setCreatedAt(created_at);
            commodityPrice.setUpdatedAt(created_at);
            Market mkt = new Market();
            mkt.setId(marketid);
            commodityPrice.setMarket(mkt);

            Category cat = new Category();
            cat.setId(categoryid);
            commodityPrice.setCategory(cat);

            AdminLevel1 adminLevel1 = new AdminLevel1();
            adminLevel1.setId(admin1id);
            commodityPrice.setAdminLevel1(adminLevel1);

            AdminLevel2 adminLevel2 = new AdminLevel2();
            adminLevel2.setId(admin2id);
            commodityPrice.setAdminLevel2(adminLevel2);

            Commodity cmdty = new Commodity();
            cmdty.setId(commodityid);
            commodityPrice.setCommodity(cmdty);

            prices.add(commodityPrice);
        }
        return prices;
    }

}
