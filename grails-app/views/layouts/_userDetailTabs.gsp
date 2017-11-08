<ul class="nav nav-tabs">
    <g:if test="${tabName == "PersonalDetail"}">
        <li class="active">
            <a href="#personalDetails" data-toggle="tab">
                Personal Details
            </a>
        </li>
    </g:if>
    <g:else>
        <li>
            <a href="${createLink(controller: "admin", action: "showUserDetails", params: [uniqueId: user?.uniqueId])}"
               data-toggle="tab">
                Personal Details
            </a>
        </li>
    </g:else>
   %{-- <g:if test="${tabName == "AccountDetail"}">
        <li class="active">
            <a href="#accountDetails" data-toggle="tab">
                Account Details
            </a>
        </li>
    </g:if>
    <g:else>
        <li>
            <a href="${createLink(controller: "admin", action: "showUserAccountDetail", params: [uniqueId: user?.uniqueId])}"
               data-toggle="tab">
                Personal Details
            </a>
        </li>
    </g:else>
    <g:if test="${tabName == "DebitMandateDetail"}">
        <li class="active">
            <a href="#debitMandateDetails" data-toggle="tab">
                Debit Mendate Details
            </a>
        </li>
    </g:if>
    <g:else>
        <li>
            <a href="${createLink(controller: "admin", action: "showUserDebitMandateDetail", params: [uniqueId: user?.uniqueId])}"
               data-toggle="tab">
                Debit Mendate Details
            </a>
        </li>
    </g:else>--}%
</ul>
