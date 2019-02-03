/* 
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Feb-02  GShokar         Created
 * =============================================================================
 */

"use strict";

$aatl_ib.TimeSheetService = {

    print: function (criteria, callback) {

        $aatl_ib.ApiService.get("timesheet/print",
                criteria,
                function (res, err) {
                    if (err) {
                        callback(null, {messages: ["Faild to get timesheet print: " + err]});

                    } else if (res.status === "failure") {
                        callback(null, {messages: [res.message]});
                    } else if (res.status === "success") {
                        let list = JSON.parse(res.data);
                        callback(list);
                    } else {
                        callback(null, {messages: ["Invalid response from server"]});
                    }
                });
    }
};


