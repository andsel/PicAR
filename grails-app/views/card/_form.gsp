%{--<fieldset>--}%
    %{--<g:form class="form-horizontal" action="create">--}%

<r:script>
    function typeheadCallback(query, process) {
        return $.get($(this)[0].$element[0].dataset.link,
            { query: query },
            function (data) {
                return process(data.options);
            });
    }

    $('#author').typeahead({source: typeheadCallback});
    $('#printer').typeahead({source: typeheadCallback});
    $('#editor').typeahead({source: typeheadCallback});
    $('#localization').typeahead({source: typeheadCallback});

    function bindAddBibliographyAction() {
        $('#addBibliographyAction').click(function () {
            var anchor = $('#bibliographyList').last();
            var lastInput = $('textarea[id^="bibliography["]').last();
            var id = $(lastInput).attr('id');
            var maxIndex = parseInt(id.replace('bibliography[', '').replace(']', ''));

            var nextIndex = maxIndex + 1;
            $('<div class="control-group"><div class="controls"><textarea rows="5" cols="40" class="span12" id="bibliography['+ nextIndex +']" value="" name="bibliography['+ nextIndex +']"></textarea></div></div>').appendTo(anchor);

            var addedInput = $('textarea[id^="bibliography[' + nextIndex + ']"]');
            addedInput.focus();
            $('html, body').animate({
                scrollTop: $(addedInput).offset().top
            }, 2000);
        });
    }

    function bindAddSourcesAction() {
        $('#addSourcesAction').click(function () {
            var anchor = $('#sourcesList').last();
            var lastInput = $('textarea[id^="sources["]').last();
            var id = $(lastInput).attr('id');
            var maxIndex = parseInt(id.replace('sources[', '').replace(']', ''));

            var nextIndex = maxIndex + 1;
            $('<div class="control-group"><div class="controls"><textarea rows="5" cols="40" class="span12" id="sources['+ nextIndex +']" value="" name="sources['+ nextIndex +']"></textarea></div></div>').appendTo(anchor);

            var addedInput = $('textarea[id^="sources[' + nextIndex + ']"]');
            addedInput.focus();
            $('html, body').animate({
                scrollTop: $(addedInput).offset().top
            }, 2000);
        });
    }


    $(document).ready(function () {
        $('#authorNotFound').click(function () {
            console.log($(this).val());
            if ($(this).is(':checked')) {
                //if value if true then disable the author field
                $("#author").attr("readonly", "true");
            } else {
                $("#author").removeAttr("readonly");
            }
        });

        bindAddBibliographyAction();
        bindAddSourcesAction();
    });
