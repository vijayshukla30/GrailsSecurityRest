<g:if test="${user}">
    <ul class="nav navbar-nav">
        <li><a href="${createLink(controller: 'account', action: 'personalDetail')}">Personal Detail</a></li>
        <li><a href="${createLink(controller: 'account', action: 'accountDetail')}">Account Detail</a></li>
    </ul>
</g:if>