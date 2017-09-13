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
                <g:form controller="public" action="saveRegisterUser" class="form-horizontal" method="POST">
                    <fieldset>
                        <legend>
                            <g:message code="user.register.form"/>
                        </legend>

                        <div class="form-group">
                            <label for="username" class="col-lg-3 control-label">
                                <g:message code="user.email"/>
                            </label>

                            <div class="col-lg-9">
                                <input type="email" class="form-control" id="username" name="username"
                                       placeholder="${message(code: 'user.email')}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="password" class="col-lg-3 control-label">
                                <g:message code="user.password"/>
                            </label>

                            <div class="col-lg-9">
                                <input type="password" class="form-control" id="password" name="password"
                                       placeholder="${message(code: 'user.password')}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="confirmPassword" class="col-lg-3 control-label">
                                <g:message code="user.confirm.password"/>
                            </label>

                            <div class="col-lg-9">
                                <input type="password" class="form-control" id="confirmPassword" name="confirmPassword"
                                       placeholder="${message(code: 'user.confirm.password')}">
                            </div>
                        </div>


                        <div class="form-group">
                            <div class="col-lg-8 col-lg-offset-4">
                                <button type="reset" class="btn btn-default">
                                    <g:message code="form.reset"/>
                                </button>
                                <button type="submit" class="btn btn-primary">
                                    <g:message code="form.submit"/>
                                </button>
                            </div>
                        </div>
                    </fieldset>
                </g:form>
            </div>
        </div>

        <div class="col-lg-3"></div>
    </div>
</div>
</body>
</html>
