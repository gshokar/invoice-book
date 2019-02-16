/*
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Feb-14  GShokar         Created
 * =============================================================================
 */
package ca.aatl.app.invoicebook.dto;

/**
 *
 * @author GShokar
 */
public class SalesItemDto {

    public SalesItemDto() {
    }

    public SalesItemDto(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private String name;

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(String name) {
        this.name = name;
    }

    private String code;

    /**
     * Get the value of code
     *
     * @return the value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Set the value of code
     *
     * @param code new value of code
     */
    public void setCode(String code) {
        this.code = code;
    }

    private String description;

    /**
     * Get the value of description
     *
     * @return the value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the value of description
     *
     * @param description new value of description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    private SalesItemTypeDto ItemType;

    /**
     * Get the value of ItemType
     *
     * @return the value of ItemType
     */
    public SalesItemTypeDto getItemType() {
        return ItemType;
    }

    /**
     * Set the value of ItemType
     *
     * @param ItemType new value of ItemType
     */
    public void setItemType(SalesItemTypeDto ItemType) {
        this.ItemType = ItemType;
    }

    private double rate;

    /**
     * Get the value of rate
     *
     * @return the value of rate
     */
    public double getRate() {
        return rate;
    }

    /**
     * Set the value of rate
     *
     * @param rate new value of rate
     */
    public void setRate(double rate) {
        this.rate = rate;
    }

    private ItemUnitDto unit;

    public ItemUnitDto getUnit() {
        return unit;
    }

    public void setUnit(ItemUnitDto unit) {
        this.unit = unit;
    }

}
