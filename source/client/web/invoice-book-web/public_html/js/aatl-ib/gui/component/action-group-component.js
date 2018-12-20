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

"use strict";

$aatl_ib.gui.ActionGroupComponent = (function () {

    function ActionGroupComponent(componentId, parentComponent) {

        let id = componentId;
        let controlId = "#" + id;

        let parent = parentComponent;

        let actionItems = [];
        let onClickActions = [];
        let activeItem = undefined;

        function getComponent() {

            if (parent && typeof parent === 'function') {
                return parent().find(controlId);
            }

            return $(controlId);
        }

        function getActionItemControl(id) {
            return getComponent().find(id);
        }

        function addActionItemControl(actionItem) {

            getComponent().append('<a class="list-group-item list-group-item-action" id="' + actionItem.id + '" data-toggle="list" href="#list-clientSearch" role="tab">' + actionItem.text + '</a>');
        }

        this.registerOnClickActionItem = function (action) {

            onClickActions.push(action);

        };

        function onClickActionItem(actionItem) {

            if (actionItem) {

                if (activeItem && activeItem.id === actionItem.id) {

                    return;

                }

                activeItem = actionItem;

                onClickActions.forEach(function (action) {
                    if (action && typeof action === 'function') {
                        action(actionItem);
                    }
                });
            }
        }

        this.addActionItem = function (actionItem) {
            //let actionItem = new $aatl_ib.model.gui.ActionItem(text, $aatl_ib.utils.createUniqueId(), panelId);

            actionItems.push(actionItem);

            addActionItemControl(actionItem);

            var control = getActionItemControl(actionItem.controlId);

            if (control) {
                control.on('click', function (e) {
                    e.preventDefault();

                    let actionItemId = $(this).attr("id");

                    let actionItem = actionItems.find(function (item) {
                        return item.id === actionItemId;
                    });

                    onClickActionItem(actionItem);
                });
            }
        };

        this.selectActionItem = function (actionItem) {
            //onClickActionItem(actionItem);

            if (actionItem && actionItem.controlId) {
                let control = getActionItemControl(actionItem.controlId);
                
                if(control){
                    control.trigger('click');
                }
            }
        };
        
        this.updateActionItemText = function(actionItem){
            
            if (actionItem && actionItem.controlId) {
                let control = getActionItemControl(actionItem.controlId);
                
                if(control){
                    control.text(actionItem.text);
                }
            }
        };
    }

    return ActionGroupComponent;
}());