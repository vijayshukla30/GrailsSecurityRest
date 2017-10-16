<%@ page import="com.moniday.enums.Currency; com.moniday.enums.Country;" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="theme"/>
    <title>Moniday | Personal Detail</title>
    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>
</head>

<body>
<div class="bs-docs-section clearfix">
    <div class="row">
        <div class="col-lg-3"></div>

        <div class="col-lg-6">
            ${user.username}
        </div>

        <div class="col-lg-3"></div>
    </div>
</div>
<script>
    $(document).ready(function () {
        $("#personalDetail").validate({
            rules: {
                email: true,
                lastName: true,
                nationality: true,
                country: true,
                currency: true
            },
            messages: {
                email: "First name required",
                lastName: "Last name required",
                nationality: 'Nationality Required',
                country: "Country Required",
                currency: "Currency Required"
            }
        });
    });
</script>
</body>
</html>
