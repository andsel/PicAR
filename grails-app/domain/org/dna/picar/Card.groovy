package org.dna.picar

class Card {

    def messageSource

    static searchable = {
        fund component: true
        only = ['inventoryNumber',
                'signature',
                'objectDescr',
                'subject',
                'title',
                'author',
                'authorNotFound',
                'printer',
                'editor',
                'inscription',
                'localization',
                'techniqueDescr',
                'primarySupportDescr',
                'secondarySupportDescr',
                'statusDetailsDescr',
                'sources',
                'bibliography',
                'dateCreated',
                'observations',
                'dating',
                'colorIndicationDescr']
    }

    Long inventoryNumber
    String signature
    String object //closed vocabulary
    String objectDescr //extended object to let it searchable
    String subject
    String title
    String author
    Boolean authorNotFound = false
    String printer
    String editor
    String inscription
    String localization
    String technique //closed vocabulary
    String techniqueDescr
    String primarySupport //closed vocabulary
    String primarySupportDescr
    Dimension primarySupportDimensions
    String secondarySupport //closed vocabulary
    String secondarySupportDescr
    Dimension secondarySupportDimensions
    String statusDetails
    String conservationStatus //closed vocabulary
    String conservationStatusDescr
    List<String> sources
    List<String> bibliography
    Date dateCreated
    String observations
    String dating
    User creator
    List images
    String colorIndication //closed vocabulary
    String colorIndicationDescr

    static hasMany = [images: StoredImage, bibliography: String, sources: String]
    static belongsTo = [fund: Fund]

    static embedded = ['primarySupportDimensions', 'secondarySupportDimensions']

    static constraints = { // Limit upload file size to 20MB
        inventoryNumber nullable: false, unique: true
        signature nullable: false, blank: false, unique: true
        fund nullable: false, component: true
        subject nullable: false
        title nullable: true
        author nullable: true, validator: {val, obj ->
            val && !obj.authorNotFound || !val && obj.authorNotFound
        }

        printer nullable: true
        editor nullable: true
        inscription nullable: false
        localization nullable: true
        technique nullable: false
        primarySupport nullable: false
        primarySupportDimensions nullable: false
        secondarySupport nullable: true
        secondarySupportDimensions nullable: true
        statusDetails nullable: false
        conservationStatus nullable: false
        sources nullable: false
        bibliography nullable: false
        observations nullable: false
        dating nullable: true/*, validator: {String val, Card obj ->
            if (!val) {
                return true
            }
            patterns.any {pattern -> val ==~ pattern}
        }                        */
        colorIndication nullable: true

        subject widget: 'textarea' //TODO remove once the UI is fixed
        inscription widget: 'textarea' //TODO remove once the UI is fixed
        observations widget: 'textarea' //TODO remove once the UI is fixed
        statusDetails widget: 'textarea' //TODO remove once the UI is fixed
    }

    static mapping = {
        subject type: 'text'
        inscription type: 'text'
        statusDetails type: 'text'
        observations type: 'text'
        bibliography type: 'text'
        sources type: 'text'
    }

    /**
     * Used to keep in synch closed vocabulary props with their description property
     * */
    protected alignClosedProperties(Locale locale) {
        objectDescr = messageSource.getMessage("card.object.${object}" as String, [] as Object[], locale)
        techniqueDescr = messageSource.getMessage("card.technique.${technique}" as String, [] as Object[], locale)
        primarySupportDescr = messageSource.getMessage("card.primarySupport.${primarySupport}" as String, [] as Object[], locale)
        secondarySupportDescr = messageSource.getMessage("card.secondarySupport.${secondarySupport}" as String, [] as Object[], locale)
        conservationStatusDescr = messageSource.getMessage("card.conservationStatus.${conservationStatus}" as String, [] as Object[], locale)
        colorIndicationDescr = messageSource.getMessage("card.colorIndication.${colorIndication}" as String, [] as Object[], locale)
    }

//    private static def fulldatePattern = /\d{1,2}\/\d{1,2}\/\d{4}/
//    private static def halfdatePattern = /\d{1,2}\/\d{4}/
//    private static def yearPattern = /\d{4}/
//    private static def yearRangePattern = /\d{4}\s*-\s*\d{4}/
//    private static List patterns = [fulldatePattern, halfdatePattern, yearPattern, yearRangePattern]
}

class Dimension {
    Integer height
    Integer width

    static constraints = {
        height nullable: true
        width nullable: true
    }
}
