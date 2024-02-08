db = db.getSiblingDB(process.env.TINYX_GATEWAY_MONGODB_DATABASE);
db.createUser(
    {
        user: process.env.TINYX_GATEWAY_MONGODB_USER,
        pwd: process.env.TINYX_GATEWAY_MONGODB_PASSWORD,
        roles: [
            {
                role: "readWrite",
                db: process.env.TINYX_GATEWAY_MONGODB_DATABASE
            }
        ]
    }
);

db = db.getSiblingDB(process.env.TINYX_POST_MONGODB_DATABASE);
db.createUser(
    {
        user: process.env.TINYX_POST_MONGODB_USER,
        pwd: process.env.TINYX_POST_MONGODB_PASSWORD,
        roles: [
            {
                role: "readWrite",
                db: process.env.TINYX_POST_MONGODB_DATABASE
            }
        ]
    }
);

db = db.getSiblingDB(process.env.TINYX_SEARCH_MONGODB_DATABASE);
db.createUser(
    {
        user: process.env.TINYX_SEARCH_MONGODB_USER,
        pwd: process.env.TINYX_SEARCH_MONGODB_PASSWORD,
        roles: [
            {
                role: "readWrite",
                db: process.env.TINYX_SEARCH_MONGODB_DATABASE
            }
        ]
    }
);

db = db.getSiblingDB(process.env.TINYX_USER_TIMELINE_MONGODB_DATABASE);
db.createUser(
    {
        user: process.env.TINYX_USER_TIMELINE_MONGODB_USER,
        pwd: process.env.TINYX_USER_TIMELINE_MONGODB_PASSWORD,
        roles: [
            {
                role: "readWrite",
                db: process.env.TINYX_USER_TIMELINE_MONGODB_DATABASE
            }
        ]
    }
);

db = db.getSiblingDB(process.env.TINYX_HOME_TIMELINE_MONGODB_DATABASE);
db.createUser(
    {
        user: process.env.TINYX_HOME_TIMELINE_MONGODB_USER,
        pwd: process.env.TINYX_HOME_TIMELINE_MONGODB_PASSWORD,
        roles: [
            {
                role: "readWrite",
                db: process.env.TINYX_HOME_TIMELINE_MONGODB_DATABASE
            }
        ]
    }
);