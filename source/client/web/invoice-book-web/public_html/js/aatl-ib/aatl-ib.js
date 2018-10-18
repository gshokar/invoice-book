/* 
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Jan-21  GShokar         Created
 * =============================================================================
 */

"use strict";

const $aatl_ib = {
    title: "Invoice Book",
    mainView: function () {
        return $("#mainView");
    },
    viewController: {
        mainController: undefined,

        setMainView: function (view, controller) {

            if (controller && controller.init && typeof controller.init !== 'function') {
                throw "Invalid initCall, it's not function";
            }

            this.mainController = controller;

            $aatl_ib.mainView().empty();
            $aatl_ib.mainView().load(this.getViewUrl(view), controller.init);
        },

        getViewUrl: function (view) {
            return "view/" + view + ".html";
        }
    },
    model: {
        gui: {}
    },
    gui: {}
};


$(document).ready(() => {
    $aatl_ib.viewController.setMainView("login", new $aatl_ib.LoginController());
});