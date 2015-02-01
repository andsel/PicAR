<!doctype html>
<html>
<head>
    <meta name="layout" content="bootstrap">
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div class="row-fluid">

    <div class="span3">
        <div class="well">
            <ul class="nav nav-list">
                <li class="nav-header">${entityName}</li>
                <li class="active">
                    <g:link class="list" action="list">
                        <i class="icon-list icon-white"></i>
                        <g:message code="default.list.label" args="[entityName]" />
                    </g:link>
                </li>
                <li>
                    <g:link class="create" action="create">
                        <i class="icon-plus"></i>
                        <g:message code="default.create.label" args="[entityName]" />
                    </g:link>
                </li>
            </ul>
        </div>
    </div>

    <div class="span9">

        <div class="page-header">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
        </div>

        <g:if test="${flash.message}">
            <bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
        </g:if>

        <table class="table table-striped">
            <thead>
            <tr>

                <g:sortableColumn property="username" title="${message(code: 'user.username.label', default: 'Username')}" />

                <%--g:sortableColumn property="password" title="${message(code: 'user.password.label', default: 'Password')}" /--%>

                <%--th class="header"><g:message code="user.profile.label" default="Profile" /></th--%>

                <g:sortableColumn property="accountExpired" title="${message(code: 'user.accountExpired.label', default: 'Account Expired')}" />

                <g:sortableColumn property="accountLocked" title="${message(code: 'user.accountLocked.label', default: 'Account Locked')}" />

                <g:sortableColumn property="dateCreated" title="${message(code: 'user.dateCreated.label', default: 'Date Created')}" />

                <%--th></th--%>
            </tr>
            </thead>
            <tbody>
            <g:each in="${userInstanceList}" var="userInstance">
                <tr>

                    <td>
                        <g:link action="edit" id="${userInstance.id}">${fieldValue(bean: userInstance, field: "username")}</g:link>
                    </td>

                    <%--td>${fieldValue(bean: userInstance, field: "password")}</td--%>

                    <%--td>${fieldValue(bean: userInstance, field: "profile")}</td--%>

                    <td><g:formatBoolean boolean="${userInstance.accountExpired}" /></td>

                    <td><g:formatBoolean boolean="${userInstance.accountLocked}" /></td>

                    <td><g:formatDate date="${userInstance.dateCreated}" format="dd/MM/yyyy"/></td>

                    <%--td class="link">
                                     <g:link action="show" id="${userInstance.id}" class="btn btn-small">Show &raquo;</g:link>
                                 </td--%>
                </tr>
            </g:each>
            </tbody>
        </table>
        <div class="pagination">
            <bootstrap:paginate total="${userInstanceTotal}" />
        </div>
    </div>

</div>
</body>
</html>
