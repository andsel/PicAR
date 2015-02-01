databaseChangeLog = {
    include file: 'Initial.groovy'

    include file: 'remove_replicated_funds.groovy'

    include file: 'change_inventory_number_type.groovy'
}