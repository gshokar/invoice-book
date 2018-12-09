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


$aatl_ib.LoginController = (function () {
    function LoginController(parent) {

        var component = new $aatl_ib.LoginComponent();
        let parentComponent = parent;
        
        function initComponents(){
            component.setSubmitButtonEnabled(false);
            component.bindEvents(login);
            component.focus();
        }
        
        this.init = function () {
            initComponents();
        };
        
        this.loadView = function(html){
            parentComponent().html(component.updateComponentIds(html));
            initComponents();
        };
        
        function login(loginId, password) {
            
//             if (loginId === 'test' && password === "test") {
//             $aatl_ib.viewController.setMainView("mainComponent", new $aatl_ib.MainController());
//             } else {
//                 let err = {messages: ["Invalid loginId or password"]};
//             component.showError(err);
//             }
             

            $aatl_ib.AuthService.login(loginId, password,
                    function (err) {

                        if (err) {
                            component.showError(err);
                        } else {
                            $aatl_ib.viewController.setMainView("mainComponent", new $aatl_ib.MainController());
                        }
                    });
        }
    }

    return LoginController;
}());