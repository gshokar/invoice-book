/* 
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Feb-16  GShokar         Created
 * =============================================================================
 */


"use strict";

$aatl_ib.SalesItemService = {

    serviceItems: function (callback) {
        $aatl_ib.ApiService.get("sales-item/service-items",
                {},
                function (res, err) {
                    if (err) {
                        callback([], "Service item list request failed: " + err);

                    } else if (res.status === "failure") {
                        callback([], res.message);
                    } else if (res.status === "success") {
                        callback(JSON.parse(res.data));
                    } else {
                        callback([], "Invalid response from server")
                    }
                });
    }
};
