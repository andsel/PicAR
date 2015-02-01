<!doctype html>
<html>
<head>
    <meta name="layout" content="bootstrap">
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div class="row-fluid">
    <g:form action="create">
        <div class="span6">
            <div class="control-group ">
                <label class="control-label" for="username"><g:message code="username.label" default="Username"/></label>
                <div class="controls ${hasErrors(bean: 'user', field: 'username', 'error')}">
                    <g:field name="username" type="text" value="${user?.username}"/>
                </div>
            </div>
                <label class="control-label" for="password"><g:message code="password.label" default="Password"/></label>
                <div class="controls ${hasErrors(bean: 'user', field: 'password', 'error')}">
                    <g:field name="password" type="password" value="${user?.password}"/>
                </div>
            </div>
        </div>

        <div class="form-actions">
            <button type="submit" class="btn btn-primary">
                <i class="icon-ok icon-white"></i>
                <g:message code="default.button.create.label" default="Create" />
            </button>
        </div>
    </g:form>
</div>
</body>
</html>