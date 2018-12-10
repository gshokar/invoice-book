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

    function Component(componentId, parentComponent, componentName) {

        let id = componentId;
        let parent = parentComponent;
        let name = componentName;
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

        this.updateElementId = function (html, newId, updateForAttributes) {

            if (newId !== undefined && newId !== null) {
                let updatedHtml = $aatl_ib.utils.replaceElementId(html, id, newId);

                if (updateForAttributes === true) {
                    updatedHtml = $aatl_ib.utils.replaceElementAttributeFor(updatedHtml, id, newId);
                }

                id = newId;

                return updatedHtml;
            }
            return html;
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
