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

    function ActionGroupComponent(props) {

        let component = new $aatl_ib.gui.Component(props);
        
        let actionItems = [];
        let onClickActions = [];
        let activeItem = undefined;
        let actionItemView = function (actionItem) {
            return '<a class="list-group-item list-group-item-action" id="' + actionItem.id + '" data-toggle="list" href="#" role="tab">';
        };

        function getComponent() {
            
            return component.getControl();
        }

        function getActionItemControl(id) {
            return getComponent().find(id);
        }

        function addActionItemControl(actionItem) {
            let actionElement = actionItemView(actionItem);

            if (actionItem.icon !== undefined && actionItem.icon !== null) {
                actionElement = actionElement + '<span data-feather="' + actionItem.icon + '"></span>';
            }
            actionElement = actionElement + actionItem.text + '</a>';

            getComponent().append(actionElement);
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

                if (control) {
                    control.trigger('click');
                }
            }
        };

        this.updateActionItemText = function (actionItem) {

            if (actionItem && actionItem.controlId) {
                let control = getActionItemControl(actionItem.controlId);

                if (control) {
                    control.text(actionItem.text);
                }
            }
        };

        this.setActionItemViewTemplate = function (view) {

            if (typeof view === 'function') {
                actionItemView = view;
            }
        };
        
        this.updateElementId = function (element) {
            return component.updateElementId(element);
        };
        
        this.getSelectedActionItem = function(){
            return activeItem;
        };
    }

    return ActionGroupComponent;
}());