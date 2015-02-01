<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title><g:message code="search.title" default="Search cards"/></title>
    <meta name="layout" content="bootstrap"/>
</head>

<body>

<div class="content">
    <div class="row">
        %{--<div class="span12">--}%
            <g:form class="well form-inline" action="index">
                <fieldset>
                    <g:textField name="q" value="${params.q}" class="input-xxlarge mender-input-search"
                                 placeholder="${message(code: 'search.location.placeholder.label', default: 'Search something')}"/>

                    <button class="btn btn-warning btn-large" type="submit"><g:message code="search.button.label" default="Search"/></button>
                </fieldset>
            </g:form>
        %{--</div>--}%
    </div>

    <div class="row">
        <g:if test="${searchResult?.results}">
            <b>${searchResult.offset + 1}-
                ${searchResult.offset + searchResult.max}</b> of
            <b>${searchResult.total}</b>:
            <g:set var="totalPages"
                   value="${Math.ceil(searchResult.total / searchResult.max)}"/>
        </g:if>
        <g:else>
            <h4>
                <g:message code="search.empty.result.label" default="No results found"/>
            </h4>
        </g:else>

        <g:if test="${searchResult?.results}">
            <g:each in="${searchResult.results}" var="card" status="resIdx">
                <div class="row">
                    <div class="alert alert-info">
                        <div class="row">
                            <div class="span4">
                                <g:link controller="card" action="show" id="${card.id}">
                                    <h4 class="alert-heading"> ${card.signature} </h4>
                                </g:link>
                            </div>
                        </div>

                        <div class="row">
                            <div class="span12">
                                <g:message code="card.subject.label" default="Subject"/>: ${card.subject}

                                %{--<g:if test="${!searchResult.highlights[resIdx].empty}">--}%
                                    %{--...${searchResult.highlights[resIdx]}...--}%
                                %{--</g:if>--}%


                                %{--<g:else>--}%
                                    %{--${card.subject}--}%
                                    %{--${card.subject[0..Math.min(50, card.subject.size() -1 )]}--}%
                                %{--</g:else>--}%

                                %{--<g:if test="${card.fileName}">--}%
                                    %{--<p>--}%
                                        %{--<img src="${createLink(action: 'retrieveImage', params: [file: card.fileName +'_thumb.png'])}"--}%
                                             %{--alt="${card.fileName}"--}%
                                             %{--title="${card.fileName}"--}%
                                             %{--class="img-rounded "/>--}%
                                    %{--</p>--}%
                                %{--</g:if>--}%
                                %{--<ul class="thumbnails">--}%
                                    %{--<g:each in="${card.images}" var="image">--}%
                                        %{--<li class="span4">--}%
                                            %{--<div class="thumbnail">--}%
                                                %{--<img data-src="holder.js/150x100"--}%
                                                     %{--src="${createLink(action: 'retrieveImage', params: [file: image.fileName +'_thumb.png'])}"--}%
                                                     %{--alt=""--}%
                                                     %{--target="_blank">--}%
                                            %{--</div>--}%
                                        %{--</li>--}%
                                    %{--</g:each>--}%
                                %{--</ul>--}%

                            </div>
                        </div>
                    </div>
                </div>
            </g:each>
            <g:paginate action="index"
                        params="[q: params.q]"
                        total="${searchResult.total}"
                        prev="&lt; previous" next="next &gt;"/>
        </g:if>

    </div>
</div>

</body>

</html>