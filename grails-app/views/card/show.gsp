
<%@ page import="org.dna.picar.Card" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="bootstrap">
		<g:set var="entityName" value="${message(code: 'card.label', default: 'Card')}" />
		<title><g:message code="card.show.user.label"/></title>
	</head>
	<body>
		<div class="row-fluid">
			
			<div class="span12">

				<div class="page-header">
					<h1><g:message code="card.show.user.label"/></h1>
				</div>

				<g:if test="${flash.message}">
				<bootstrap:alert class="alert-info">${flash.message}</bootstrap:alert>
				</g:if>

                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th class="span3"></th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- Identfication -->
                        <tr>
                            <td><b><g:message code="card.inventoryNumber.label" default="Inventory Number"/></b></td>
                            <td><g:fieldValue bean="${cardInstance}" field="inventoryNumber"/></td>
                        </tr>

                        <tr>
                            <td><b><g:message code="card.signature.label" default="Signature"/></b></td>
                            <td><g:fieldValue bean="${cardInstance}" field="signature"/></td>
                        </tr>

                        <tr>
                            <td><b><g:message code="card.dating.label" default="Dating"/></b></td>
                            <td><g:fieldValue bean="${cardInstance}" field="dating"/></td>
                        </tr>

                        <tr>
                            <td><b><g:message code="card.fund.label" default="Fund"/></b></td>
                            <td><g:fieldValue bean="${cardInstance}" field="fund"/></td>
                        </tr>

                        <!--Authors-->
                        <tr>
                            <td><b><g:message code="card.author.label" default="Author"/></b></td>
                            <td><g:fieldValue bean="${cardInstance}" field="author"/></td>
                        </tr>

                        <tr>
                            <td><b><g:message code="card.printer.label" default="Printer"/></b></td>
                            <td><g:fieldValue bean="${cardInstance}" field="printer"/></td>
                        </tr>

                        <tr>
                            <td><b><g:message code="card.editor.label" default="Editor"/></b></td>
                            <td><g:fieldValue bean="${cardInstance}" field="editor"/></td>
                        </tr>


                        <!-- Description -->
                        <tr>
                            <td><b><g:message code="card.title.label" default="Title" /></b></td>
                            <td><g:fieldValue bean="${cardInstance}" field="title"/></td>
                        </tr>

                        <tr>
                            <td><b><g:message code="card.subject.label" default="Subject" /></b></td>
                            <td><g:fieldValue bean="${cardInstance}" field="subject"/></td>
                        </tr>

                        <tr>
                            <td><b><g:message code="card.inscription.label" default="Inscription" /></b></td>
                            <td><g:fieldValue bean="${cardInstance}" field="inscription"/></td>
                        </tr>
                        <tr>
                            <td><b><g:message code="card.object.label" default="Object"/></b></td>
                            <td><g:message code="card.object.${cardInstance?.object}"/></td>
                        </tr>
                        <tr>
                            <td><b><g:message code="card.colorIndication.label" default="Color indication"/></b></td>
                            <td><g:message code="card.colorIndication.${cardInstance?.colorIndication}"/> </td>
                        </tr>

                        <!-- Technical Description -->
                        <tr>
                            <td><b><g:message code="card.technique.label" default="Photo technique" /></b></td>
                            <td><g:message code="card.technique.${cardInstance?.technique}"/> </td>
                        </tr>

                        <tr>
                            <td><b><g:message code="card.primarySupport.label" default="Primary support" /></b></td>
                            %{--<td><g:fieldValue bean="${cardInstance}" field="primarySupport"/>--}%
                            <td><g:message code="card.primarySupport.${cardInstance?.primarySupport}"/>
                                (mm)
                                ${cardInstance.primarySupportDimensions.height}  h x
                                ${cardInstance.primarySupportDimensions.width} l
                            </td>
                        </tr>

                        <tr>
                            <td><b><g:message code="card.secondarySupport.label" default="Secondary support" /></b></td>
                            %{--<td><g:fieldValue bean="${cardInstance}" field="secondarySupport"/>--}%
                            <td><g:message code="card.secondarySupport.${cardInstance?.secondarySupport}"/>
                            <g:if test="${cardInstance.secondarySupportDimensions}">
                                (mm)
                                ${cardInstance.secondarySupportDimensions.height}  h x
                                ${cardInstance.secondarySupportDimensions.width} l
                            </g:if>
                            </td>
                        </tr>

                        <tr>
                            <td><b><g:message code="card.conservationStatus.label"/></b></td>
                            <td><g:message code="card.conservationStatus.${cardInstance?.conservationStatus}"/></td>
                        </tr>

                        <tr>
                            <td><b><g:message code="card.statusDetails.label"/></b></td>
                            <td><g:fieldValue bean="${cardInstance}" field="statusDetails"/></td>
                        </tr>

                        <g:each in="${cardInstance.bibliography}" var="bibliography" status="i">
                            <g:if test="${i == 0}">
                                <tr>
                                    <td rowspan="${cardInstance.bibliography.size()}"><b><g:message code="card.bibliography.label"/></b></td>
                                    <td>${bibliography}</td>
                                </tr>
                            </g:if>
                            <g:else>
                                <tr>
                                    <td>${bibliography}</td>
                                </tr>
                            </g:else>
                        </g:each>

                        <!-- Other -->
                        <g:each in="${cardInstance.sources}" var="source" status="i">
                            <g:if test="${i == 0}">
                                <tr>
                                    <td rowspan="${cardInstance.sources.size()}"><b><g:message code="card.sources.label"/></b></td>
                                    <td>${source}</td>
                                </tr>
                            </g:if>
                            <g:else>
                                <tr>
                                    <td>${source}</td>
                                </tr>
                            </g:else>
                        </g:each>

                        <tr>
                            <td><b><g:message code="card.observations.label"/></b></td>
                            <td><g:fieldValue bean="${cardInstance}" field="observations"/></td>
                        </tr>

                        <g:if test="${cardInstance?.creator}">
                            <tr>
                                <td><b><g:message code="card.creator.label" default="Archiver" /></b></td>
                                <td><g:link controller="user" action="show" id="${cardInstance?.creator?.id}">${cardInstance?.creator?.encodeAsHTML()}</g:link></td>
                            </tr>
                        </g:if>
                    </tbody>
                </table>

                <ul class="thumbnails">
                    <g:each in="${cardInstance.images}" var="image">
                        <li class="span4">
                            <div class="thumbnail">
                                <sec:ifAnyGranted roles="ROLE_CATALOG">
                                    <g:link action="originalImage" id="${image.id}">
                                        <img data-src="holder.js/300x200"
                                             src="${createLink(action: 'retrieveImage', params: [file: image.fileName +'_thumb.png'])}"
                                             alt=""
                                             target="_blank">
                                    </g:link>
                                </sec:ifAnyGranted>
                                <sec:ifNotGranted roles="ROLE_CATALOG">
                                    <img data-src="holder.js/300x200"
                                         src="${createLink(action: 'retrieveImage', params: [file: image.fileName +'_thumb.png'])}"
                                         alt=""
                                         target="_blank">
                                </sec:ifNotGranted>
                                <h3>${image.fileName}</h3>
                            </div>
                        </li>
                    </g:each>
                </ul>


                <sec:ifAnyGranted roles="ROLE_CATALOG">
                    <g:form>
                        <g:hiddenField name="id" value="${cardInstance?.id}" />
                        <div class="form-actions">
                            <g:link class="btn" action="edit" id="${cardInstance?.id}">
                                <i class="icon-pencil"></i>
                                <g:message code="default.button.edit.label" default="Edit" />
                            </g:link>
                            <button class="btn btn-danger" type="submit" name="_action_delete">
                                <i class="icon-trash icon-white"></i>
                                <g:message code="default.button.delete.label" default="Delete" />
                            </button>
                        </div>
                    </g:form>
                </sec:ifAnyGranted>

			</div>

		</div>
	</body>
</html>
