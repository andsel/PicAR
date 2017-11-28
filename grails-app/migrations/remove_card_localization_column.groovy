/**
 * Used to remove the replicated Funds created during ech new startup, due to bad BootStrap script
 */

databaseChangeLog = {

    changeSet(author: "andrea", id: "remove_card_localization_column") {
        grailsChange {
            change {
                sql.execute("DROP COLUMN localization FROM card")
            }
            rollback {
            }
        }
    }
}

