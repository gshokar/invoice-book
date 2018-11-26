/* 
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Nov-26  GShokar         Created
 * =============================================================================
 */

"use strict";

$aatl_ib.LookupService = {

    provinces: [{code: "AB", name: "Alberta"}, {code: "BC", name: "British Columbia"}, {code: "ON", name: "Ontario"}],
    defaultProvince: {code: "ON", name: "Ontario"},
    loadProvinces: function(control){
        
        control.empty();
        
        if($aatl_ib.LookupService.provinces.length === 0){
            
            return;
        }
        
        $.each($aatl_ib.LookupService.provinces, function(key, province){
            control.append($('<option></option>').val(province.code).text(province.name));
        });
    }
};
