<!doctype html>
<html>
<head>
    <meta name="layout" content="theme"/>
    <title>Moniday | Add Bank</title>
    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>
</head>

<body>

<div class="bs-docs-section clearfix">
    <div class="row">

        <div class="col-lg-3"></div>

        <div class="col-lg-6">
            <div class="well bs-component">
                <form action="${createLink(controller: 'admin', action: 'saveBanks')}" method="POST"
                      id="passwordResetForm" autocomplete="off" class="form-horizontal">
                    <fieldset>
                        <legend>
                            <g:message code="admin.bank.add"/>
                        </legend>

                        <div class="form-group">
                            <label for="bankname" class="col-lg-2 control-label">
                                <g:message code="admin.bank.name"/>
                            </label>

                            <div class="col-lg-10">
                                <input type="text" class="form-control" id="bankname" name="bankName"
                                       placeholder="${message(code: 'admin.bank.name')}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="bankurl" class="col-lg-2 control-label">
                                <g:message code="admin.bank.url"/>
                            </label>

                            <div class="col-lg-10">
                                <input type="url" class="form-control" id="bankurl" name="bankURL"
                                       placeholder="${message(code: "admin.bank.url")}">
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