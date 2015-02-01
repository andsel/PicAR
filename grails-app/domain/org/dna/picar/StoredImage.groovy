package org.dna.picar

/**
 * Contains the details of single imported image
 */
class StoredImage {
    static belongsTo = [card: Card]

    String fileName
    String extension

    static constraints = {
        fileName nullable: true
    }

    String getCompleteName() {
        return "${fileName}.${extension}"
    }
}
