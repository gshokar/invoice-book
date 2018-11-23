/* 
 * Copyright (c) 2018 Absolute Apogee Technologies Ltd. All rights reserved.
 * 
 * =============================================================================
 * Revision History:
 * Date         Author          Detail
 * -----------  --------------  ------------------------------------------------
 * 2018-Nov-22  GShokar         Created
 * =============================================================================
 */

"use strict";

$aatl_ib.gui.ClientDetailComponent = (function () {
    function ClientDetailComponent(componentId, parentComponent) {

        let component = new $aatl_ib.gui.Component(componentId, parentComponent);

        let toolbar = null;
        let onToolbarItemClicked = null;

        function afterLoad() {
            toolbar = new $aatl_ib.gui.PanelToolbarComponent("clientDetailPanelToolbar", component.getControl);
            toolbar.init();
            toolbar.registerOnClickActionItem(onToolbarItemClicked);
            addToolbarItems();
        }

        function addToolbarItems() {

            toolbar.addNewActionItem("Save", $aatl_ib.model.gui.PanelToolbarItemTypeCode.Save);
            toolbar.addNewActionItem("Close", $aatl_ib.model.gui.PanelToolbarItemTypeCode.Close);
        };

        function getNumberField(){
            return this.getComponent().find("#clientNumber");
        }
        
        function getNameField(){
            return this.getComponent().find("#clientName");
        }
        
        function getAddress1Field(){
            return this.getComponent().find("#clientAddress1");
        }
        
        function getAddress2Field(){
            return this.getComponent().find("#clientAddress2");
        }
        
        function getCityField(){
            return this.getComponent().find("#clientCity");
        }
        
        function getProvinceField(){
            return this.getComponent().find("#clientProvince");
        }
        
        function getPostalCodeField(){
            return this.getComponent().find("#clientPostalCode");
        }
        
        function getEmailField(){
            return this.getComponent().find("#clientEmail");
        }
        
        function getPhoneField(){
            return this.getComponent().find("#clientPhoneNumber");
        }
        
        this.init = function () {
            this.getComponent().load($aatl_ib.viewController.getViewUrl("client-detail"), afterLoad);

        };

        this.registerOnToolbarItemClicked = function (actionItemClicked) {
            onToolbarItemClicked = actionItemClicked;
        };

        this.getComponent = function () {

            return component.getControl();
        };
        
        this.getClient = function(){
            let client = {
                number: getNumberField().val(),
                name: getNameField().val(),
                address: {
                    address1: getAddress1Field().val(),
                    address2: getAddress2Field().val(),
                    city: getCityField().val(),
                    province: getProvinceField().val(),
                    postalCode: getPostalCodeField().val()
                },
                contact: {
                    phone: getPhoneField().val(),
                    email: getEmailField().val()
                }
            };
            
            return client;
        };
    }
    
    return ClientDetailComponent;
}());

