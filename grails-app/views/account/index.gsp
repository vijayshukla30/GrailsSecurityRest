<%@ page import="com.moniday.enums.Currency; com.moniday.enums.Country;" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="theme"/>
    <title>Moniday | Home Page</title>
    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>
</head>

<body>
<div class="bs-docs-section clearfix">

    <g:render template="scrappedBankDescription" model="[personDTO: personDTO]"/>

</div>
</body>
</html>
