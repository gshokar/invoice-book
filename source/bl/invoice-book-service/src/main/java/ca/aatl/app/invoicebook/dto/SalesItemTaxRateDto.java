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
public class SalesItemTaxRateDto {
    
    private String uid;

    /**
     * Get the value of uid
     *
     * @return the value of uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * Set the value of uid
     *
     * @param uid new value of uid
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    private SalesTaxDto tax;

    /**
     * Get the value of tax
     *
     * @return the value of tax
     */
    public SalesTaxDto getTax() {
        return tax;
    }

    /**
     * Set the value of tax
     *
     * @param tax new value of tax
     */
    public void setTax(SalesTaxDto tax) {
        this.tax = tax;
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

    private String fromDate;

    /**
     * Get the value of fromDate
     *
     * @return the value of fromDate
     */
    public String getFromDate() {
        return fromDate;
    }

    /**
     * Set the value of fromDate
     *
     * @param fromDate new value of fromDate
     */
    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    private String toDate;

    /**
     * Get the value of toDate
     *
     * @return the value of toDate
     */
    public String getToDate() {
        return toDate;
    }

    /**
     * Set the value of toDate
     *
     * @param toDate new value of toDate
     */
    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    private CountryDto country;

    /**
     * Get the value of country
     *
     * @return the value of country
     */
    public CountryDto getCountry() {
        return country;
    }

    /**
     * Set the value of country
     *
     * @param country new value of country
     */
    public void setCountry(CountryDto country) {
        this.country = country;
    }

    private ProvinceDto province;

    /**
     * Get the value of province
     *
     * @return the value of province
     */
    public ProvinceDto getProvince() {
        return province;
    }

    /**
     * Set the value of province
     *
     * @param province new value of province
     */
    public void setProvince(ProvinceDto province) {
        this.province = province;
    }

    private SalesItemDto item;

    /**
     * Get the value of item
     *
     * @return the value of item
     */
    public SalesItemDto getItem() {
        return item;
    }

    /**
     * Set the value of item
     *
     * @param item new value of item
     */
    public void setItem(SalesItemDto item) {
        this.item = item;
    }

}
