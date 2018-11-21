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

        let component = new $aatl_ib.MainComponent();
        let panelComponents = [];

        function actionItemClicked(actionItem) {

            switch (actionItem.typeCode) {
                case $aatl_ib.model.gui.ActionItemTypeCode.Home:
                    openHome(actionItem);
                    break;
                case $aatl_ib.model.gui.ActionItemTypeCode.ClientSearch:
                    openClientSearch(actionItem);
                    break;
            }
        }

        this.init = function () {

            component.init();
            component.bindEvents(actionItemClicked);
            component.setHomeView();
        };

        this.openPanel = function (panel) {

            // TODO: invalid Panel error code

            let existingPanel = findPanel(panel);
            let controlId = undefined;

            if (existingPanel) {
                controlId = existingPanel.controlId;
            } else {

                switch (panel.typeCode) {
                    case $aatl_ib.model.gui.PanelTypeCode.Home:
                        openHome(panel);
                        break;
                    case $aatl_ib.model.gui.PanelTypeCode.ClientSearch:
                        openClientSearch(panel);
                        break;
                }

                controlId = panel.controlId;
            }

            if (controlId) {
                component.showView(controlId);
            }
        };

        function findPanel(title) {
            return panelComponents.find(function (panel) {
                return panel.title === title;
            });
        }

        function createPanel(panelTypeCode, actionItem) {

            let panel = new $aatl_ib.model.gui.Panel(panelTypeCode, $aatl_ib.utils.createUniqueId());

            if (actionItem !== undefined && actionItem !== null) {

                panel.setLinkedActionItem(actionItem);
                actionItem.linkedPanel = panel;
            }

            component.addView(panel.controlId);

            return panel;
        }

        function openHome(actionItem) {

            let panel = null;

            if (actionItem.linkedPanel === undefined) {

                panel = createPanel($aatl_ib.model.gui.PanelTypeCode.Home, actionItem);

                panel.controller = new $aatl_ib.gui.HomeController(panel.controlId, component.getCenterView());

                panel.controller.init();

            } else {
                panel = actionItem.linkedPanel;
            }

            component.showView(panel.controlId);
        }

        function openClientSearch(actionItem) {

            let panel = null;

            if (actionItem.linkedPanel === undefined) {

                panel = createPanel($aatl_ib.model.gui.PanelTypeCode.ClientSearch, actionItem);

                panel.controller = new $aatl_ib.gui.ClientSearchController(panel.controlId, component.getCenterView());

                panel.controller.init();

            } else {
                panel = actionItem.linkedPanel;
            }

            component.showView(panel.controlId);
        }
    }

    return MainController;
}());