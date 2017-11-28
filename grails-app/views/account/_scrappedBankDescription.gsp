<%@ page import="com.moniday.enums.Currency; com.moniday.enums.Country;" %>
<div class="bs-docs-section clearfix">
    <div class="row">
        <div class="col-lg-3">
            <strong>Total Debit Account Money:</strong> <span class="fa fa-eur">${personDTO?.deductedMoney}</span>
        </div>

        <form action="${createLink(controller: 'admin', action: 'approveAmountDeduction')}"
              method="POST" class="form-horizontal">
            <g:hiddenField name="uniqueId" value="${user.uniqueId}"/>
            <g:hiddenField name="forOne" value="false"/>
            <g:hiddenField name="accountNumber" value=""/>
            <div class="col-lg-5">
                <sec:ifAnyGranted roles="ROLE_ADMIN">
                    <g:if test="${personDTO?.deductedMoney > "${minDeductionAmount}"}">
                        <td>
                            <button type="submit" class="btn btn-xs btn-primary">
                                <span>Approve</span>
                            </button>
                        </td>
                    </g:if>
                </sec:ifAnyGranted>
            </div>
        </form>
    </div>

    <div class="row">
        <div class="col-lg-12">
            <h2 id="nav-tabs">Accounts and Transactions</h2>

            <g:if test="${personDTO}">
                <div class="bs-component">
                    <ul class="nav nav-tabs">
                        <g:each in="${personDTO.accounts}" var="account" status="i">
                            <li class="${i == 0 ? "active" : ""}">
                                <a href="#${account.isCardTransaction ? "${account?.accountNumber}-CreditCard" : account?.accountNumber}"
                                   data-toggle="tab">${account.isCardTransaction ? "${account?.accountNumber}-CreditCard" : account?.accountNumber}</a>
                            </li>
                        </g:each>
                    </ul>

                    <div id="myTabContent" class="tab-content">
                        <g:each in="${personDTO.accounts}" var="account" status="i">
                            <div class="tab-pane fade ${i == 0 ? "active" : ""} in"
                                 id="${account.isCardTransaction ? "${account?.accountNumber}-CreditCard" : account?.accountNumber}">
                                <div class="row">
                                    <div class="col-md-3">
                                        <strong>Total Money of Debit Account:</strong> <span
                                            class="fa fa-eur">${account?.deductedMoney}</span>
                                    </div>


                                    <form action="${createLink(controller: 'admin', action: 'approveAmountDeduction')}"
                                          method="POST" class="form-horizontal">
                                        <g:hiddenField name="uniqueId" value="${user.uniqueId}"/>
                                        <g:hiddenField name="forOne" value="true"/>
                                        <g:hiddenField name="accountNumber" value="${account?.accountNumber}"/>
                                        <div class="col-lg-5">
                                            <sec:ifAnyGranted roles="ROLE_ADMIN">
                                                <g:if test="${account?.deductedMoney > "${minDeductionAmount}"}">
                                                    <td>
                                                        <button type="submit" class="btn btn-xs btn-primary">
                                                            <span>Approve</span>
                                                        </button>
                                                    </td>
                                                </g:if>
                                            </sec:ifAnyGranted>
                                        </div>

                                    </form>
                                </div>

                                <div class="row">
                                    <div class="col-md-12">
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
                                            <g:each in="${account.transactions}" var="transaction">
                                                <tr>
                                                    <td>${transaction?.transactionDate.split("-")[0]}</td>
                                                    <td>${transaction?.description}</td>
                                                    <td><g:message code="amount" args="${[transaction?.amount]}"/></td>
                                                    <td><g:message code="amount"
                                                                   args="${[transaction?.grabAmount ?: 0.0]}"/></td>
                                                    <td>
                                                        ${transaction?.status}
                                                    </td>
                                                </tr>
                                            </g:each>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </g:each>
                    </div>
                </div>
            </g:if>
        </div>
    </div>
</div>