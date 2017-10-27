<%@ page import="com.moniday.enums.Currency; com.moniday.enums.Country;" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="theme"/>
    <title>Moniday | Personal Detail</title>
    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>
</head>

<body>

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
