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
    <g:layoutHead/>
    <script>
        $.notify.defaults({
            clickToHide: true,
            autoHide: true,
            autoHideDelay: 5000,
            arrowShow: true,
            arrowSize: 5,
            globalPosition: 'top center',
            style: 'bootstrap',
            className: 'error',
            showAnimation: 'slideDown',
            showDuration: 400,
            hideAnimation: 'slideUp',
            hideDuration: 200,
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
            <ul class="nav navbar-nav navbar-right">
                <li><a href="http://builtwithbootstrap.com/" target="_blank">Built With Bootstrap</a></li>
                <li><a href="https://wrapbootstrap.com/?ref=bsw" target="_blank">WrapBootstrap</a></li>
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
<div id="spinner" class="spinner" style="display:none;"></div>
</body>
</html>
