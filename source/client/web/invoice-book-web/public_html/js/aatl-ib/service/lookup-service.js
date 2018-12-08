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

    loadProvinces: function (control) {

        function addOptions() {
            $.each($aatl_ib.LookupService.provinces, function (index, province) {
                control.append($('<option></option>').val(province.code).text(province.name));
            });
        }

        control.empty();

        if ($aatl_ib.LookupService.provinces.length === 0) {

            $aatl_ib.ApiService.post("get","provinceList",
                    {},
                    function (res, err) {
                        if (err) {
                            //callback("Login request failed: " + err);

                        } else if (res.status === "failure") {
                            //callback(res.message);
                        } else if (res.status === "success") {
                            aatl_ib.LookupService.provinces = JSON.parse(res.data);
                            addOptions();
                            //callback();
                        } else {
                            //callback("Invalid response from server")
                        }
                    });

            return;
        } else {

            addOptions();
        }
    }
};
