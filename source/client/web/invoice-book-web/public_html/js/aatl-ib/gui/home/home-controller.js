/* 
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Nov-17  GShokar         Created
 * =============================================================================
 */

"use strict";

$aatl_ib.gui.HomeController = (function () {
    function HomeController(componentId, parentComponent) {

        var component = new $aatl_ib.gui.HomeComponent(componentId, parentComponent);

        this.init = function () {
            component.init();
        };
        
        this.getComponent = function(){
            return component;
        };
    }

    return HomeController;
}());