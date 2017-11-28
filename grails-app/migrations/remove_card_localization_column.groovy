/**
 * Used to remove the replicated Funds created during ech new startup, due to bad BootStrap script
 */

databaseChangeLog = {

    changeSet(author: "andrea", id: "remove_card_localization_column") {
        grailsChange {
            change {
<<<<<<< HEAD
                sql.execute("DROP COLUMN localization FROM card")
=======
                sql.execute("ALTER TABLE card DROP COLUMN localization")
>>>>>>> Rimosso campo Localication, sistemato l'export, rinominata label Datazione -> Cronologia
            }
            rollback {
            }
        }
    }
}

