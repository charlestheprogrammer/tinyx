import React from "react";
import WritePost from "./WritePost";

import "./styles/Feed.scss";
import Post from "./Post";
import axios from "axios";
import { getAllPosts } from "../api.js";

export default function Feed() {
    const [selectedTab, setSelectedTab] = React.useState(0);
    const [posts, setPosts] = React.useState([]);

    React.useEffect(() => {
        getAllPosts().then((res) => {
            if (!res.ok) return;
            setPosts(res.data);
        });
    }, []);

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
            {posts.map((post) => (
                <Post
                    key={post.id}
                    user={post.author}
                    content={post.text}
                    image={post.media}
                    date={post.created_date}
                    post={post.repost}
                    id={post.id}
                />
            ))}
        </div>
    );
}
