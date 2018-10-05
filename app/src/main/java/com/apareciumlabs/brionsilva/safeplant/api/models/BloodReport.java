package com.apareciumlabs.brionsilva.safeplant.api.models;

/**
 * Copyright (c) 2017. Aparecium Labs.  http://www.apareciumlabs.com
 *
 * @author brionsilva
 * @version 1.0
 * @since 29/10/2017
 */
public class BloodReport {

    private Integer id;
    private String date;
    private String time;
    private double wbc;
    private double rbc;
    private double cell_volume;
    private double hemoglobin_concentration;
    private double corpuscular_volume;
    private double corpuscular_hemoglobin;
    private double corpuscular_hemoglobin_concentration;
    private double platelet_count;

    public BloodReport(String date, String time, double wbc, double rbc, double cell_volume,
                       double hemoglobin_concentration, double corpuscular_volume, double corpuscular_hemoglobin,
                       double corpuscular_hemoglobin_concentration, double platelet_count ) {
        this.date = date;
        this.time = time;
        this.wbc = wbc;
        this.rbc = rbc;
        this.cell_volume = cell_volume;
        this.hemoglobin_concentration = hemoglobin_concentration;
        this.corpuscular_volume = corpuscular_volume;
        this.corpuscular_hemoglobin = corpuscular_hemoglobin;
        this.corpuscular_hemoglobin_concentration = corpuscular_hemoglobin_concentration;
        this.platelet_count = platelet_count;
    }

    public Integer getId() {
        return id;
    }
}
