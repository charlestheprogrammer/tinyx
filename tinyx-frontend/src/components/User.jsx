import React from "react";

import "./styles/User.scss";

import { Link, useParams } from "react-router-dom";
import Post from "./Post.jsx";
import axios from "axios";
import profilePicture from "../assets/pp.jpeg";

export default function User() {
    const { user } = useParams();

    const [posts, setPosts] = React.useState([]);
    const [userInfos, setUserInfos] = React.useState({});

    React.useEffect(() => {
        document.title = `@${user} | TinyX`;
        axios
            .get(`http://localhost:9000/api/user/by_username/${user}`)
            .then((res) => {
                setUserInfos(res.data);
            });
    }, [user]);

    React.useEffect(() => {
        if (!userInfos.id) return;
        axios
            .get(
                `http://localhost:9000/repo-post/api/posts/by_author/${userInfos.id}`
            )
            .then((res) => {
                setPosts(res.data);
            });
    }, [userInfos]);

    return (
        <div className="user">
            <div className="topBar">
                <Link to="/" className="backButton">
                    Back
                </Link>
                <div className="infos">
                    <p>@{user}</p>
                    <p>{posts.length ?? 0} posts</p>
                </div>
            </div>
            <div className="banner">
                <img
                    src={userInfos.bannerUri ?? "https://picsum.photos/800/300"}
                    alt=""
                />
            </div>
            <div className="profilePicture">
                <img src={userInfos.imageUri ?? profilePicture} alt="" />
            </div>
            <div className="actions">
                <button>Block</button>
                <button>Follow</button>
            </div>
            <div className="posts">
                {posts.map((post) => (
                    <Post
                        image={post.media}
                        user={post.author}
                        content={post.text}
                        date={post.created_date}
                        post={post.repost}
                        key={post.id}
                    />
                ))}
            </div>
        </div>
    );
}
