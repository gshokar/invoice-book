/* 
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Jan-24  GShokar         Created
 * =============================================================================
 */

"use strict";

$aatl_ib.gui.HomeComponent = (function () {

    function HomeComponent(props) {

        let id = props.componentId;
        let controlId = "#" + id;
        let parent = props.parentComponent;
        
        this.getComponent = function() {

            if (parent && typeof parent === 'function') {
                return parent().find(controlId);
            }

            return $(controlId);
        };
        
        this.getId = function(){
            return id;
        };
        
        this.bindEvents = function () {

        };
        
        this.init = function(){
            this.getComponent().load($aatl_ib.viewController.getViewUrl("home"));
        };
    }

    return HomeComponent;
}());