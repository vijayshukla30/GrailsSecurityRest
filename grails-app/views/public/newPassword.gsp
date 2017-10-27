<!doctype html>
<html>
<head>
    <meta name="layout" content="theme"/>
    <title>Moniday | Reset Password</title>
    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>
</head>

<body>

<div class="bs-docs-section clearfix">
    <div class="row">

        <div class="col-lg-3"></div>

        <div class="col-lg-6">
            <div class="well bs-component">
                <form action="${createLink(controller: 'public', action: 'saveResetPassword')}" method="POST"
                      id="passwordResetForm"
                      autocomplete="off">
                    <fieldset>
                        <legend>
                            <g:message code="user.password.reset"/>
                        </legend>

                        <g:hiddenField name="name" value="${name}"/>

                        <div class="form-group">
                            <label for="password" class="col-lg-2 control-label">
                                <g:message code="user.password"/>
                            </label>

                            <div class="col-lg-10">
                                <input type="password" class="form-control" id="password" name="password"
                                       placeholder="${message(code: 'user.password')}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="password" class="col-lg-2 control-label">
                                <g:message code="user.confirm.password"/>
                            </label>

                            <div class="col-lg-10">
                                <input type="password" class="form-control" id="confirmpassword" name="confirmpassword"
                                       placeholder="${message(code: 'user.confirm.password')}">
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-lg-10 col-lg-offset-4">

                                <div class="col-lg-2">
                                    <button type="submit" class="btn btn-default">
                                        <g:message code="form.submit"/>
                                    </button>
                                </div>

                            </div>
                        </div>

                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>