
package ca.aatl.app.invoicebook.dto;

/**
 *
 * @author gshokar
 */
public class ProvinceDto {

    public ProvinceDto() {
    }

    public ProvinceDto(String code, String name) {
        this.code = code;
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

    private String countryCode;

    /**
     * Get the value of countryCode
     *
     * @return the value of countryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Set the value of countryCode
     *
     * @param countryCode new value of countryCode
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

}
