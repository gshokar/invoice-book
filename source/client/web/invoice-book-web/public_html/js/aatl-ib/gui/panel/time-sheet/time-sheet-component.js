/* 
 * Copyright (c) 2019 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2019-Jan-25  GShokar         Created
 * =============================================================================
 */
"use strict";

$aatl_ib.gui.TimeSheetComponent = (function () {
    function TimeSheetComponent(props) {

        let component = new $aatl_ib.gui.Component(props);

        let timeSheet = {};
        let onActionButtonClicked = null;
        let afterInit = null;

        let errorComponent = new $aatl_ib.ErrorComponent({componentId: "timeSheetErrors", parentComponent: component.getControl});
        let titleComponent = new $aatl_ib.gui.Component({componentId: "panelTitle", parentComponent: component.getControl});
        let employeeField = new $aatl_ib.gui.Component({componentId: "employee", parentComponent: component.getControl, componentName: "employee"});
        let timeRecordTable = new $aatl_ib.gui.TableComponent({componentId: "timeSheeTimeRecords", parentComponent: component.getControl});
        let timeRecordRowEdit = new $aatl_ib.gui.TimeCodeRowEdit();

        function getControl() {
            return component.getControl();
        }

        function afterLoad() {

            bindEvents();

            if (afterInit !== null && typeof afterInit === "function") {
                afterInit();
            }
        }

        function bindEvents() {

            getControl().find(".time-sheet-action-btn-group button").click(function (evt) {

                let $element = $(evt.target);
                let action = $element.data("action");

                onActionButtonClicked(action);
            });

            timeRecordTable.setOnRowDoubleClicked(onTimeRecordRowDoubleClicked)
        }

        function onTimeRecordRowDoubleClicked(keyValue) {

            if (timeRecordRowEdit.getEditMode() === false) {

                let timeCode = timeCodes.find(function (tc) {
                    return tc.uid == keyValue;
                });

                if (timeCode !== undefined) {
                    if (keyValue !== timeRecordRowEdit.getTimeCode().uid) {
                        timeRecordRowEdit.setRow(timeCodeTable.getRowControl(keyValue), timeCode);

                        setButtonActionEnabled("add", false);
                        setButtonActionEnabled("cancel", true);
                        setButtonActionEnabled("save", false);
                    }
                }
            }
        }
        function updateElementIds(html) {
            let updatedHtml = errorComponent.updateElementId(html);

            let element = {html: updatedHtml, createNewId: true};

            element.html = titleComponent.updateElementId(element);

            element.attributes = ['for'];

            element.html = employeeField.updateElementId(element);
            
            return element.html;
        }
        function loadView(html) {
            getControl().html(updateElementIds(html));
            afterLoad();
        }

        this.init = function () {
            $aatl_ib.ViewService.getViewContent("time-sheet", loadView);

        };

        this.registerOnActionButtonClicked = function (actionClicked) {
            onActionButtonClicked = actionClicked;
        };

        this.setAfterInit = function (callback) {
            afterInit = callback;
        };
    }
    return TimeSheetComponent;
}());

