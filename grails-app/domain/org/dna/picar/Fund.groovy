package org.dna.picar

class Fund {
    String name

    static searchable = true

    static constraints = {
        name nullable: false
    }

    @Override
    def String toString() {
        name
    }
}
