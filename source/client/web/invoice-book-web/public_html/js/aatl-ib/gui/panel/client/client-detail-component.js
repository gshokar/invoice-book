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
        let client = null;
        let toolbar = null;
        let onToolbarItemClicked = null;
        let afterInit = null;
        
        function afterLoad() {
            toolbar = new $aatl_ib.gui.PanelToolbarComponent("clientDetailPanelToolbar", component.getControl);
            toolbar.init();
            toolbar.registerOnClickActionItem(onToolbarItemClicked);
            addToolbarItems();
            
            loadProvinceDropdownOptions();
            
            if(afterInit !== null && typeof afterInit === "function"){
                afterInit();
            }
        }

        function addToolbarItems() {

            toolbar.addNewActionItem("Save", $aatl_ib.model.gui.PanelToolbarItemTypeCode.Save);
            toolbar.addNewActionItem("Close", $aatl_ib.model.gui.PanelToolbarItemTypeCode.Close);
        };

        function getControl(){
            return component.getControl();
        }
        function getNumberField(){
            return getControl().find("#clientNumber");
        }
        
        function getNameField(){
            return getControl().find("#clientName");
        }
        
        function getAddress1Field(){
            return getControl().find("#clientAddress1");
        }
        
        function getAddress2Field(){
            return getControl().find("#clientAddress2");
        }
        
        function getCityField(){
            return getControl().find("#clientCity");
        }
        
        function getProvinceField(){
            return getControl().find("#clientProvince");
        }
        
        function getPostalCodeField(){
            return getControl().find("#clientPostalCode");
        }
        
        function getEmailField(){
            return getControl().find("#clientEmail");
        }
        
        function getPhoneField(){
            return getControl().find("#clientPhoneNumber");
        }
        
        function getTitleControl(){
            return getControl().find("#panelTitle");
        }
        
        function getErrorControl(){
            return getControl().find(".alert-danger");
        }
        
        function loadProvinceDropdownOptions(){
            $aatl_ib.LookupService.loadProvinces(getProvinceField());
        }
        
        function onClientChanged(){
           
           if(client === null || client === undefined){
               getNumberField().val("");
               getNameField().val("");
               getAddress1Field().val("");
               getAddress2Field().val("");
               getCityField().val("");
               //getProvinceField().val("");
               getPostalCodeField().val("");
               getPhoneField().val("");
               getEmailField().val("");
           }else{
               getNumberField().val(client.number);
               getNameField().val(client.name);
               getAddress1Field().val(client.address.address1);
               getAddress2Field().val(client.address.address2);
               getCityField().val(client.address.city);
               getProvinceField().val(client.address.province);
               getPostalCodeField().val(client.address.postalCode);
               getPhoneField().val(client.contact.phone);
               getEmailField().val(client.contact.email);
           }
        }
        
         function updateComponentIds(html) {
            return errorComponent.updateComponentIds(html);
        }
        
        this.init = function () {
            getControl().load($aatl_ib.viewController.getViewUrl("client-detail"), afterLoad);

        };

        this.registerOnToolbarItemClicked = function (actionItemClicked) {
            onToolbarItemClicked = actionItemClicked;
        };

        this.getComponent = function () {

            return getControl();
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
        
        this.setClient = function(value){
            client = value;
            onClientChanged();
        };
        
        this.setTitle = function(title){
          
            getTitleControl().text(title);
        };
        
        this.setAfterInit = function(callback){
            afterInit = callback;
        };
        
        this.showError = function(err){
            let control = getErrorControl();
            
            control.empty();
            
            $.each(err.messages, function(index, message){ 
                control.append(message);
                
                if(index < (err.messages.length -1) ){
                    control.append("</br>");
                }
            });
            
            control.prop("hidden", false);
        };
        
        this.hideError = function(){
          getErrorControl().prop("hidden", true);
        };
    }
    
    return ClientDetailComponent;
}());

