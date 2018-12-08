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

"use strict"

$aatl_ib.ErrorComponent = (function(){
    
    function ErrorComponent(parent){
        
        let id = $aatl_ib.utils.createUniqueId();
        let viewId = 'errorMessages';
        
        let component = new $aatl_ib.gui.Component(viewId, parent,viewId);
        
        function getControl(){
          
            return component.getControl();
        };
                
        function setMessage(control, messages){
            
            $.each(messages, function(index, message){ 
                control.append($('<label></label>').text(message));
               
            });
            
        };
        
        this.show = function(messages){
            let control = getControl();
            
            control.empty();
            
            setMessage(control, messages);
            
            control.prop('hidden', false);
        };
        
        this.hide = function(){
            getControl().prop('hidden', true);
        };
        
        this.updateComponentIds = function(html){
          return component.updateElementId(html, id);
        };
    };
    
    return ErrorComponent;
}());