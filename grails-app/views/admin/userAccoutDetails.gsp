<%@ page import="com.moniday.enums.Currency; com.moniday.enums.Country; com.moniday.AdminSetting" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="theme"/>
    <title>Moniday | ${tabName}</title>
    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>
</head>

<body>
<div class="bs-docs-section clearfix">
    <g:render template="/layouts/userDetailTabs" model="[tabName: tabName, user: user]"/>
</div>

<div id="userTabContent" class="tab-content">
    <div class="tab-pane fade active in" id="personalDetails">
        <g:if test="${personDTO}">
            <g:render template="/account/scrappedBankDescription"
                      model="[personDTO: personDTO, user: user, minDeductionAmount: AdminSetting.get(1).minDeductionAmount]"/>
        </g:if>
        <g:else>
            <div class="alert alert-warning" role="alert">No Account Detail Found</div>
        </g:else>
    </div>
</div>
</body>
</html>