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


$aatl_ib.gui.HomeComponent = (function () {

    function HomeComponent(componentId, parentComponent) {

        let id = componentId;
        let controlId = "#" + id;
        let parent = parentComponent;
        
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
    }

    return HomeComponent;
}());