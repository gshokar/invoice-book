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


$aatl_ib.ErrorComponent = (function(){
    
    function ErrorComponent(id, parent){
        
        var messageControlId = '#errorMessage';
        
        function getComponent(){
            
            return ((parent) ? parent.find(id) : $(id));
        }
        
        function getMessageControl(){ 
          return getComponent().find(messageControlId); 
        };
        
        function setMessage(message){
            getMessageControl().text(message);
        };
        
        this.show = function(message){
            setMessage(message);
            getComponent().prop('hidden', false);
        };
        
        this.hide = function(){
            getComponent().prop('hidden', true);
            setMessage('');
        };
    };
    
    return ErrorComponent;
}());