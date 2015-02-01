package org.dna.picar

class CardService {

    protected Map<String, Set> autocompletions = [:]

    def initAutocompletion(List<String> fields) {
        fields.each {field ->
            String query = "select c.$field from Card c group by c.$field"
            def res = Card.executeQuery(query)
            res = res.findAll {it != null}
            autocompletions."$field" = res as Set
        }
    }


    Set<String> searchMatching(String field, String nameFragment) {
        log.debug "searchMatching invoked with query $nameFragment"

        nameFragment = nameFragment.toLowerCase()

        autocompletions."$field".findAll { it.toLowerCase().startsWith(nameFragment)}.sort() as Set
    }


    def updatedCard(Card card) {
        if (card.localization) {
            autocompletions.localization << card.localization
        }
        if (card.author) {
            autocompletions.author << card.author
        }
        if (card.editor) {
            autocompletions.editor << card.editor
        }
        if (card.printer) {
            autocompletions.printer << card.printer
        }
    }

}