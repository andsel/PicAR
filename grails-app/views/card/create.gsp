<%@ page import="org.dna.picar.Card" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap">
		<g:set var="entityName" value="${message(code: 'card.label', default: 'Card')}" />
		<title><g:message code="card.show.cataloger.label"/></title>
	</head>
	<body>
		<div class="row-fluid">
			
			<div class="span12">
				<div class="page-header">
					<h1><g:message code="card.show.cataloger.label"/></h1>
				</div>

				<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>

				<g:hasErrors bean="${cardInstance}">
				<bootstrap:alert class="alert-error">
				<ul>
					<g:eachError bean="${cardInstance}" var="error">
					<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
					</g:eachError>
				</ul>
				</bootstrap:alert>
				</g:hasErrors>
            </div>
        </div>

        <fieldset>
            <g:form class="form-horizontal" action="create">
                <g:render template="form"/>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">
                        <i class="icon-ok icon-white"></i>
                        <g:message code="default.button.create.label" default="Create" />
                    </button>
                </div>
            </g:form>
        </fieldset>
				
	</body>
</html>
