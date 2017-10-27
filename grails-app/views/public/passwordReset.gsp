<!doctype html>
<html>
<head>
    <meta name="layout" content="theme"/>
    <title>Moniday | Reset Password</title>
</head>

<body>

<div class="bs-docs-section clearfix">
    <div class="row">

        <div class="col-lg-3">
            <div class="alert alert-error" style="display: block">${flash.warn}</div>
        </div>

        <div class="col-lg-6">
            <div class="well bs-component">
                <form action="${createLink(controller: 'public', action: 'forgetPassword')}" method="POST"
                      id="passwordResetForm" autocomplete="off">
                    <fieldset>
                        <legend>
                            <g:message code="user.password.reset"/>
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
                            <div class="col-lg-3"></div>

                            <div class="col-lg-4">
                                <button type="submit" class="btn btn-default">
                                    <g:message code="form.submit"/>
                                </button>
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