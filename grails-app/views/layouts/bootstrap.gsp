<%@ page import="org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title><g:layoutTitle default="${meta(name: 'app.name')}"/></title>
    <meta name="description" content="">
    <meta name="author" content="">

    <meta name="viewport" content="initial-scale = 1.0">

    <!-- Le HTML5 shim, for IE6-8 support of HTML elements -->
    <!--[if lt IE 9]>
        <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <r:require modules="scaffolding"/>

    <!-- Le fav and touch icons -->
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
    <link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
    <link rel="apple-touch-icon" sizes="72x72" href="${resource(dir: 'images', file: 'apple-touch-icon-72x72.png')}">
    <link rel="apple-touch-icon" sizes="114x114"
          href="${resource(dir: 'images', file: 'apple-touch-icon-114x114.png')}">

    <r:require module="picar"/>
    <g:layoutHead/>
    <r:layoutResources/>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">

            <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a>

            <a class="brand" href="${createLink(uri: '/')}">Picar</a>

            <sec:ifLoggedIn>
                <ul class="nav pull-right">
                    <li class="dropdown">
                        <a href="#" data-toggle="dropdown"
                           class="dropdown-toggle"><sec:username/><!--b class="caret"/--></a>
                        <ul class="dropdown-menu">
                            %{--<li><a href="${createLink(controller: 'settings', action: 'show')}">Settings</a></li>--}%
                            %{--<li><a href="#feedbackModal" data-toggle="modal">Feedback</a></li>--}%
                            %{--<li class="divider"></li>--}%
                            <li><a href="${createLink(controller: 'logout', action: 'index')}">Sign out</a></li>
                        </ul>
                    </li>
                </ul>

                <sec:ifAnyGranted roles="ROLE_CATALOG">
                    <ul class="nav">
                        <li class="dropdown">
                            <a href="#" data-toggle="dropdown"
                               class="dropdown-toggle"><g:message code="card.menu.label" default="Cards"/></a>
                            <ul class="dropdown-menu">
                                <li><a href="${createLink(controller: 'card', action: 'create')}">
                                    <g:message code="card.create.label" default="New card"/></a>
                                </li>
                                <li><a href="${createLink(controller: 'search', action: 'index')}">
                                    <g:message code="card.list.label" default="Search"/></a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </sec:ifAnyGranted>

                <sec:ifAnyGranted roles="ROLE_CATALOG">
                    <ul class="nav">
                        <li class="dropdown">
                            <a href="#" data-toggle="dropdown"
                               class="dropdown-toggle"><g:message code="fund.menu.label" default="Funds"/></a>
                            <ul class="dropdown-menu">
                                <li><a href="${createLink(controller: 'fund', action: 'create')}">
                                    <g:message code="fund.create.label" default="New fund"/></a>
                                </li>
                                <li><a href="${createLink(controller: 'fund', action: 'list')}">
                                    <g:message code="fund.list.label" default="Fund list"/></a>
                                </li>
                                <li><a href="${createLink(controller: 'card', action: 'exportCatalog')}" target="_">
                                    <g:message code="button.exportcatalog.label" default="Export catalog"/></a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </sec:ifAnyGranted>

                <sec:ifAnyGranted roles="ROLE_ADMIN">
                    <ul class="nav">
                        <li class="dropdown">
                            <a href="#" data-toggle="dropdown"
                               class="dropdown-toggle"><g:message code="administration.menu.label" default="Administration"/></a>
                            <ul class="dropdown-menu">
                                %{--<li><a href="${createLink(controller: 'user', action: 'createUser')}">--}%
                                <li><a href="${createLink(controller: 'user', action: 'create')}">
                                    <g:message code="user.create.label" default="New user"/></a>
                                </li>

                                <li><a href="${createLink(controller: 'card', action: 'reindex')}">
                                    <g:message code="card.reindex.label" default="Reindex"/></a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </sec:ifAnyGranted>
            </sec:ifLoggedIn>
            <sec:ifNotLoggedIn>
                <ul class="nav">
                    <li><a href="${createLink(controller: 'search', action: 'index')}">
                        <g:message code="card.list.label" default="Search"/></a>
                    </li>
                </ul>
            </sec:ifNotLoggedIn>

            <sec:ifNotLoggedIn>
                <ul class="nav pull-right">
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                            Have an account? <b>sign in</b>
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu">
                            <li>
                                <form action='${resource(file: 'j_spring_security_check')}' method='POST' id='loginForm' class="navbar-form">
                                    <input type='text' class='mender-navbar-login' name='j_username' id='j_username'
                                           value='${request.remoteUser}' placeholder="${message(code: 'login.username.placeholder', default: 'Username')}"/>
                                    <input type='password' class='mender-navbar-login' name='j_password' id='j_password'
                                           placeholder="${message(code: 'login.password.placeholder', default: 'Password')}"/>
                                    <%-- TODO enable in near future for the remember p>
                                    <label for='remember_me'>Remember me</label>
                                    <input type='checkbox' class='chk' name='_spring_security_remember_me' id='remember_me'
                                    <g:if test='${hasCookie}'>checked='checked'</g:if> />
                                    </p--%>
                                    <button class="btn btn-small mender-navbar-login" type="submit"><b><g:message code="login.action.label" default="Sign in"/></b></button>
                                </form>
                            </li>
                        </ul>
                    </li>
                </ul>

            </sec:ifNotLoggedIn>
        </div>
    </div>
</nav>

<div class="container">
    <g:layoutBody/>

    <hr>

    <footer>
        Version <g:meta name="app.version"/>
        Built with Grails <g:meta name="app.grails.version"/>
    </footer>
</div>

<r:layoutResources/>

</body>
</html>