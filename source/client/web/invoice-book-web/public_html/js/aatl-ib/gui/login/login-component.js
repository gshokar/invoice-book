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


$aatl_ib.LoginComponent = (function () {
    function LoginComponent() {

        function fieldLoginId() {
            return $("#loginId");
        };

        function fieldPassword() {
            return $("#loginPassword");
        };

        function getLoginId() {
            return fieldLoginId().val().trim();
        };

        function getPassword() {
            return fieldPassword().val().trim();
        };

        buttonLoginSubmit = function () {
            return $("#loginSubmit");
        };

        loginForm = function () {
            return $("#loginForm");
        };

        loginProcess = function () {
            return $("#loginProcess");
        };

        loginError = function () {
            return $("#loginError");
        };

        var hideLoginProcess = function (value) {
            loginProcess().prop('hidden', value);
        };

        isFormValid = function () {
            let loginId = getLoginId();
            let password = getPassword();

            return loginId && loginId.length > 0 && password && password.length > 0;
        };

        var errorComponent = new $aatl_ib.ErrorComponent();

        formControlValueChanged = function () {
            buttonLoginSubmit().prop('disabled', !isFormValid());
        };

        this.setSubmitButtonEnabled = function (value) {
            buttonLoginSubmit().prop('disabled', !value);
        };

        this.showError = function(err){
            hideLoginProcess(true);
            errorComponent.show(err.messages);
        };
        
        this.focus = function(){
            fieldLoginId().focus();
        };
        
        login = undefined; // function(loginId, password);

        this.bindEvents = function (loginCall) {
            
            login = loginCall;
            
            fieldLoginId().change(() => {
                formControlValueChanged();
            });

            fieldPassword().change(() => {
                formControlValueChanged();
            });

            loginForm().submit(function (event) {

                // Stop form from submitting normally
                event.preventDefault();
                //sidebarController.toggleSidebar();

                errorComponent.hide();

                hideLoginProcess(false);

                if(login){
                    login(getLoginId(), getPassword());
                }
               
            });
        };

        this.updateComponentIds = function (html) {
            return errorComponent.updateComponentIds(html);
        };
    }

    return LoginComponent;
}());