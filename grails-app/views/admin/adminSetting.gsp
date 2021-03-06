<!doctype html>
<html>
<head>
    <meta name="layout" content="theme"/>
    <title>Moniday | Admin Setting</title>
    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>
</head>

<body>

<div class="bs-docs-section clearfix">
    <div class="row">

        <div class="col-lg-2"></div>

        <div class="col-lg-8">
            <div class="well bs-component">
                <form action="${createLink(controller: 'admin', action: 'updateAdminSetting')}" method="POST"
                      id="passwordResetForm" autocomplete="off" class="form-horizontal">
                    <fieldset>
                        <legend>
                            <g:message code="admin.setting"/>
                        </legend>


                        <div class="form-group">
                            <label for="mangourl" class="col-lg-2 control-label">
                                <g:message code="admin.setting.mangopay.url"/>
                            </label>

                            <div class="col-lg-10">
                                <input type="url" class="form-control" id="mangourl" name="mangoPayUrl"
                                       value="${adminSetting.mangoPayUrl}" disabled>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="mangoclientid" class="col-lg-2 control-label">
                                <g:message code="admin.setting.mangopay.clientid"/>
                            </label>

                            <div class="col-lg-10">
                                <input type="text" class="form-control" id="mangoclientid" name="mangoPayClientId"
                                       value="${adminSetting.mangoPayClientId}" disabled>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="mangoemailid" class="col-lg-2 control-label">
                                <g:message code="admin.setting.mangopay.emailid"/>
                            </label>

                            <div class="col-lg-10">
                                <input type="email" class="form-control" id="mangoemailid" name="mangoPayEmailId"
                                       value="${adminSetting.mangoPayEmailId}" disabled>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="mangopasskey" class="col-lg-2 control-label">
                                <g:message code="admin.setting.mangopay.passkey"/>
                            </label>

                            <div class="col-lg-10">
                                <input type="text" class="form-control" id="mangopasskey" name="mangoPayPassKey"
                                       value="${adminSetting.mangoPayPassKey}" disabled>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="sendgridusername" class="col-lg-2 control-label">
                                <g:message code="admin.setting.sendgrid.username"/>
                            </label>

                            <div class="col-lg-10">
                                <input type="text" class="form-control" id="sendgridusername" name="sendgridUsername"
                                       value="${adminSetting.sendgridUsername}" disabled>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="sendgridpassword" class="col-lg-2 control-label">
                                <g:message code="admin.setting.sendgrid.password"/>
                            </label>

                            <div class="col-lg-10">
                                <input type="password" class="form-control" id="sendgridPassword"
                                       name="sendgridpassword"
                                       value="${adminSetting.sendgridPassword}" disabled>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="sendgridemail" class="col-lg-2 control-label">
                                <g:message code="admin.setting.sendgrid.sender.emailid"/>
                            </label>

                            <div class="col-lg-10">
                                <input type="email" class="form-control" id="sendgridemail" name="sendgridEmail"
                                       value="${adminSetting.sendgridEmail}" disabled>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="firebaseserverurl" class="col-lg-2 control-label">
                                <g:message code="admin.setting.firebase.server.url"/>
                            </label>

                            <div class="col-lg-10">
                                <input type="url" class="form-control" id="firebaseserverurl" name="firebaseServerUrl"
                                       value="${adminSetting.firebaseServerUrl}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="minDeductionAmount" class="col-lg-2 control-label">
                                <g:message code="admin.setting.deduction.amount.min"/>
                            </label>

                            <div class="col-lg-10">
                                <input type="number" class="form-control" id="minDeductionAmount"
                                       name="minDeductionAmount"
                                       value="${adminSetting.minDeductionAmount}" step="0.5">
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-lg-10 col-lg-offset-4">

                                <div class="col-lg-2">
                                    <button type="submit" class="btn btn-default">
                                        <g:message code="form.upade"/>
                                    </button>
                                </div>

                            </div>
                        </div>

                    </fieldset>
                </form>
            </div>
        </div>

        <div class="col-lg-2"></div>
    </div>
</div>
</body>
</html>