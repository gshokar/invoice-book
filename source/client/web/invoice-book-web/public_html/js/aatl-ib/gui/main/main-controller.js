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


$aatl_ib.MainController = (function () {

    function MainController() {

        let component = new $aatl_ib.gui.MainComponent();
        let panels = [];

        function actionItemClicked(actionItem) {
            let existingPanel = findPanel(actionItem.text);

            if (existingPanel) {

                component.showView(existingPanel.controlId);

            } else {

                openActionItemPanel(actionItem);
            }
        }

        function openActionItemPanel(actionItem) {

            if (actionItem.id === undefined) {

                addActionItem(actionItem);

            } else {

                let controlId = undefined;

                switch (actionItem.typeCode) {
                    case $aatl_ib.model.gui.ActionItemTypeCode.Home:
                        controlId = openHome(actionItem);
                        break;
                    case $aatl_ib.model.gui.ActionItemTypeCode.ClientSearch:
                        controlId = openClientSearch(actionItem);
                        break;
                    case $aatl_ib.model.gui.ActionItemTypeCode.ClientDetail:
                        controlId = openClientDetail(actionItem);
                        break;
                    case $aatl_ib.model.gui.ActionItemTypeCode.EmployeeSearch:
                        controlId = openEmployeeSearch(actionItem);
                        break;
                    case $aatl_ib.model.gui.ActionItemTypeCode.EmployeeDetail:
                        controlId = openEmployeeDetail(actionItem);
                        break;
                }

                if (controlId) {
                    component.showView(controlId);
                }
            }
        }
        ;

        function addActionItem(actionItem) {
            actionItem.setId($aatl_ib.utils.createUniqueId());

            component.addActionItem(actionItem);
        }

        function findPanel(title) {
            return panels.find(function (panel) {
                return panel.title === title;
            });
        }

        function createPanel(panelTypeCode, actionItem) {

            let panel = new $aatl_ib.model.gui.Panel(panelTypeCode, $aatl_ib.utils.createUniqueId());

            if (actionItem !== undefined && actionItem !== null) {

                panel.setLinkedActionItem(actionItem);
                actionItem.linkedPanel = panel;
                panel.title = actionItem.text;
            }

            component.addView(panel.controlId);
            panels.push(panel);

            return panel;
        }

        function openHome(actionItem) {

            let panel = createPanel($aatl_ib.model.gui.PanelTypeCode.Home, actionItem);

            panel.controller = new $aatl_ib.gui.HomeController(panel.controlId, component.getCenterView());

            panel.controller.init();

            return panel.controlId;
        }

        function openClientSearch(actionItem) {

            let panel = createPanel($aatl_ib.model.gui.PanelTypeCode.ClientSearch, actionItem);

            panel.controller = new $aatl_ib.gui.ClientSearchController(panel.controlId, component.getCenterView());

            panel.controller.init();

            return panel.controlId;
        }

        function openClientDetail(actionItem) {

            let panel = createPanel($aatl_ib.model.gui.PanelTypeCode.ClientDetail, actionItem);

            panel.controller = new $aatl_ib.gui.ClientDetailController(panel.controlId, component.getCenterView());
            panel.controller.setTitle(actionItem.text);
            panel.controller.setClientNumber(actionItem.data);

            panel.controller.init();

            return panel.controlId;
        }
        
        function openEmployeeSearch(actionItem) {

            let panel = createPanel($aatl_ib.model.gui.PanelTypeCode.EmployeeSearch, actionItem);

            panel.controller = new $aatl_ib.gui.EmployeeSearchController(panel.controlId, component.getCenterView());

            panel.controller.init();

            return panel.controlId;
        }

        function openEmployeeDetail(actionItem) {

            let panel = createPanel($aatl_ib.model.gui.PanelTypeCode.EmployeeDetail, actionItem);

            panel.controller = new $aatl_ib.gui.EmployeeDetailController(panel.controlId, component.getCenterView());
            panel.controller.setTitle(actionItem.text);
            panel.controller.setEmployeeNumber(actionItem.data);

            panel.controller.init();

            return panel.controlId;
        }

        this.init = function () {

            component.init();
            component.bindEvents(actionItemClicked);
            component.setHomeView();
        };

        this.openPanel = function (actionItem) {
            let existingPanel = findPanel(actionItem.text);

            if (existingPanel) {

                component.selectActionItem(existingPanel.linkedActionItem);

            } else {

                openActionItemPanel(actionItem);
            }

        };

        this.updateActionItemText = function (currentTitle, newTitle) {
            let panel = findPanel(currentTitle);

            if (panel) {
                panel.title = newTitle;
                panel.linkedActionItem.text = newTitle;
                component.updateActionItemText(panel.linkedActionItem);
            }
        };
    }

    return MainController;
}());