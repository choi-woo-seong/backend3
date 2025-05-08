package com.project.msy.facility.entity;

import lombok.Getter;

public enum FacilityType {
    SILVER_TOWN("실버타운"),
    NURSING_HOME("양로원"),
    CARE_HOME("요양원");

    @Getter
    private final String korean;
    FacilityType(String korean) { this.korean = korean; }

    @Override
    public String toString() {
        return korean;
    }
}
