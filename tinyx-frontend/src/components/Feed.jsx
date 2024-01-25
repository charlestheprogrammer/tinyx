import React from "react";
import WritePost from "./WritePost";

import "./styles/Feed.scss";
import Post from "./Post";

export default function Feed() {
    const [selectedTab, setSelectedTab] = React.useState(0);

    return (
        <div className="feed">
            <div className="feed__tabs">
                <div
                    className={`feed__tab ${selectedTab === 0 ? "--selected" : ""}`}
                    onClick={() => setSelectedTab(0)}
                >
                    For you
                </div>
                <div
                    className={`feed__tab ${selectedTab === 1 ? "--selected" : ""}`}
                    onClick={() => setSelectedTab(1)}
                >
                    Following
                </div>
            </div>
            <WritePost />
            <Post
                content="Salut les gars"
                date="Jan 23"
                user="username"
                image="https://source.unsplash.com/random"
            />
            <Post
                content="One of the two remaining northern white rhinos in the world, guarded 24 hours a day"
                date="Jan 23"
                user="username"
                image="https://pbs.twimg.com/media/GEgEsj_WcAA3npm?format=jpg&name=small"
            />
            <Post
                content="Bro thinks he's Maradona"
                date="18h"
                user="username"
                image="https://pbs.twimg.com/media/GEjIwX7WwAAp0TY?format=jpg&name=medium"
            />
            <Post
                content="Salut les gars"
                date="Jan 23"
                user="username"
                post={
                    <Post
                        content="Bro thinks he's Maradona"
                        date="18h"
                        user="username"
                        image="https://pbs.twimg.com/media/GEjIwX7WwAAp0TY?format=jpg&name=medium"
                    />
                }
            />
            <Post
                content="Salut les gars"
                date="Jan 23"
                user="username"
                image="https://source.unsplash.com/random"
            />
        </div>
    );
}
