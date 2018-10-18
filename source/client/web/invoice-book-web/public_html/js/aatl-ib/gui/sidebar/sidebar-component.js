/* 
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Jan-22  GShokar         Created
 * =============================================================================
 */


$aatl_ib.SidebarComponent = (function () {

    function SidebarComponent(componentId) {
        
        let id = componentId;
        let controlId = "#" + id;
        
        let actionGroupComponent = new $aatl_ib.gui.ActionGroupComponent("list-documents", getComponent);
        
        function getComponent() {
            return $(controlId);
        }

        function getSidebarCollasped() {
            return $("#sidebar-collapsed");
        }
       
        function toggleSidebar() {

            let sidebar = getComponent();
            let sidebarCollasped = getSidebarCollasped();

            sidebar.prop("hidden", !sidebar.prop("hidden"));
            sidebarCollasped.prop("hidden", !sidebarCollasped.prop("hidden"));
        }

        this.bindEvents = function () {
            
            $("#sidebarButtonCollapse").click(() => {
                toggleSidebar();
            });
            $("#sidebarButtonOpen").click(() => {
                toggleSidebar();
            });

        };
        
        this.getActionGroupComponent = function(){
            return actionGroupComponent;
        };
        
    }

    return SidebarComponent;
}());