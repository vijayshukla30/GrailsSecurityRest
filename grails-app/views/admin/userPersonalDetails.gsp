<%@ page import="com.moniday.enums.Currency; com.moniday.enums.Country;" %>
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
        <g:render template="/account/scrappedBankDescription" model="[personDTO: personDTO]"/>
    </div>
</div>
</body>
</html>