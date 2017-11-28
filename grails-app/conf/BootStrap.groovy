import grails.util.Environment
import org.dna.picar.UserRole
import org.dna.picar.User
import org.dna.picar.Role
import org.dna.picar.Card
import org.dna.picar.Fund
import org.dna.picar.Dimension

class BootStrap {

    def cardService

    def springSecurityService

    def init = { servletContext ->
        println "Launching with environment ${Environment.current.name}"
        initRoles()
        switch (Environment.current) {
            case Environment.CUSTOM: //Environment.current.name 'standalone'
                createAdminUser()
//                createFunds()
                createRealCataloguer()
                break
            case Environment.DEVELOPMENT:
                createAdminUser()
                createFakeCataloguers()
                createFunds()
                createFakeCards()
                break
            case Environment.PRODUCTION:
                createAdminUser()
                createRealCataloguer()
                //DBG cloud
//                createFakeUsers()
//                createFakeCataloguers()
                //DBG
                break
        }

        cardService.initAutocompletion(['printer', 'author', 'editor'])
    }

    def destroy = {
    }

    private initRoles() {
        def userRole = Role.findByAuthority('ROLE_USER') ?: new Role(authority: 'ROLE_USER').save()
        def cataloguerRole = Role.findByAuthority('ROLE_CATALOG') ?: new Role(authority: 'ROLE_CATALOG').save()
        def adminRole = Role.findByAuthority('ROLE_ADMIN') ?: new Role(authority: 'ROLE_ADMIN').save()
    }

    private createAdminUser() {
        if (User.findByUsername("admin")) {
            log.info "Admin user already exists"
            return
        }

        def adminRole = Role.findByAuthority('ROLE_ADMIN') ?: new Role(authority: 'ROLE_ADMIN').save()
        if (!User.findByUsername('admin')) {
            def admin = new User(username:"admin",
                    password: "pazzwork",
                    enabled: true).save(flush: true, failOnError: true)
            UserRole.create(admin, adminRole)
        }
    }

   private createFunds() {
       Fund fund1 = Fund.findByName('Fondo SAT') ?: new Fund(name: 'Fondo SAT').save(failOnError: true)
       Fund fund2 = Fund.findByName('Fondo Pedrotti') ?: new Fund(name: 'Fondo Pedrotti').save(failOnError: true)
       Fund fund3 = Fund.findByName('Fondo Alpinistico') ?: new Fund(name: 'Fondo Alpinistico').save(failOnError: true)
       Fund fund4 = Fund.findByName('Fondo Montagna') ?: new Fund(name: 'Fondo Montagna').save(failOnError: true)
//       Fund fund1 = new Fund(name: 'Fondo SAT').save(failOnError: true)
//       Fund fund2 = new Fund(name: 'Fondo Pedrotti').save(failOnError: true)
//       Fund fund3 = new Fund(name: 'Fondo Alpinistico').save(failOnError: true)
//       Fund fund4 = new Fund(name: 'Fondo Montagna').save(failOnError: true)
   }


    private createFakeCataloguers() {
        def cataloguerRole = Role.findByAuthority('ROLE_CATALOG')
        if (!User.findByUsername('gino')) {
            def shop = new User(username: "gino",
                password: "passwd",
                enabled: true).save(flush: true, failOnError: true)
            shop.save()
            UserRole.create(shop, cataloguerRole)
        }
    }

    private createRealCataloguer() {
        def cataloguerRole = Role.findByAuthority('ROLE_CATALOG')
        if (!User.findByUsername('Utente')) {
            def shop = new User(username:"Utente",
                    password: "pazzwork",
                    enabled: true).save(flush: true, failOnError: true)
            shop.save()
            UserRole.create(shop, cataloguerRole)
        }
    }

    private createFakeCards() {
        def user = User.findByUsername('gino')
        assert user != null

        def fundSAT = Fund.findByName('Fondo SAT')
        def fundPedrotti = Fund.findByName('Fondo Pedrotti')

        for (int i in  1..40) {
            if (!Card.findByInventoryNumber(i)) {
                def fund = i % 2 ? fundSAT : fundPedrotti
                Card card = new Card(inventoryNumber: i, signature: "SIG_${i}", title: "bla ${i}", subject: "immagine monti ${i}",
                    author: 'me', creator: user, object: 'negative', fund: fund, inscription: 'iscrizioni della foto',
                    technique: 'albumina', primarySupport: 'paper', primarySupportDimensions: new Dimension(height: 200, width: 300),
                        statusDetails: 'conservato bene tutto a posto', sources: 'Documenti di reiferimento',
                        bibliography: ['Note bibliografiche varie'],
                        observations: 'Osservazioni ',
                        conservationStatus: 'good'
                    )
                card.alignClosedProperties(Locale.ITALIAN)
                card.save(failOnError: true)
            }
        }
    }
}
