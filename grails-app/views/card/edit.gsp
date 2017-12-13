<%@ page import="org.dna.picar.Card" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap">
		<g:set var="entityName" value="${message(code: 'card.label', default: 'Card')}" />
		<title><g:message code="card.show.cataloger.label"/></title>
        <r:script>
            jQuery(document).ready(function () {
                jQuery('[name=file]').bind('change', function (e) {
                    var input = jQuery(e.target);
                    var form = input.closest('form');
                    form.submit();
                });
            });
        </r:script>
	</head>

	<body>
        <div class="row-fluid">
            <g:link class="btn btn-primary" action="exportCard" target="_" id="${cardInstance.id}">
                <g:message code="button.exportpdf.label"/>
            </g:link>
        </div>

		<div class="row-fluid">

			<div class="span12">

				<div class="page-header">
					<h1><g:message code="card.show.cataloger.label"/></h1>
				</div>

				<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>

                <g:if test="${flash.error}">
                    <bootstrap:alert class="alert-error">${flash.error}</bootstrap:alert>
                </g:if>

				<g:hasErrors bean="${cardInstance}">
				<bootstrap:alert class="alert-error">
				<ul>
					<g:eachError bean="${cardInstance}" var="error">
					<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
                        <g:message error="${error}"/>
                    </li>
					</g:eachError>
				</ul>
				</bootstrap:alert>
				</g:hasErrors>
            </div>
        </div>

        <fieldset>
            <g:form class="form-horizontal" action="edit" id="${cardInstance?.id}"  enctype="multipart/form-data">
                <g:hiddenField name="version" value="${cardInstance?.version}" />
                <g:render template="form"/>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">
                        <i class="icon-ok icon-white"></i>
                        <g:message code="default.button.update.label" default="Update" />
                    </button>
                    <button type="submit" class="btn btn-danger" name="_action_delete" formnovalidate>
                        <i class="icon-trash icon-white"></i>
                        <g:message code="default.button.delete.label" default="Delete" />
                    </button>
                </div>
            </g:form>
        </fieldset>


        Upload File <br/>
        <g:uploadForm action="upload" method="post">
            <g:hiddenField name="cardId" value="${cardInstance.id}"/>
            <input type="file" class="file" name="file"/>
            %{--<button type="submit" class="btn btn-primary">--}%
                %{--<g:message code="card.button.upload.label" default="Upload" />--}%
            %{--</button>--}%
        </g:uploadForm>

        <ul class="thumbnails">
            <g:each in="${cardInstance.images}" var="image">
                <li class="span4">
                    <div class="thumbnail">
                        <g:link action="originalImage" id="${image.id}">
                            <img data-src="holder.js/300x200"
                                 src="${createLink(action: 'retrieveImage', params: [file: image.fileName +'_thumb.png'])}"
                                 alt=""
                                 target="_blank">
                        </g:link>
                        <h3>${image.fileName}</h3>
                        <p>
                            <g:link action="deleteImage" id="${image.id}">
                                <i class="icon-trash"></i> <g:message code="image.delete.action.label" default="Delete"/>
                            </g:link>
                        </p>
                    </div>
                </li>
            </g:each>
        </ul>

	</body>
</html>
