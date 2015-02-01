package org.dna.picar

/**
 * Handles administrative console actions
 * */
class UserController {

    def create() {
        switch (request.method) {
            case 'GET':
                [user: new User(params)]
                break
            case 'POST':
                def user = new User(params)
                user.enabled = true
                def cataloguerRole = Role.findByAuthority('ROLE_CATALOG')
                if (!user.save(flush: true)) {
                    render view: 'create', model: [user: user]
                    return
                }
                UserRole.create(user, cataloguerRole)

                flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), user.id])
                redirect action: 'list'
                break
        }
    }


    def edit(Long id) {
        switch (request.method) {
            case 'GET':
                [user: User.get(id)]
                break
            case 'POST':
                def user = User.get(id)
                user.password = params.password
//                def cataloguerRole = Role.findByAuthority('ROLE_CATALOG')
                if (!user.save(flush: true)) {
                    render view: 'edit', model: [user: user]
                    return
                }
//                UserRole.create(user, cataloguerRole)

                flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), user.id])
                redirect action: 'list'
                break
        }
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [userInstanceList: User.list(params), userInstanceTotal: User.count()]
    }
}
