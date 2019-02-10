package ca.aatl.app.invoicebook.bl.ejb;

import ca.aatl.app.invoicebook.data.jpa.dao.AddressTypeDao;
import ca.aatl.app.invoicebook.data.jpa.dao.CompanyServiceDao;
import ca.aatl.app.invoicebook.data.jpa.dao.ContactTypeDao;
import ca.aatl.app.invoicebook.data.jpa.dao.CountryDao;
import ca.aatl.app.invoicebook.data.jpa.dao.ProvinceDao;
import ca.aatl.app.invoicebook.data.jpa.entity.AddressType;
import ca.aatl.app.invoicebook.data.jpa.entity.CompanyService;
import ca.aatl.app.invoicebook.data.jpa.entity.ContactType;
import ca.aatl.app.invoicebook.data.jpa.entity.Country;
import ca.aatl.app.invoicebook.data.jpa.entity.Province;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author gshokar
 */
@Stateless
@LocalBean
public class LookupService {

    @EJB
    ProvinceDao provinceDao;

    @EJB
    AddressTypeDao addressTypeDao;
    
    @EJB
    ContactTypeDao contactTypeDao;

    @EJB
    CountryDao countryDao;
    
    @EJB
    CompanyServiceDao companyServiceDao;
    
    private static AddressType defaultAddressType;
    private static ContactType defaultContactType;
    private static Province defaultProvince;
    private static Country defaultCountry;
    
    public List<Province> provinces() throws Exception {

        return provinceDao.list();
    }

    public Province findProvince(String code) throws Exception {
        return provinceDao.findByCode(code);
    }

    public synchronized AddressType defaultAddressType() throws Exception {

        if (defaultAddressType == null) {

            defaultAddressType = addressTypeDao.find("Business");

        }

        return defaultAddressType;
    }

    public synchronized Province defaultProvince() throws Exception {
        if (defaultProvince == null) {

            defaultProvince = findProvince("ON");

        }

        return defaultProvince;
    }

    public synchronized Country defaultCountry() throws Exception{
        if (defaultCountry == null) {

            defaultCountry = countryDao.find("CA");

        }

        return defaultCountry;
    }

    public synchronized ContactType defaultContactType() {
        
        if (defaultContactType == null) {

            defaultContactType = contactTypeDao.find("Business");

        }

        return defaultContactType;
    }

    public List<CompanyService> companyServices() throws Exception{
        return companyServiceDao.list();
    }

    public CompanyService companyService(String guid) throws Exception{
        return companyServiceDao.find(guid);
    }

}
