databaseChangeLog = {

    changeSet(author: "andrea (generated)", id: "1365173028171-1") {
        createTable(tableName: "card") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "cardPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "author", type: "varchar(255)")

            column(name: "author_not_found", type: "boolean") {
                constraints(nullable: "false")
            }

            column(name: "color_indication", type: "varchar(255)")

            column(name: "color_indication_descr", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "conservation_status", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "conservation_status_descr", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "creator_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "dating", type: "varchar(255)")

            column(name: "editor", type: "varchar(255)")

            column(name: "fund_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "inscription", type: "longvarchar") {
                constraints(nullable: "false")
            }

            column(name: "inventory_number", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "localization", type: "varchar(255)")

            column(name: "object", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "object_descr", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "observations", type: "longvarchar") {
                constraints(nullable: "false")
            }

            column(name: "primary_support", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "primary_support_descr", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "primary_support_dimensions_height", type: "integer")

            column(name: "primary_support_dimensions_width", type: "integer")

            column(name: "printer", type: "varchar(255)")

            column(name: "secondary_support", type: "varchar(255)")

            column(name: "secondary_support_descr", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "secondary_support_dimensions_height", type: "integer")

            column(name: "secondary_support_dimensions_width", type: "integer")

            column(name: "signature", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "status_details", type: "longvarchar") {
                constraints(nullable: "false")
            }

            column(name: "subject", type: "longvarchar") {
                constraints(nullable: "false")
            }

            column(name: "technique", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "technique_descr", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "title", type: "varchar(255)")
        }
    }

    changeSet(author: "andrea (generated)", id: "1365173028171-2") {
        createTable(tableName: "card_bibliography") {
            column(name: "card_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "bibliography_string", type: "longvarchar")

            column(name: "bibliography_idx", type: "integer")
        }
    }

    changeSet(author: "andrea (generated)", id: "1365173028171-3") {
        createTable(tableName: "card_sources") {
            column(name: "card_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "sources_string", type: "longvarchar")

            column(name: "sources_idx", type: "integer")
        }
    }

    changeSet(author: "andrea (generated)", id: "1365173028171-4") {
        createTable(tableName: "fund") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "fundPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "andrea (generated)", id: "1365173028171-5") {
        createTable(tableName: "registration_code") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "registration_PK")
            }

            column(name: "date_created", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "token", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "username", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "andrea (generated)", id: "1365173028171-6") {
        createTable(tableName: "role") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "rolePK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "authority", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "andrea (generated)", id: "1365173028171-7") {
        createTable(tableName: "stored_image") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "stored_imagePK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "card_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "extension", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "file_name", type: "varchar(255)")

            column(name: "images_idx", type: "integer")
        }
    }

    changeSet(author: "andrea (generated)", id: "1365173028171-8") {
        createTable(tableName: "user") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "userPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "account_expired", type: "boolean") {
                constraints(nullable: "false")
            }

            column(name: "account_locked", type: "boolean") {
                constraints(nullable: "false")
            }

            column(name: "date_created", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "email", type: "varchar(255)")

            column(name: "enabled", type: "boolean") {
                constraints(nullable: "false")
            }

            column(name: "password", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "password_expired", type: "boolean") {
                constraints(nullable: "false")
            }

            column(name: "username", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "andrea (generated)", id: "1365173028171-9") {
        createTable(tableName: "user_role") {
            column(name: "role_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "user_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "andrea (generated)", id: "1365173028171-10") {
        addPrimaryKey(columnNames: "role_id, user_id", constraintName: "user_rolePK", tableName: "user_role")
    }

    changeSet(author: "andrea (generated)", id: "1365173028171-16") {
        createIndex(indexName: "inventory_number_uniq_1365173027966", tableName: "card", unique: "true") {
            column(name: "inventory_number")
        }
    }

    changeSet(author: "andrea (generated)", id: "1365173028171-17") {
        createIndex(indexName: "signature_uniq_1365173027976", tableName: "card", unique: "true") {
            column(name: "signature")
        }
    }

    changeSet(author: "andrea (generated)", id: "1365173028171-18") {
        createIndex(indexName: "authority_uniq_1365173027991", tableName: "role", unique: "true") {
            column(name: "authority")
        }
    }

    changeSet(author: "andrea (generated)", id: "1365173028171-19") {
        createIndex(indexName: "username_uniq_1365173027996", tableName: "user", unique: "true") {
            column(name: "username")
        }
    }

    changeSet(author: "andrea (generated)", id: "1365173028171-11") {
        addForeignKeyConstraint(baseColumnNames: "creator_id", baseTableName: "card", constraintName: "FK2E7B106F4E6E4D", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
    }

    changeSet(author: "andrea (generated)", id: "1365173028171-12") {
        addForeignKeyConstraint(baseColumnNames: "fund_id", baseTableName: "card", constraintName: "FK2E7B10FEB0500E", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "fund", referencesUniqueColumn: "false")
    }

    changeSet(author: "andrea (generated)", id: "1365173028171-13") {
        addForeignKeyConstraint(baseColumnNames: "card_id", baseTableName: "stored_image", constraintName: "FK2C9CA9BF3E13682E", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "card", referencesUniqueColumn: "false")
    }

    changeSet(author: "andrea (generated)", id: "1365173028171-14") {
        addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "user_role", constraintName: "FK143BF46A6F27A66E", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "role", referencesUniqueColumn: "false")
    }

    changeSet(author: "andrea (generated)", id: "1365173028171-15") {
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "user_role", constraintName: "FK143BF46A14526A4E", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
    }
}
