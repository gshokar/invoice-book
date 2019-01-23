package ca.aatl.app.invoicebook.dto;

import java.util.List;

/**
 *
 * @author gshokar
 */
public class CountryDto {

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

    private List<ProvinceDto> provinces;

    /**
     * Get the value of provinces
     *
     * @return the value of provinces
     */
    public List<ProvinceDto> getProvinces() {
        return provinces;
    }

    /**
     * Set the value of provinces
     *
     * @param provinces new value of provinces
     */
    public void setProvinces(List<ProvinceDto> provinces) {
        this.provinces = provinces;
    }

}
