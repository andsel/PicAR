/**
 * Used to remove the replicated Funds created during ech new startup, due to bad BootStrap script
 */

databaseChangeLog = {

    changeSet(author: "andrea", id: "remove_replicated_funds") {
        grailsChange {
            change {
                sql.execute("""
                    DELETE FROM FUND WHERE ID IN (
                       SELECT ID FROM FUND WHERE ID NOT IN (
                             SELECT f.ID FROM FUND AS f JOIN CARD AS c ON f.ID = c.FUND_ID
                       )
                    )
                """)
            }
            rollback {
            }
        }
    }
}

