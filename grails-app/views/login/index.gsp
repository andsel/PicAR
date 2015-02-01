<!doctype html>
<html>
<head>
    <title>Welcome to Picar</title>
    <meta name="layout" content="bootstrap"/>
    <style type="text/css" media="screen">
    #nav {
        margin-top: 20px;
        margin-left: 30px;
        width: 228px;
        float: left;

    }

    .homePagePanel * {
        margin: 0px;
    }

    .homePagePanel .panelBody ul {
        list-style-type: none;
        margin-bottom: 10px;
    }

    .homePagePanel .panelBody h1 {
        text-transform: uppercase;
        font-size: 1.1em;
        margin-bottom: 10px;
    }

    .homePagePanel .panelBody {
        background: url(images/leftnav_midstretch.png) repeat-y top;
        margin: 0px;
        padding: 15px;
    }

    .homePagePanel .panelBtm {
        background: url(images/leftnav_btm.png) no-repeat top;
        height: 20px;
        margin: 0px;
    }

    .homePagePanel .panelTop {
        background: url(images/leftnav_top.png) no-repeat top;
        height: 11px;
        margin: 0px;
    }

    h2 {
        margin-top: 15px;
        margin-bottom: 15px;
        font-size: 1.2em;
    }

    #pageBody {
        margin-left: 280px;
        margin-right: 20px;
    }
    </style>
    %{--<r:require module="picar"/>--}%
</head>

<body>

<div class="row">
    <div class="offset7 span4">
        <form action='${resource(file: 'j_spring_security_check')}' method='POST' id='loginForm'
              class="well">
            <label>Login</label>
            <input type='text' class='input-large' name='j_username' id='j_username'
                   value='${request.remoteUser}' placeholder="${message(code: 'login.username.placeholder', default: 'Username')}"
                   placeholder="Login…"/>

            <label>Password</label>
            <input type='password' class='input-large' name='j_password' id='j_password'
                   placeholder="${message(code: 'login.password.placeholder', default: 'Password')}" placeholder="Password"/>
            <button class="btn" type="submit"><g:message code="login.action.label" default="Sign in"/></button>
        </form>
    </div>
</div>
</body>

<script type='text/javascript'>
    <!--
    (function () {
        document.forms['loginForm'].elements['j_username'].focus();
    })();
    // -->
</script>
</html>