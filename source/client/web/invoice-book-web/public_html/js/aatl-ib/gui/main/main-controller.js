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
                    case $aatl_ib.gui.ActionItemTypeCode.Home:
                        controlId = openHome(actionItem);
                        break;
                    case $aatl_ib.gui.ActionItemTypeCode.ClientSearch:
                        controlId = openClientSearch(actionItem);
                        break;
                    case $aatl_ib.gui.ActionItemTypeCode.ClientDetail:
                        controlId = openClientDetail(actionItem);
                        break;
                    case $aatl_ib.gui.ActionItemTypeCode.EmployeeSearch:
                        controlId = openEmployeeSearch(actionItem);
                        break;
                    case $aatl_ib.gui.ActionItemTypeCode.EmployeeDetail:
                        controlId = openEmployeeDetail(actionItem);
                        break;
                    case $aatl_ib.gui.ActionItemTypeCode.TimeCodes:
                        controlId = openTimeCodes(actionItem);
                        break;
                    case $aatl_ib.gui.ActionItemTypeCode.TimeSheet:
                        controlId = openTimeSheet(actionItem);
                        break;
                    case $aatl_ib.gui.ActionItemTypeCode.InvoiceSearch:
                        controlId = openInvoiceSearch(actionItem);
                        break;
                    case $aatl_ib.gui.ActionItemTypeCode.InvoiceDetail:
                        controlId = openInvoiceDetail(actionItem);
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

            if (actionItem.icon === undefined) {
                actionItem.icon = $aatl_ib.gui.ActionItemIcon.getIcon(actionItem.typeCode);
            }
            component.addActionItem(actionItem);

            $aatl_ib.gui.replaceIcons();
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

            actionItem.icon = "user";

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

            actionItem.icon = "user";

            let panel = createPanel($aatl_ib.model.gui.PanelTypeCode.EmployeeDetail, actionItem);

            panel.controller = new $aatl_ib.gui.EmployeeDetailController(panel.controlId, component.getCenterView());
            panel.controller.setTitle(actionItem.text);
            panel.controller.setEmployeeNumber(actionItem.data);

            panel.controller.init();

            return panel.controlId;
        }

        function openTimeCodes(actionItem) {

            let panel = createPanel($aatl_ib.model.gui.PanelTypeCode.TimeCodes, actionItem);

            panel.controller = new $aatl_ib.gui.TimeCodesController(panel.controlId, component.getCenterView());

            panel.controller.init();

            return panel.controlId;
        }

        function openTimeSheet(actionItem) {

            let panel = createPanel($aatl_ib.model.gui.PanelTypeCode.TimeSheet, actionItem);

            panel.controller = new $aatl_ib.gui.TimeSheetController(panel.controlId, component.getCenterView());

            panel.controller.init();

            return panel.controlId;
        }

        function openInvoiceSearch(actionItem) {

            let panel = createPanel($aatl_ib.model.gui.PanelTypeCode.InvoiceSearch, actionItem);

            panel.controller = new $aatl_ib.gui.InvoiceSearchController(panel.controlId, component.getCenterView());

            panel.controller.init();

            return panel.controlId;
        }
        
        function openInvoiceDetail(actionItem) {

            actionItem.icon = "dollar-sign";

            let panel = createPanel($aatl_ib.model.gui.PanelTypeCode.Invoice, actionItem);

            panel.controller = new $aatl_ib.gui.InvoiceDetailController(panel.controlId, component.getCenterView());
            panel.controller.setTitle(actionItem.text);
            panel.controller.setInvoiceNumber(actionItem.data);

            panel.controller.init();

            return panel.controlId;
        }
        
        this.init = function () {

            component.init();
            component.bindEvents(actionItemClicked);
            component.setHomeView();
            $aatl_ib.gui.replaceIcons();
        };

        this.openPanel = function (actionItem) {
            let existingPanel = findPanel(actionItem.text);

            if (existingPanel) {

                component.selectActionItem(existingPanel.linkedActionItem);

            } else {

                openActionItemPanel(actionItem);
            }

        };

        this.closePanel = function(panelTitle){
            let panel = findPanel(panelTitle);
            
            if(panel){

                component.removeActionItem(panel.linkedActionItem);
                component.removeView(panel.controlId);
                
                let index = panels.findIndex(function(value){
                   return value.controlId === panel.controlId; 
                });
                
                if(index >= 0){
                    panels.splice(index, 1);
                    
                    index--;
                }
                
                if(panels.length > 0){
                    if(index < 0){
                        index = 0;
                    }
                   
                    component.selectActionItem(panels[index].linkedActionItem);
                }
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