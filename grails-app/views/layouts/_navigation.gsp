<sec:ifAllGranted roles="ROLE_USER">
    <ul class="nav navbar-nav">
        <li><a href="${createLink(controller: 'account', action: 'personalDetail')}">Personal Detail</a></li>
        <li><a href="${createLink(controller: 'account', action: 'accountDetail')}">Account Detail</a></li>
        <li><a href="${createLink(controller: 'account', action: 'securityDetail')}">Security Detail</a></li>
        <li><a href="${createLink(controller: 'account', action: 'debitMandateDetail')}">DebitMandate Detail</a></li>
    </ul>
</sec:ifAllGranted>
