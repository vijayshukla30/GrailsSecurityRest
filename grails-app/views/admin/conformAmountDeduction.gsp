<%@ page import="com.moniday.enums.TransactionStatus" %>
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

        <div class="col-lg-5">
            <h4><g:message code="admin.transaction.confirm"/></h4>
        </div>

        <div class="col-lg-2"></div>

        <div class="col-lg-3">
            <div class="pull-left">
                <form action="${createLink(controller: 'admin', action: 'conformAmountDeduction', params: [uniqueId: uniqueId, forOne: forOne, accountNumber: accountNumber])}"
                      method="POST" class="form-horizontal">
                    <button class="btn btn-primary btn-sm">submit</button>
                </form>
            </div>
        </div>
    </div>
    <hr/>
    <br/>

    <div class="row">
        <div class="col-lg-2"></div>

        <div class="col-lg-8">
            <form action="${createLink(controller: 'admin', action: 'conformAmountDeduction', params: [uniqueId: uniqueId, forOne: forOne, accountNumber: accountNumber])}"
                  method="POST" class="form-horizontal">
                <div class="panel-group">
                    <g:each in="${accountDTOS}" var="accountDTO" status="i">
                        <div class="panel panel-primary">
                            <div class="panel-heading"><g:message code="${accountDTO.accountNumber}"/></div>

                            <div class="panel-body">
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
                                        <g:if test="${transaction?.status == TransactionStatus.PENDING}">
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
                            </div>
                        </div>
                    </g:each>

                </div>


                <div class="form-group">
                    <div class="row">

                        <div class="col-lg-5"></div>

                        <div class="col-lg-7 pull-left">
                            <button type="submit" class="btn btn-sm btn-primary">
                                <g:message code="form.submit"/>
                            </button>
                        </div>

                    </div>
                </div>
            </form>
        </div>

        <div class="col-lg-2"></div>
    </div>

</div>
</body>
</html>