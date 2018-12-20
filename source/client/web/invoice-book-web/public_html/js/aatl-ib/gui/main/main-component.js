/* 
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Nov-11  GShokar         Created
 * =============================================================================
 */


$aatl_ib.gui.MainComponent = (function () {

    function MainComponent() {

        let sidebarComponent = new $aatl_ib.SidebarComponent('sidebar');
        let centerComponent = new $aatl_ib.gui.ContainerComponent('centerView');
        let homeItem = null;
        
        this.init = function () {

            sidebarComponent.bindEvents();
                        
            homeItem = new $aatl_ib.model.gui.ActionItem("Home", $aatl_ib.model.gui.ActionItemTypeCode.Home, $aatl_ib.utils.createUniqueId());
            let clientSearchItem = new $aatl_ib.model.gui.ActionItem("Client Search", $aatl_ib.model.gui.ActionItemTypeCode.ClientSearch, $aatl_ib.utils.createUniqueId());
            
            sidebarComponent.getActionGroupComponent().addActionItem(homeItem);
            sidebarComponent.getActionGroupComponent().addActionItem(clientSearchItem);
            
        };

        this.bindEvents = function(actionItemClicked){
            sidebarComponent.getActionGroupComponent().registerOnClickActionItem(actionItemClicked);
        };
        
        this.showView = function(controlId){
            centerComponent.showView(controlId);
        };
        
        this.addView = function(controlId){
            centerComponent.addView(controlId);
        };

        this.getCenterView = function(){
          return centerComponent.getComponent();  
        };
        
        this.setHomeView = function(){
            sidebarComponent.getActionGroupComponent().selectActionItem(homeItem);
        };
        
        this.addActionItem = function(actionItem){
            sidebarComponent.getActionGroupComponent().addActionItem(actionItem);
            sidebarComponent.getActionGroupComponent().selectActionItem(actionItem);
        };
        
        this.selectActionItem = function(actionItem){
            sidebarComponent.getActionGroupComponent().selectActionItem(actionItem);
        };
        
        this.updateActionItemText = function(actionItem){
            sidebarComponent.getActionGroupComponent().updateActionItemText(actionItem);
        };
    }

    return MainComponent;
}());