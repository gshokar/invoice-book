/* 
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Jan-28  GShokar         Created
 * =============================================================================
 */

"use strict";

$aatl_ib.gui.Component = (function () {

    function Component(componentId, parentComponent) {

        let id = componentId;
        let controlId = "#" + id;
        let parent = parentComponent;
        
        this.getControl = function() {

            if (parent && typeof parent === 'function') {
                return parent().find(controlId);
            }

            return $(controlId);
        };
        
        this.getId = function(){
            return id;
        };
    
        this.getControlId = function(){
            return controlId;
        };
        
        this.getParent = function(){
            return parent;
        };
    }

    return Component;
}());
