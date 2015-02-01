dataSource {
    pooled = true
    driverClassName = "org.h2.Driver"
    username = "sa"
    password = ""
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "create" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:h2:mem:devDb;MVCC=TRUE"
            pooled = true
            dialect = "org.hibernate.dialect.H2Dialect"
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE"
        }
    }
    production {
        dataSource {
            driverClassName = "com.mysql.jdbc.Driver"
            dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
            dbCreate = ""
            url = "jdbc:mysql://localhost:3306/picar?autoReconnect=true"
            username = "picar"
            password = ""
            pooled = true
            properties {
                maxActive = 50
                maxIdle = 25
                minIdle = 5
                initialSize = 5
                minEvictableIdleTimeMillis = 60000
                timeBetweenEvictionRunsMillis = 60000
                maxWait = 10000
                validationQuery = "SELECT 1"
            }
        }
    }

    standalone {
        dataSource {
            workspace.dir = System.getProperty('workspace.dir')
            dbCreate = ""
            url = "jdbc:h2:${workspace.dir}/standaloneDb;MVCC=TRUE"
            pooled = true
            dialect = "org.hibernate.dialect.H2Dialect"
        }
    }

}
