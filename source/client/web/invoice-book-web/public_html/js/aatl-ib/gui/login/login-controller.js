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
            
             /*
                 AuthService.login(LoginViewController.getLoginId(), LoginViewController.getPassword(),
                 function (err, user) {
                 
                 LoginViewController.loginProcess().prop('hidden', true);
                 
                 if (err) {
                 LoginViewController.errorControl.show(err);
                 } else {
                 ViewController.setMainContainer("views/main-container.html", MainController.init);
                 }
                 });*/
        }
    }

    return LoginController;
}());