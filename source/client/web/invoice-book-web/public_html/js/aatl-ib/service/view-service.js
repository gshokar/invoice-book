/* 
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Dec-27  GShokar         Created
 * =============================================================================
 */

"use strict";

$aatl_ib.ViewService = {

    views: [],

    getViewContent: function (name, callback) {

        let view = $aatl_ib.ViewService.views.find(function (view) {
            return view.name === name;
        });

        if (view !== undefined) {
            callback(view.html);
        } else {
            $.get($aatl_ib.viewController.getViewUrl(name), function (html) {
                $aatl_ib.ViewService.views.push({name: name, html: html});
                callback(html);
            });
        }

    }
};
