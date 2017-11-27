<!doctype html>
<html>
<head>
    <meta name="layout" content="theme"/>
    <title>Moniday | Conform Transactions</title>
    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>
</head>

<body>

<div class="bs-docs-section clearfix">
    <div class="row">

        <div class="col-lg-2"></div>

        <div class="col-lg-8">

            <div class="well bs-component">
                <form action="${createLink(controller: 'admin', action: 'conformAmountDeduction', params: [uniqueId: uniqueId, forOne: forOne, accountNumber: accountNumber])}"
                      method="POST"
                      id="passwordResetForm" autocomplete="off" class="form-horizontal">

                    <fieldset>
                        <legend>
                            <g:message code="admin.transaction.conform"/>
                        </legend>

                        <div class="row">
                            <g:each in="${accountDTOS}" var="accountDTO" status="i">
                                <legend>
                                    <g:message code="${accountDTO.accountNumber}"/>
                                </legend>
                                <table class="table table-hover table-bordered">
                                    <thead>
                                    <tr>
                                        <th><g:message code="date.label"/></th>
                                        <th><g:message code="bank.comment.label"/></th>
                                        <th><g:message code="expenses.label"/></th>
                                        <th><g:message code="grab.label"/></th>
                                        <th><g:message code="status.label"/></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <g:each in="${accountDTO.transactions}" var="transaction">
                                        <g:if test="${transaction?.status == com.moniday.enums.TransactionStatus.PENDING}">
                                            <tr>
                                                <td>${transaction?.transactionDate}</td>
                                                <td>${transaction?.description}</td>
                                                <td><g:message code="amount" args="${[transaction?.amount]}"/></td>
                                                <td><g:message code="amount"
                                                               args="${[transaction?.grabAmount ?: 0.0]}"/></td>
                                                <td>
                                                    ${transaction?.status}
                                                </td>
                                            </tr>
                                        </g:if>
                                    </g:each>
                                    </tbody>
                                </table>
                            </g:each>
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