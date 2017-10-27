<%@ page import="com.moniday.enums.Currency; com.moniday.enums.Country;" %>
<div class="bs-docs-section clearfix">
    <div class="row">
        <div class="col-lg-12">
            <h2 id="nav-tabs">Accounts and Transactions</h2>

            <div class="bs-component">
                <ul class="nav nav-tabs">
                    <g:each in="${personDTO.accounts}" var="account" status="i">
                        <li class="${i == 0 ? "active" : ""}">
                            <a href="#${account.accountNumber}" data-toggle="tab">${account.accountNumber}</a>
                        </li>
                    </g:each>
                </ul>

                <div id="myTabContent" class="tab-content">
                    <g:each in="${personDTO.accounts}" var="account" status="i">
                        <div class="tab-pane fade ${i == 0 ? "active" : ""} in" id="${account.accountNumber}">
                            <div class="row">
                                <div class="col-md-12">
                                    <table class="table table-hover table-bordered">
                                        <thead>
                                        <tr>
                                            <td>Date</td>
                                            <td>Amount</td>
                                            <td>Description</td>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <g:each in="${account.transactions}" var="transaction">
                                            <tr>
                                                <td>${transaction.date}</td>
                                                <td>${transaction.amount}</td>
                                                <td>${transaction.description}</td>
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
        </div>
    </div>
</div>