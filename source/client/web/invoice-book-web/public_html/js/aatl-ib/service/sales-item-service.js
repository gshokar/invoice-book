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

    itemTypeList: [],
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
    },
    itemsByItemType: function (itemType, callback) {
        $aatl_ib.ApiService.get("sales-item/items-by-item-type",
                {itemType: itemType},
                function (res, err) {
                    if (err) {
                        callback([], {messages: ["Item list by item type request failed: " + err]});

                    } else if (res.status === "failure") {
                        callback([], res.message);
                    } else if (res.status === "success") {
                        callback(JSON.parse(res.data));
                    } else {
                        callback([], "Invalid response from server")
                    }
                });
    },
       itemTypes: function (callback) {

        if ($aatl_ib.SalesItemService.itemTypeList.length > 0) {
            callback($aatl_ib.SalesItemService.itemTypeList);
        } else {
            $aatl_ib.ApiService.get("sales-item/item-types",
                    {},
                    function (res, err) {
                        if (err) {
                            callback([], "Item types list request failed: " + err);

                        } else if (res.status === "failure") {
                            callback([], res.message);
                        } else if (res.status === "success") {
                            $aatl_ib.SalesItemService.itemTypeList = JSON.parse(res.data);
                            callback($aatl_ib.SalesItemService.itemTypeList);
                        } else {
                            callback([], "Invalid response from server");
                        }
                    });
        }
    }
};
