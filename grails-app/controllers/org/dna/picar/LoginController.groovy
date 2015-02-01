package org.dna.picar


class LoginController {

    def springSecurityService

    def index() {}

    def authfail() {
    }

    /**
     *Used to redirect the normal list to their pages, and shop user to their home
     **/
    def route() {
        User user = User.get(springSecurityService.principal.id)
        log.debug "route for user of type ${user?.getClass()?.simpleName}"
        if (!user) {
            //not logged into user
            redirect controller: 'card', action: 'index'
            return
        }
        Role catalogRole = Role.findByAuthority("ROLE_CATALOG")
        Role adminRole = Role.findByAuthority("ROLE_ADMIN")

        if (user.authorities.contains(catalogRole)) {
            log.debug "redirected to cataloguer home"
            redirect controller: 'card', action: 'index'
            return
        } else if (user.authorities.contains(adminRole)) {
            log.debug "redirected to admin console"
            redirect controller: 'user', action: 'list'
            return
        }

        log.error "bad role"
    }

}
