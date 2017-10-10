<%@ page import="com.moniday.enums.Currency; com.moniday.enums.Country; com.moniday.enums.Nationality" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="theme"/>
    <title>Moniday | Personal Detail</title>
    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>
</head>

<body>
<div class="bs-docs-section clearfix">
    bankCOS: ${bankCOS}
    <div class="row">
        <div class="col-lg-3"></div>

        <div class="col-lg-6">
            <div class="well bs-component">
                <form action="${createLink(controller: 'account', action: 'saveAccountDetail')}"
                      class="form-horizontal" method="POST" id="personalDetail">
                    <g:hiddenField name="uniqueId" value="${uniqueId}"/>
                    <fieldset>
                        <legend>
                            <g:message code="user.account.detail"/>
                        </legend>

                        <div class="form-group">
                            <label for="bankName" class="col-lg-3 control-label">
                                <g:message code="user.account.bank.name"/>
                            </label>

                            <div class="col-lg-9">
                                <input type="text" class="form-control" id="bankName" name="bankName"
                                       placeholder="${message(code: 'user.account.bank.name')}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="bankUsername" class="col-lg-3 control-label">
                                <g:message code="user.account.bank.username"/>
                            </label>

                            <div class="col-lg-9">
                                <input type="text" class="form-control" id="bankUsername" name="bankUsername"
                                       placeholder="${message(code: 'user.account.bank.username')}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="bankPassword" class="col-lg-3 control-label">
                                <g:message code="user.account.bank.password"/>
                            </label>

                            <div class="col-lg-9">
                                <input type="password" class="form-control" id="bankPassword" name="bankPassword"
                                       placeholder="${message(code: 'user.account.bank.password')}">
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
                </form>
            </div>
        </div>

        <div class="col-lg-3"></div>
    </div>
</div>
<script>
    $(document).ready(function () {
        $("#personalDetail").validate({
            rules: {
                bankName: {
                    required: true
                },
                bankUsername: {
                    required: true
                },
                bankPassword: {
                    required: true
                }
            },
            messages: {
                bankName: {
                    required: "Bank Name is required"
                },
                bankUsername: {
                    required: "Bank Username is required"
                },
                bankPassword: {
                    required: "Bank Password is required"
                }
            }
        });
    });
</script>
</body>
</html>
