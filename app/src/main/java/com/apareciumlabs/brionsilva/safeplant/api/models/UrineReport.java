package com.apareciumlabs.brionsilva.safeplant.api.models;

/**
 * Copyright (c) 2017. Aparecium Labs.  http://www.apareciumlabs.com
 *
 * @author brionsilva
 * @version 1.0
 * @since 29/10/2017
 */
public class UrineReport {

    private Integer id;
    private String date;
    private String time;
    private String appearance;
    private double ph;
    private double gravity;
    private String protein;
    private String sugar;
    private String red_cells;
    private String pus_cells;
    private String ephi_cells;
    private String casts;
    private String crystals;
    private String bile_segments;
    private String others;

    public UrineReport(String date, String time, String appearance, double ph, double gravity,
                       String protein, String sugar, String red_cells, String pus_cells, String ephi_cells,
                       String casts, String crystals, String bile_segments, String others) {
        this.date = date;
        this.time = time;
        this.appearance = appearance;
        this.ph = ph;
        this.gravity = gravity;
        this.protein = protein;
        this.sugar = sugar;
        this.red_cells = red_cells;
        this.pus_cells = pus_cells;
        this.ephi_cells = ephi_cells;
        this.casts = casts;
        this.crystals = crystals;
        this.bile_segments = bile_segments;
        this.others = others;
    }

    public Integer getId() {
        return id;
    }
}
