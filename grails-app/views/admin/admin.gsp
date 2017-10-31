<%@ page import="com.moniday.enums.Currency; com.moniday.enums.Country;" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="theme"/>
    <title>Moniday | Admin</title>
    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>
</head>

<body>
<div class="container">
    <div class="row">
        <div class="col-lg-2"></div>

        <div class="col-lg-8">
            <table class="table">
                <thead>
                <tr>
                    <th>Username</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                </tr>
                </thead>
                <tbody>
                <g:each in="${users}">
                    <tr>
                        <td>${users.username}</td>
                        <td>${users.firstName}</td>
                        <td>${users.lastName}</td>
                    </tr>
                </g:each>
                </tbody>
            </table>
        </div>

        <div class="col-lg-2"></div>
    </div>
</div>
</body>
</html>