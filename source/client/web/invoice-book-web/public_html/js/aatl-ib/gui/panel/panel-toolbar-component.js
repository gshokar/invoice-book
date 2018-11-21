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


$aatl_ib.gui.PanelToolbarComponent = (function () {

    function PanelToolbarComponent(componentId) {

        let id = componentId;
        let controlId = "#" + id;
        
        let toolbarComponentId = id + "-toolbar";
        let toolbarControlId = "#" + toolbarComponentId;
        let toolbarItems = [];
        let onClickAction = null;

        let toolbarView = '<div class="collapse navbar-collapse"><ul class="navbar-nav mr-auto" id="' + toolbarComponentId + '"></ul></div>';

        //let actionGroupComponent = new $aatl_ib.gui.ActionGroupComponent("list-documents", getComponent);

        function getComponent() {
            return $(controlId);
        }

        function getToolbarComponent() {
            return getComponent().find(toolbarControlId);
        }

        function addToolbarItemControl(toolbarItem) {

            getToolbarComponent().append('<li class="nav-item"><a class="nav-link" href="#" id="' + toolbarItem.id + '">' + toolbarItem.text + '</a></li>');
        }

        function getToolbarItemControl(id) {
            return getToolbarComponent().find(id);
        }

        function onClickActionItem(actionItem) {

            if (onClickAction && typeof onClickAction === 'function') {
                onClickAction(actionItem);
            }
        }

        this.init = function () {
            getComponent().append(toolbarView);
        };

        this.registerOnClickActionItem = function (action) {

            onClickAction = action;

        };

        this.addActionItem = function (toolbarItem) {

            toolbarItems.push(toolbarItem);

            addToolbarItemControl(toolbarItem);

            var control = getToolbarItemControl(toolbarItem.controlId);

            if (control) {
                control.on('click', function (e) {
                    e.preventDefault();

                    let itemId = $(this).attr("id");

                    let actionItem = toolbarItems.find(function (item) {
                        return item.id === itemId;
                    });

                    onClickActionItem(actionItem);
                });
            }
        };
        
        this.addNewActionItem = function(actionText, actionType){
          let toolbarItem = new $aatl_ib.model.gui.PanelToolbarItem(actionText, $aatl_ib.utils.createUniqueId(), actionType, null);  
          
          this.addActionItem(toolbarItem);
        };
        
        this.getControl = function(){
            
            return getToolbarComponent();
        };
    }

    return PanelToolbarComponent;
}());