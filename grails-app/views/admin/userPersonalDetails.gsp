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
    <div class="row">
        <div class="col-md-3"></div>

        <div class="col-md-6">
            <table class="table table-hover table-bordered">
                <tr>
                    <th>First Name</th>
                    <td>${personalDetailCO?.firstName}</td>
                </tr>
                <tr>
                    <th>Last Name</th>
                    <td>${personalDetailCO?.lastName}</td>
                </tr>
                <tr>
                    <th>Date Of Birth</th>
                    <td>${personalDetailCO?.dateOfBirth}</td>
                </tr>
                <tr>
                    <th>Nationality</th>
                    <td>${personalDetailCO?.nationality}</td>
                </tr>
                <tr>
                    <th>Country</th>
                    <td>${personalDetailCO?.country}</td>
                </tr>
                <tr>
                    <th>Currency</th>
                    <td>${personalDetailCO?.currency}</td>
                </tr>

            </table>
        </div>

        <div class="col-md-3"></div>
    </div>
</div>
</body>
</html>