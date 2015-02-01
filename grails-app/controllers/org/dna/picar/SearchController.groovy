package org.dna.picar

class SearchController {

    def searchableService

    def index() {
        if (!params.q?.trim()) {
            return [:]
        }
        try {
//            return [searchResult: Card.search(params.q, params)]
            //NB the highlight on 'subject' cause problems with cross relations references, make the service throw
            //an exception
//            params.withHighlighter = {highlighter, index, sr ->
//                if (!sr.highlights) {
//                    sr.highlights = []
//                }
//                def matchedFragment = highlighter.fragment('subject')
//                sr.highlights[index] = matchedFragment ?: ''
//            }

//            def res = searchableService.search(params.q, params)
            def res = Card.search(params.q, params + [sort: 'inventoryNumber', order: 'asc'])
            return [searchResult: res]
        } catch (ex) {
            log.warn "Exception in search method ", ex
            return [parseException: true]
        }
    }
}
