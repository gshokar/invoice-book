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

    function Component(props) {

        let id = props.componentId;
        let parent = props.parentComponent;
        let name = props.componentName;
        let valueChanged = false;

        function controlId() {
            return "#" + id;
        }

        this.getControl = function () {

            if (parent && typeof parent === 'function') {
                return parent().find(controlId());
            }

            return $(controlId());
        };

        this.getId = function () {
            return id;
        };

        this.getControlId = function () {
            return controlId();
        };

        this.getParent = function () {
            return parent;
        };

        this.setParent = function (parentComponent) {
            parent = parentComponent;
        };

        this.getName = function () {
            return name;
        };

        this.setName = function (value) {
            name = value;
        };

        this.updateElementId = function (element) {

            let elementId = undefined;
            
            if (element.newId !== undefined && element.newId !== null) {
                elementId = element.newId;
            }else if(element.createNewId === true){
                elementId = $aatl_ib.utils.createUniqueId();
            }

            if (elementId !== undefined) {
                let updatedHtml = $aatl_ib.utils.replaceElementId(element.html, id, elementId);

                if (Array.isArray(element.attributes)) {

                    $.each(element.attributes, function (index, attribute) {
                        updatedHtml = $aatl_ib.utils.replaceElementAttribute(updatedHtml, attribute, id, elementId);
                    });
                }

                id = elementId;

                return updatedHtml;
            }
            
            return element.html;
        };

        this.isModified = function () {

            return valueChanged;
        };

        this.setModified = function (value) {

            if (typeof value === 'boolean') {
                valueChanged = value;
            }
        };
    }

    return Component;
}());
