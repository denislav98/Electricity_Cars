package app.entities.service;

import java.util.EnumSet;

public enum ServiceEnum {;


    public static EnumSet<ServiceType> serviceTypesEnumSet = EnumSet.of(
                    ServiceType.BATTERY_RECHARGED,
                    ServiceType.CAR_CLEANED,
                    ServiceType.CAR_REPAIRED,
                    ServiceType.TYRES_CHANGED,
                    ServiceType.WARRANTY_SERVICE_ATTENDED
            );


    public EnumSet<ServiceType> getServiceTypesEnumSet() {
        return serviceTypesEnumSet;
    }

    public void setServiceTypesEnumSet(EnumSet<ServiceType> serviceTypesEnumSet) {
        ServiceEnum.serviceTypesEnumSet = serviceTypesEnumSet;
    }
}
