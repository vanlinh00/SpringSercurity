package com.example.SpringSecurity.entity;

import java.util.Objects;

//Khu vực địa lý
public class GeoZone {

    private Long id;
    private double lat;
    private double lon;
    private double radius;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GeoZone zone)) return false;
        return id != null && id.equals(zone.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /*
    Set<GeoZone> uniqueZones = new HashSet<>(listFromDB);
 Khi bạn cần loại bỏ vùng an toàn bị trùng (theo id), equals() và hashCode() giúp Set hoạt động đúng.

     */
}
