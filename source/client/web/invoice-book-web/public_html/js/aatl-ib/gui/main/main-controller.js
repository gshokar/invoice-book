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

        let sidebarComponent = new $aatl_ib.SidebarComponent('sidebar');
        let centerComponent = new $aatl_ib.gui.ContainerComponent('centerView');

        let panelComponents = [];

        function actionItemClicked(actionItem){
            
            switch(actionItem.typeCode){
                case $aatl_ib.model.gui.ActionItemTypeCode.Home:
                    openHome(actionItem);
                    break;
                case $aatl_ib.model.gui.ActionItemTypeCode.ClientSearch:
                    openClientSearch(actionItem);
                    break;
            }
        }
        
        this.init = function () {

            sidebarComponent.bindEvents();
            //centerComponent.bindEvents();
            
            let homeItem = new $aatl_ib.model.gui.ActionItem("Home", $aatl_ib.utils.createUniqueId(), $aatl_ib.model.gui.ActionItemTypeCode.Home);
            let clientSearchItem = new $aatl_ib.model.gui.ActionItem("Client Search", $aatl_ib.utils.createUniqueId(), $aatl_ib.model.gui.ActionItemTypeCode.ClientSearch);
            
            sidebarComponent.getActionGroupComponent().addActionItem(homeItem);
            sidebarComponent.getActionGroupComponent().addActionItem(clientSearchItem);

            sidebarComponent.getActionGroupComponent().registerOnClickActionItem(actionItemClicked);
            
            sidebarComponent.getActionGroupComponent().selectActionItem(homeItem);
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
                centerComponent.showView(controlId);
            }
        };

        function findPanel(title) {
            return panelComponents.find(function (panel) {
                return panel.title === title;
            });
        }
        
        function addPanel(panel){
            panel.controlId = $aatl_ib.utils.createUniqueId();
            panelComponents.push(panel);

            centerComponent.addView(panel.controlId);
        }
        
        function openHome(actionItem) {

            if(actionItem.viewComponent === undefined){
                actionItem.viewComponent = new $aatl_ib.gui.Component($aatl_ib.utils.createUniqueId(), centerComponent.getComponent);
                centerComponent.addView(actionItem.viewComponent.getId());
                actionItem.viewComponent.getControl().load($aatl_ib.viewController.getViewUrl("home"));
            }
            
            centerComponent.showView(actionItem.viewComponent.getId());
        }

        function openClientSearch(actionItem) {
            
            if(actionItem.viewComponent === undefined){
                actionItem.viewComponent = new $aatl_ib.gui.Component($aatl_ib.utils.createUniqueId(), centerComponent.getComponent);
                centerComponent.addView(actionItem.viewComponent.getId());
                actionItem.viewComponent.getControl().load($aatl_ib.viewController.getViewUrl("clientSearch"));
            }
            centerComponent.showView(actionItem.viewComponent.getId());
        }
    }

    return MainController;
}());