</r:script>

        <h3>
            <g:message code="card.identity.section.title" default="Identification"/>
        </h3>

        <div class="row-fluid">
            <div class="span6">
                %{--<fieldset>--}%
                <f:field bean="cardInstance" property="inventoryNumber"/>
                <f:field bean="cardInstance" property="signature"/>
            </div>
            <div class="span6">
                <f:field bean="cardInstance" property="fund"/>
                <f:field bean="cardInstance" property="dating"/>
            </div>
        </div>


        <h3>
            <g:message code="card.authoring.section.title" default="Authors"/>
        </h3>

        <div class="row-fluid">
            <div class="span6">
                <div class="control-group ">
                    <label class="control-label" for="author"><g:message code="card.author.label" default="Author"/></label>
                    <div class="controls ${hasErrors(bean: 'cardInstance', field: 'author', 'error')}">
                        <g:if test="${cardInstance?.authorNotFound}">
                            <g:field name="author" type="text" value="${cardInstance?.author}" readonly="${cardInstance?.authorNotFound ? 'disabled': ''}"/>
                        </g:if>
                        <g:else>
                            <input type="text" name="author" id="author" value="${cardInstance?.author}"
                                data-link="${createLink(controller: 'card', action: 'autoCompletion', params: ['field': 'author'])}"
                                autocomplete="off"/>
                        </g:else>
                    </div>
                </div>
                <div class="control-group ">
                    <div class="controls">
                        <g:checkBox name="authorNotFound" value="${cardInstance?.authorNotFound}"/> <g:message code="card.authorNotFound.label" default="Author not found"/>
                    </div>
                </div>
            </div>
            <div class="span6">
                %{--<f:field bean="cardInstance" property="printer"/>--}%
                <div class="control-group ">
                    <label class="control-label" for="printer"><g:message code="card.printer.label"/></label>
                    <div class="controls ${hasErrors(bean: 'cardInstance', field: 'printer', 'error')}">
                        <input type="text" name="printer" id="printer" value="${cardInstance?.printer}"
                               data-link="${createLink(controller: 'card', action: 'autoCompletion', params: ['field': 'printer'])}"
                               autocomplete="off"/>
                    </div>
                </div>

                %{--<f:field bean="cardInstance" property="editor"/>--}%
                <div class="control-group ">
                    <label class="control-label" for="editor"><g:message code="card.editor.label"/></label>
                    <div class="controls ${hasErrors(bean: 'cardInstance', field: 'printer', 'error')}">
                        <input type="text" name="editor" id="editor" value="${cardInstance?.editor}"
                               data-link="${createLink(controller: 'card', action: 'autoCompletion', params: ['field': 'editor'])}"
                               autocomplete="off"/>
                    </div>
                </div>
            </div>
        </div>


        <h3>
            <g:message code="card.description.section.title" default="Description"/>
        </h3>

        <div class="row-fluid">
            <div class="control-group ">
                <label class="control-label" for="title"><g:message code="card.title.label" default="Title"/></label>
                <div class="controls ${hasErrors(bean: 'cardInstance', field: 'title', 'error')}">
                    <g:field class="span12" name="title" type="text" value="${cardInstance?.title}"/>
                </div>
            </div>

            <div class="control-group ">
                <label class="control-label" for="subject"><g:message code="card.subject.label"/></label>
                <div class="controls ${hasErrors(bean: 'cardInstance', field: 'subject', 'error')}">
                    <g:textArea name="subject" value="${cardInstance.subject}" rows="5" cols="40" class="span12"/>
                </div>
            </div>

            <div class="control-group ">
                <label class="control-label" for="localization"><g:message code="card.localization.label"/></label>
                <div class="controls ${hasErrors(bean: 'cardInstance', field: 'localization', 'error')}">
                    <g:field class="span12" name="localization" type="text" value="${cardInstance?.localization}"
                             data-link="${createLink(controller: 'card', action: 'autoCompletion', params: ['field': 'localization'])}"
                             autocomplete="off"/>
                </div>
            </div>

            <div class="control-group ">
                <label class="control-label" for="inscription"><g:message code="card.inscription.label"/></label>
                <div class="controls ${hasErrors(bean: 'cardInstance', field: 'inscription', 'error')}">
                    <g:textArea name="inscription" value="${cardInstance.inscription}" rows="5" cols="40" class="span12"/>
                </div>
            </div>

        </div>

        <h3>
            <g:message code="card.technicalDescription.section.title" default="Technical description"/>
        </h3>

        <div class="control-group ">
            <label class="control-label" for="object"><g:message code="card.object.label" default="Object"/></label>
            <div class="controls ${hasErrors(bean: 'cardInstance', field: 'object', 'error')}">
                <g:select name="object" from="${['positive', 'negative', 'slide', 'virtual_image', 'photomechanical_process', 'unicum']}"
                          noSelection="${['other': message(code: 'card.object.other')]}"
                          valueMessagePrefix="card.object"
                          value="${cardInstance?.object}"/>
            </div>
        </div>

        <div class="control-group ">
            <label class="control-label" for="colorIndication"><g:message code="card.colorIndication.label" default="Color indication"/></label>
            <div class="controls ${hasErrors(bean: 'cardInstance', field: 'colorIndication', 'error')}">
                <g:select name="colorIndication"
                          from="${['black_white', 'colors', 'hand_coloured', 'miscellaneous']}"
                          valueMessagePrefix="card.colorIndication"
                          value="${cardInstance?.colorIndication}"/>
            </div>
        </div>

        <div class="control-group ">
            <label class="control-label" for="technique"><g:message code="card.technique.label" default="Photo technique"/></label>
            <div class="controls ${hasErrors(bean: 'cardInstance', field: 'technique', 'error')}">
                <g:select name="technique"
                          noSelection="${['not_identified': message(code: 'card.technique.not_identified')]}"
                          from="${['albumina', 'ambrotipo', 'aristotipo', 'autocromia', 'calotype', 'carbon', 'salt_paper', 'cyanotype', 'collodion', 'daguerreotype', 'tintype', 'virtual_picture', 'jelly_chromogen_development', 'jelly_silver_salt', 'bichromate_rubber', 'planotipo', 'polaroid', 'not_available']}"
                          valueMessagePrefix="card.technique"
                          value="${cardInstance?.technique}"/>
            </div>
        </div>

        <div class="row-fluid">
            <div class="span12">
                <div class="control-group ">
                    <label class="control-label" for="primarySupport"><g:message code="card.primarySupport.label" default="Primary support"/></label>
                    <div class="controls">
                        <g:select name="primarySupport"
                                  from="${['paper', 'metal', 'film', 'glass']}"
                                  valueMessagePrefix="card.primarySupport"
                                  value="${cardInstance?.primarySupport}"/>
                        (mm)
                        <g:field class="input-mini ${hasErrors(bean: 'cardInstance', field: 'primarySupportDimensions.height', 'error')}"
                                 name="primarySupportDimensions.height" type="number" value="${cardInstance?.primarySupportDimensions?.height}"/> h
                        <g:field class="input-mini ${hasErrors(bean: 'cardInstance', field: 'primarySupportDimensions.width', 'error')}"
                                 name="primarySupportDimensions.width" type="number" value="${cardInstance?.primarySupportDimensions?.width}"/> l
                    </div>
                </div>
            </div>
        </div>

        <div class="row-fluid">
            <div class="span12">
                <div class="control-group ">
                    <label class="control-label" for="secondarySupport"><g:message code="card.secondarySupport.label" default="Secondary support"/></label>
                    <div class="controls ${hasErrors(bean: 'cardInstance', field: 'secondarySupportDimensions.width', 'error')}">
                        <g:select name="secondarySupport"
                                  noSelection="${['null': message(code: 'card.secondarySupport.null')]}"
                                  from="${['paper', 'cardboard']}"
                                  valueMessagePrefix="card.secondarySupport"
                                  value="${cardInstance?.secondarySupport}"/>
                        (mm)
                        <g:field class="input-mini ${hasErrors(bean: 'cardInstance', field: 'secondarySupportDimensions.height', 'error')}"
                                 name="secondarySupportDimensions.height" type="number" value="${cardInstance?.secondarySupportDimensions?.height}"/> h
                        <g:field class="input-mini ${hasErrors(bean: 'cardInstance', field: 'secondarySupportDimensions.width', 'error')}"
                                 name="secondarySupportDimensions.width" type="number" value="${cardInstance?.secondarySupportDimensions?.width}"/> l
                    </div>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="conservationStatus"><g:message code="card.conservationStatus.label"/></label>
                <div class="controls ${hasErrors(bean: 'cardInstance', field: 'statusDetails', 'error')}">
                    %{--<g:textArea name="statusDetails" value="${cardInstance.statusDetails}" rows="5" cols="40" class="span12"/>--}%
                    <g:select name="conservationStatus"
                        from="${['good', 'bad', 'fairly_good', 'second_rate']}"
                        valueMessagePrefix="card.conservationStatus"
                        value="${cardInstance?.conservationStatus}"/>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="statusDetails"><g:message code="card.statusDetails.label"/></label>
                <div class="controls ${hasErrors(bean: 'cardInstance', field: 'statusDetails', 'error')}">
                    <g:textArea name="statusDetails" value="${cardInstance.statusDetails}" rows="5" cols="40" class="span12"/>
                </div>
            </div>
        </div>


        <h3>
            <g:message code="card.documentation.section.title" default="Other"/>
        </h3>

        <div class="row-fluid">
            <div class="control-group">
                <label class="control-label" for="bibliography"><g:message code="card.bibliography.label"/></label>
                <a href="#" id="addBibliographyAction">
                    <i class="icon-plus-sign"></i>
                </a>
            </div>


            <g:if test="${!(cardInstance?.bibliography) || cardInstance?.bibliography?.empty}">
                <div class="control-group">
                    <div class="controls">
                        <g:field class="input-xxlarge ${hasErrors(bean: 'cardInstance', field: 'bibliography[0]', 'error')}"
                                 type="text" name="bibliography[0]"/>
                    </div>
                </div>
            </g:if>
            <g:else>
                <g:each in="${cardInstance.bibliography}" var="bibliography" status="i">
                    <div class="control-group">
                        <div class="controls">
                            <g:textArea class="span12 ${hasErrors(bean: 'cardInstance', field: 'bibliography[i]', 'error')}"
                                     rows="5" cols="40" name="bibliography[${i}]" value="${cardInstance?.bibliography[i]}"/>
                        </div>
                    </div>
                </g:each>
            </g:else>

            <span id="bibliographyList"></span>
        </div>

        <div class="row-fluid">
            %{--<f:field bean="cardInstance" property="sources"/>--}%

            <div class="control-group">
                <label class="control-label" for="sources"><g:message code="card.sources.label"/></label>
                <a href="#" id="addSourcesAction">
                    <i class="icon-plus-sign"></i>
                </a>
            </div>


            <g:if test="${!(cardInstance?.sources) || cardInstance?.sources?.empty}">
                <div class="control-group">
                    <div class="controls">
                        <g:textArea class="span12 ${hasErrors(bean: 'cardInstance', field: 'sources[0]', 'error')}"
                                    rows="5" cols="40" name="sources[0]"/>
                    </div>
                </div>
            </g:if>
            <g:else>
                <g:each in="${cardInstance.sources}" var="source" status="i">
                    <div class="control-group">
                        <div class="controls">
                            <g:textArea class="span12 ${hasErrors(bean: 'cardInstance', field: 'sources[i]', 'error')}"
                                     rows="5" cols="40" name="sources[${i}]" value="${cardInstance?.sources[i]}"/>
                        </div>
                    </div>
                </g:each>
            </g:else>

            <span id="sourcesList"></span>


            <div class="control-group ">
                <label class="control-label" for="observations"><g:message code="card.observations.label"/></label>
                <div class="controls ${hasErrors(bean: 'cardInstance', field: 'observations', 'error')}">
                    <g:textArea name="observations" value="${cardInstance.observations}" rows="5" cols="40" class="span12"/>
                </div>
            </div>
        </div>

        %{--<div class="form-actions">--}%
            %{--<button type="submit" class="btn btn-primary">--}%
                %{--<i class="icon-ok icon-white"></i>--}%
                %{--<g:message code="default.button.create.label" default="Create" />--}%
            %{--</button>--}%
        %{--</div>--}%

    %{--</g:form>--}%
%{--</fieldset>--}%