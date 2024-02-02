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
