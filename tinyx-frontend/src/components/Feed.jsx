import React from "react";
import WritePost from "./WritePost";

import "./styles/Feed.scss";
import Post from "./Post";
import axios from "axios";

export default function Feed() {
    const [selectedTab, setSelectedTab] = React.useState(0);
    const [posts, setPosts] = React.useState([]);

    React.useEffect(() => {
        axios.get("http://localhost:9000/repo-post/api/posts").then((res) => {
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
                />
            ))}
        </div>
    );
}
