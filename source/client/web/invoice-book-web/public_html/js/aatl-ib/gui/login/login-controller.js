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
    function LoginController() {

        var component = new $aatl_ib.LoginComponent();

        this.init = function () {
            component.setSubmitButtonEnabled(false);
            component.bindEvents(login);
            component.focus();
        };

        function login(loginId, password) {
            
             if (loginId === 'test' && password === "test") {
             $aatl_ib.viewController.setMainView("mainComponent", new $aatl_ib.MainController());
             } else {
             component.showError("Invalid loginId or password");
             }
             

//            $aatl_ib.AuthService.login(loginId, password,
//                    function (err) {
//
//                        if (err) {
//                            component.showError(err);
//                        } else {
//                            $aatl_ib.viewController.setMainView("mainComponent", new $aatl_ib.MainController());
//                        }
//                    });
        }
    }

    return LoginController;
}());