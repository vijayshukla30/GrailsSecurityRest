<%@ page import="com.moniday.enums.Currency; com.moniday.enums.Country;" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="theme"/>
    <title>Moniday | Admin</title>
    <style>
    .step {
        padding: 10px;
        color: black;
        text-decoration: none;
        transition: background-color .3s;
        border: 1px solid #ddd;
    }

    .nextLink {
        padding: 10px;
        color: black;
        text-decoration: none;
        transition: background-color .3s;
        border: 1px solid #ddd;
    }

    .prevLink {
        padding: 10px;
        color: black;
        text-decoration: none;
        transition: background-color .3s;
        border: 1px solid #ddd;
    }

    .currentStep {
        padding: 10px;
        background-color: #4CAF50;
        color: white;
        border: 1px solid #4CAF50;
    }

    .step.gap {
        display: none;
    }

    .step:hover:not(.active) {
        background-color: #ddd;
    }
    </style>
</head>

<body>
<div class="bs-docs-section clearfix">

    <g:if test="${users}">
        <div class="row">
            <div class="col-lg-2"></div>

            <div class="col-lg-8">
                <table class="table table-bordered table-hover table-striped">
                    <thead>
                    <tr>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Username</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${users}" var="user">
                        <tr>
                            <td>${user.firstName ?: "-"}</td>
                            <td>${user.lastName ?: "-"}</td>
                            <td>${user.username ?: "-"}</td>
                            <td>
                                <a href="${createLink(controller: 'admin', action: 'showUserDetails', params: [uniqueId: user?.uniqueId])}"
                                   class="btn btn-sm btn-success">
                                    <span class="fa fa-search">Details</span>
                                </a>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
                <g:paginate next="Next" prev="Prev" controller="admin" action="index" total="${userCount}"
                            params="${params}"/>
            </div>

            <div class="col-lg-2"></div>
        </div>
    </g:if>

    <g:else>
        <div class="alert alert-warning" role="alert">
            No Users found
        </div>
    </g:else>

</div>
</body>
</html>