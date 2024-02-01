import React from "react";
import WritePost from "./WritePost";

import "./styles/Feed.scss";
import Post from "./Post";
import axios from "axios";
import {getAllPosts, getHomeTimeline, getPostById} from "../api.js";
import {useSelector} from "react-redux";
import {selectAccountId} from "../store/Account.js";

export default function Feed() {
    const [selectedTab, setSelectedTab] = React.useState(0);
    const [posts, setPosts] = React.useState([]);

    const accountId = useSelector(selectAccountId)

    React.useEffect(() => {
        if (!accountId) return;
        getHomeTimeline().then(async (res) => {
            if (!res.ok) return;
            const timelinePosts = [];
            for (const postInfos of res.data.posts) {
                const postRequest = await getPostById(postInfos.postId);
                if (!postRequest.ok) continue;
                if (postInfos.userWhoLiked !== "null" && postInfos.userWhoLiked !== accountId)
                    postRequest.data.likedBy = postInfos.userWhoLiked;
                timelinePosts.push(postRequest.data);
            }
            setPosts(timelinePosts);
        });
        document.title = "TinyX";
    }, [accountId]);

    const onPostDeletion = (id) => {
        setPosts(posts.filter((post) => post.id !== id));
    }

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
                    onDelete={onPostDeletion}
                    likedBy={post.likedBy}
                />
            ))}
        </div>
    );
}
