<%@ page import="com.moniday.enums.Currency; com.moniday.enums.Country;" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="theme"/>
    <title>Moniday | Debit Mandate Detail</title>
    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>
</head>

<body>
<div class="bs-docs-section clearfix">
    <div class="row">
        <div class="col-lg-3"></div>

        <div class="col-lg-6">
            <div class="well bs-component">
                <form action="${createLink(controller: 'account', action: 'saveDebitMandateDetail')}"
                      class="form-horizontal" method="POST" id="debitMandateDetail">
                    <g:hiddenField name="uniqueId" value="${uniqueId}"/>
                    <fieldset>
                        <legend>
                            <g:message code="user.debit.mandate.detail"/>
                        </legend>

                        <div class="form-group">
                            <label for="owner" class="col-lg-3 control-label">
                                <g:message code="user.debit.mandate.owner"/>
                            </label>

                            <div class="col-lg-9">
                                <input type="text" class="form-control" id="owner" name="owner"
                                       placeholder="${message(code: 'user.debit.mandate.owner')}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="addressLine1" class="col-lg-3 control-label">
                                <g:message code="user.debit.mandate.address.line.one"/>
                            </label>

                            <div class="col-lg-9">
                                <input type="text" class="form-control" id="addressLine1" name="addressLine1"
                                       placeholder="${message(code: 'user.debit.mandate.address.line.one')}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="city" class="col-lg-3 control-label">
                                <g:message code="user.debit.mandate.city"/>
                            </label>

                            <div class="col-lg-9">
                                <input type="text" class="form-control" id="city" name="city"
                                       placeholder="${message(code: 'user.debit.mandate.city')}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="region" class="col-lg-3 control-label">
                                <g:message code="user.debit.mandate.region"/>
                            </label>

                            <div class="col-lg-9">
                                <input type="text" class="form-control" id="region" name="region"
                                       placeholder="${message(code: 'user.debit.mandate.region')}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="postCode" class="col-lg-3 control-label">
                                <g:message code="user.debit.mandate.post.code"/>
                            </label>

                            <div class="col-lg-9">
                                <input type="text" class="form-control" id="postCode" name="postCode"
                                       placeholder="${message(code: 'user.debit.mandate.post.code')}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="country" class="col-lg-3 control-label">
                                <g:message code="user.debit.mandate.country"/>
                            </label>

                            <div class="col-lg-9">
                                <g:select class="form-control" from="${Country.list()}" name="country" value=""
                                          noSelection="${["null": "--Select Country--"]}" optionKey="key"
                                          optionValue="value"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="iban" class="col-lg-3 control-label">
                                <g:message code="user.debit.mandate.iban"/>
                            </label>

                            <div class="col-lg-9">
                                <input type="text" class="form-control" id="iban" name="iban"
                                       placeholder="${message(code: 'user.debit.mandate.iban')}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="bic" class="col-lg-3 control-label">
                                <g:message code="user.debit.mandate.bic"/>
                            </label>

                            <div class="col-lg-9">
                                <input type="text" class="form-control" id="bic" name="bic"
                                       placeholder="${message(code: 'user.debit.mandate.bic')}">
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
                owner: {
                    required: true
                },
                addressLine1: {
                    required: true
                },
                city: {
                    required: true
                },
                region: {
                    required: true
                },
                postCode: {
                    required: true
                },
                iban: {
                    required: true
                },
                bic: {
                    required: true
                }
            },
            messages: {
                owner: {
                    required: "Owner is required"
                },
                addressLine1: {
                    required: "LastName is required"
                },
                city: {
                    required: "City is required"
                },
                region: {
                    required: "region is required"
                },
                postCode: {
                    required: "Currency is required"
                },
                iban: {
                    required: "iban is required"
                },
                bic: {
                    required: "bic is required"
                }
            }
        });
    });
</script>
</body>
</html>
