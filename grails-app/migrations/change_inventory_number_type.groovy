/**
 * Change the type of Card's inventoryNumber from String to integer
 */

databaseChangeLog = {
    changeSet(author: "andrea", id: "change_inventory_number_type") {
        modifyDataType(tableName: 'card', columnName: 'inventory_number', newDataType: 'bigint')
    }
}
