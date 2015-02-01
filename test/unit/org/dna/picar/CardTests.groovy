package org.dna.picar



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Card)
@Mock([Fund])
class CardTests {

    def card

    @Before
    void setUp() {
        card = new Card(inventoryNumber: '1',
                signature: 'SIG_1',
                subject: 'montagne',
                author: 'John the Picture',
                creator: new User(),
                fund: new Fund(),
                inscription: 'inscription',
                technique: 'bianco/nero' ,
                primarySupport: 'paper',
                primarySupportDimensions: new Dimension(height: 200, width: 300),
                statusDetails: 'perfect',
                sources: 'sources',
                bibliography: ['bibliography'],
                observations: 'observation',
                object: 'Monte canin')
        mockForConstraintsTests(Card, [card])
    }

    void testDatingValidator_long() {
        card.dating = '20/10/1977'
        assert card.validate()
    }

    void testDatingValidator_medium() {
        card.dating = '10/1977'
        assert card.validate()
    }

    void testDatingValidator_short() {
        card.dating = '1977'
        assert card.validate()
    }

    void testDatingValidator_range() {
        card.dating = '1910 -  1913'
        assert card.validate()
    }
}
