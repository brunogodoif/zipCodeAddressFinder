package br.com.brunogodoif.zipcodeaddressfinder.adapters.configs;

import br.com.brunogodoif.zipcodeaddressfinder.adapters.configs.dataloaders.*;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoaderConfig {

    private final RegionDataLoader regionDataLoader;
    private final StateDataLoader stateDataLoader;
    private final CityDataLoader cityDataLoader;
    private final DistrictsDataLoader districtsDataLoader;
    private final AddressDataLoader addressDataLoader;

    public DataLoaderConfig(RegionDataLoader regionDataLoader, StateDataLoader stateDataLoader,
                            CityDataLoader cityDataLoader, DistrictsDataLoader districtsDataLoader,
                            AddressDataLoader addressDataLoader
                           ) {
        this.regionDataLoader = regionDataLoader;
        this.stateDataLoader = stateDataLoader;
        this.cityDataLoader = cityDataLoader;
        this.districtsDataLoader = districtsDataLoader;
        this.addressDataLoader = addressDataLoader;
    }

    @Bean public ApplicationRunner dataLoaderRunner() {
        return args -> {
            regionDataLoader.loadData();
            stateDataLoader.loadData();
            cityDataLoader.loadData();
            districtsDataLoader.loadData();
            addressDataLoader.loadData();
        };
    }
}