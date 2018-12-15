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

provinces:[],
    //provinces: [{code: "AB", name: "Alberta"}, {code: "BC", name: "British Columbia"}, {code: "ON", name: "Ontario"}],

    defaultProvince: {code: "ON", name: "Ontario"},

    loadProvinces: function (callback) {
        
        if ($aatl_ib.LookupService.provinces.length === 0) {

            $aatl_ib.ApiService.get("provinceList",
                    {},
                    function (res, err) {
                        if (err) {
                            callback([], "Province list request failed: " + err);

                        } else if (res.status === "failure") {
                            callback([], res.message);
                        } else if (res.status === "success") {
                            $aatl_ib.LookupService.provinces = JSON.parse(res.data);
                            callback($aatl_ib.LookupService.provinces);
                        } else {
                            callback([], "Invalid response from server")
                        }
                    });

            return;
        } else {

            callback($aatl_ib.LookupService.provinces);
        }
    }
};
