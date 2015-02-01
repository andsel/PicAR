package org.dna.picar
class User {

    transient springSecurityService

	String username
	String password
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
    Date dateCreated
    String email

    static constraints = {
        username blank: false, unique: true
        password blank: false
        email(nullable: true, blank: true/*, email: true*/)
    }

    static mapping = {
//        table '`user`' //HACK for PostgresSQL
//        password column: '`password`'
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}


    String toString() {
        username
    }
}
