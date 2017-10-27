<!doctype html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title><g:layoutTitle default="Grails"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <asset:stylesheet src="theme/theme.css"/>
    <asset:javascript src="theme/theme.js"/>
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet"/>
    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>
    <g:layoutHead/>
    <script>
        $.notify.defaults({
            clickToHide: true,
            autoHide: true,
            autoHideDelay: 3000,
            arrowShow: true,
            arrowSize: 5,
            globalPosition: 'top right',
            style: 'bootstrap',
            className: 'error',
            showAnimation: 'slideDown',
            showDuration: 500,
            hideAnimation: 'slideUp',
            hideDuration: 300,
            gap: 2
        })
    </script>
</head>

<body>
<div class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <a href="${createLink(uri: '/')}" class="navbar-brand">Moniday</a>
        </div>

        <div class="navbar-collapse collapse" id="navbar-main">
            <g:render template="/layouts/navigation"/>
            <ul class="nav navbar-nav navbar-right">
                <sec:ifLoggedIn>
                    <li><a href="#"><span class="fa fa-user"></span> <sec:loggedInUserInfo
                            field="username"/></a></li>
                    <li><g:link controller='logout'><span class="fa fa-sign-out"></span>Logout</g:link></li>
                </sec:ifLoggedIn>
            </ul>

        </div>
    </div>
</div>

<div class="container">
    <g:layoutBody/>

    <footer>
        <div class="row">
            <div class="col-lg-12">

                <ul class="list-unstyled">
                    <li class="pull-right">
                        <a href="#top">Back to top</a>
                    </li>
                </ul>
            </div>
        </div>
    </footer>
</div>
<g:render template="/layouts/notify"/>
%{--<div id="spinner" class="spinner" style="display:none;"></div>--}%
</body>
</html>
