
<%@ page import="org.dna.picar.Card" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap">
		<g:set var="entityName" value="${message(code: 'card.label', default: 'Card')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<div class="row-fluid">
			
			<div class="span12">
				
				<div class="page-header">
					<h1><g:message code="default.list.label" args="[entityName]" /></h1>
				</div>

				<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>
				
				<table class="table table-striped">
					<thead>
						<tr>
						
							<g:sortableColumn property="inventoryNumber" title="${message(code: 'card.inventoryNumber.label', default: 'Inventory Number')}" />
						
							<g:sortableColumn property="signature" title="${message(code: 'card.signature.label', default: 'Signature')}" />
						
							<g:sortableColumn property="subject" title="${message(code: 'card.subject.label', default: 'Subject')}" />
						
							<g:sortableColumn property="header" title="${message(code: 'card.header.label', default: 'Header')}" />
						

							<th></th>
						</tr>
					</thead>
					<tbody>
					<g:each in="${cardInstanceList}" var="cardInstance">
						<tr>
						
							<td>${fieldValue(bean: cardInstance, field: "inventoryNumber")}</td>
						
							<td>${fieldValue(bean: cardInstance, field: "signature")}</td>
						
							<td>${fieldValue(bean: cardInstance, field: "subject")}</td>
						
							<td class="link">
								<g:link action="show" id="${cardInstance.id}" class="btn btn-small">Show &raquo;</g:link>
							</td>
						</tr>
					</g:each>
					</tbody>
				</table>
				<div class="pagination">
					<bootstrap:paginate total="${cardInstanceTotal}" />
				</div>
			</div>

		</div>
	</body>
</html>
