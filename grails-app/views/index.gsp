<!doctype html>
<html>
<head>
    <meta name="layout" content="theme"/>
    <title>Welcome to Moniday</title>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>
</head>

<body>
<div class="bs-docs-section clearfix">
    <div class="row">
        <div class="col-lg-3"></div>

        <div class="col-lg-6">
            <div class="well bs-component">
                <form action="${postUrl ?: '/login/authenticate'}" class="form-horizontal" method="POST" id="loginForm"
                      autocomplete="off">
                    <fieldset>
                        <legend>
                            <g:message code="user.login.form"/>
                        </legend>

                        <div class="form-group">
                            <label for="username" class="col-lg-2 control-label">
                                <g:message code="user.email"/>
                            </label>

                            <div class="col-lg-10">
                                <input type="email" class="form-control" id="username" name="username"
                                       placeholder="${message(code: 'user.email')}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="password" class="col-lg-2 control-label">
                                <g:message code="user.password"/>
                            </label>

                            <div class="col-lg-10">
                                <input type="password" class="form-control" id="password" name="password"
                                       placeholder="${message(code: 'user.password')}">

                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox">
                                        <g:message code="user.login.checkbox"/>
                                    </label>
                                </div>
                                <g:link controller="public" action="forgetPassword">
                                    <g:message code="user.forgot.password"/>
                                </g:link>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-lg-10 col-lg-offset-2">
                                <div class="col-lg-4">
                                    <button type="reset" class="btn btn-default">
                                        <g:message code="form.reset"/>
                                    </button>
                                </div>

                                <div class="col-lg-4">
                                    <g:submitButton name="login" value="${message(code: 'user.login')}"
                                                    class="btn btn-success"/>
                                </div>

                                <div class="col-lg-4">
                                    <g:link controller="public" action="register" class="btn btn-primary">
                                        <g:message code="user.register"/>
                                    </g:link>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>

        <div class="col-lg-3"></div>
    </div>
</div>
</body>
</html>
