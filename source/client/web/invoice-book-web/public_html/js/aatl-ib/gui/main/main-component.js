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
                        
            homeItem = new $aatl_ib.gui.ActionItem({text: "Home", typeCode: $aatl_ib.gui.ActionItemTypeCode.Home, icon: "home", createId: true});
            let clientSearchItem = new $aatl_ib.gui.ActionItem({text: "Client Search", typeCode: $aatl_ib.gui.ActionItemTypeCode.ClientSearch, icon: "users", createId: true});
            let employeeSearchItem = new $aatl_ib.gui.ActionItem({text: "Employee Search", typeCode: $aatl_ib.gui.ActionItemTypeCode.EmployeeSearch, icon: "users", createId: true});
            let timecodesItem = new $aatl_ib.gui.ActionItem({text: "Time Codes", typeCode: $aatl_ib.gui.ActionItemTypeCode.TimeCodes, icon: "code", createId: true});
            
            sidebarComponent.getActionGroupComponent().addActionItem(homeItem);
            sidebarComponent.getActionGroupComponent().addActionItem(clientSearchItem);
            sidebarComponent.getActionGroupComponent().addActionItem(employeeSearchItem);
            sidebarComponent.getActionGroupComponent().addActionItem(timecodesItem);
            
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