/* 
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Jan-26  GShokar         Created
 * =============================================================================
 */


"use strict";

$aatl_ib.gui.ActionItem = (function () {

    function ActionItem(props) {

        this.text = props.text;
        this.id = props.id !== undefined && props.id !== null ? props.id : props.createId === true ? $aatl_ib.utils.createUniqueId() : undefined;
        this.controlId = "#" + this.id;
        this.typeCode = props.typeCode;
        this.viewComponent = undefined;
        this.data = props.data;
        this.linkedPanel = undefined;
        this.icon = props.icon;

        this.setId = function (id) {

            this.id = id;
            this.controlId = "#" + id;

        };

    }

    return ActionItem;

}());

$aatl_ib.gui.ActionItemTypeCode = {

    Home: "home",
    ClientSearch: "clientSearch",
    ClientDetail: 'clientDetail',
    EmployeeSearch: "employeeSearch",
    EmployeeDetail: 'employeeDetail',
    TimeCodes: "timeCodes",
    Save: 'save',
    Open: 'open',
    Close: 'close',
    New: 'new',
    Reset: 'reset'
};

$aatl_ib.gui.ActionItemIcon = {

    Home: "home",
    ClientSearch: "users",
    ClientDetail: 'user',
    EmployeeSearch: "users",
    EmployeeDetail: 'user',
    TimeCodes: "code",
    Save: 'save',
    getIcon: function (actionItemTypeCode) {

        let icon = undefined;

        switch (actionItemTypeCode) {
            case $aatl_ib.gui.ActionItemTypeCode.ClientDetail:
                icon = $aatl_ib.gui.ActionItemIcon.ClientDetail;
                break;
            case $aatl_ib.gui.ActionItemTypeCode.ClientSearch:
                icon = $aatl_ib.gui.ActionItemIcon.ClientSearch;
                break;
            case $aatl_ib.gui.ActionItemTypeCode.EmployeeDetail:
                icon = $aatl_ib.gui.ActionItemIcon.EmployeeDetail;
                break;
            case $aatl_ib.gui.ActionItemTypeCode.EmployeeSearch:
                icon = $aatl_ib.gui.ActionItemIcon.EmployeeSearch;
                break;
            case $aatl_ib.gui.ActionItemTypeCode.Home:
                icon = $aatl_ib.gui.ActionItemIcon.Home;
                break;
            case $aatl_ib.gui.ActionItemTypeCode.TimeCodes:
                icon = $aatl_ib.gui.ActionItemIcon.TimeCodes;
                break;
            case $aatl_ib.gui.ActionItemTypeCode.Save:
                icon = $aatl_ib.gui.ActionItemIcon.Save;
                break;
        }
        return icon;
    }
};