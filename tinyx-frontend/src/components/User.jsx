import React from "react";

import "./styles/User.scss";

import { Link, useParams } from "react-router-dom";
import Post from "./Post.jsx";
import axios from "axios";
import profilePicture from "../assets/pp.jpeg";
import {
    blockUser,
    followUser,
    getFollowersByUserId,
    getFollowsByUserId,
    getPostsByAuthor,
    getUserInfosByUsername,
    isUserBlocked,
    unblockUser,
    unfollowUser,
} from "../api.js";
import { selectAccountId } from "../store/Account.js";
import { useSelector } from "react-redux";

export default function User() {
    const { user } = useParams();
    const accountId = useSelector(selectAccountId);

    const [posts, setPosts] = React.useState([]);
    const [userInfos, setUserInfos] = React.useState({});
    const [isAccountOwner, setIsAccountOwner] = React.useState(false);
    const [following, setFollowing] = React.useState(false);
    const [followers, setFollowers] = React.useState([]);
    const [blocked, setBlocked] = React.useState(false);
    const [follows, setFollows] = React.useState([]);

    React.useEffect(() => {
        document.title = `@${user} | TinyX`;
        getUserInfosByUsername(user).then((res) => {
            if (!res.ok) return;
            setUserInfos(res.data);
            setIsAccountOwner(accountId === res.data.id);
        });
    }, [user]);

    React.useEffect(() => {
        if (!userInfos || !userInfos.id) return;
        setIsAccountOwner(accountId === userInfos.id);
        setFollowing(followers.some((follower) => follower === accountId));
        isUserBlocked(userInfos.id).then((res) => {
            console.log(res);
            setBlocked(res);
        });
    }, [accountId, userInfos]);

    React.useEffect(() => {
        if (!userInfos || !userInfos.id) return;
        getPostsByAuthor(userInfos.id).then((res) => {
            if (!res.ok) return;
            setPosts(res.data);
        });
        getFollowersByUserId(userInfos.id).then((res) => {
            setFollowers(res.data);
            setFollowing(res.data.some((follower) => follower === accountId));
        });
        getFollowsByUserId(userInfos.id).then((res) => {
            setFollows(res.data);
        });
    }, [userInfos]);

    const followCurrentUser = () => {
        if (!userInfos || !userInfos.id) return;
        followUser(userInfos.id).then((res) => {
            if (!res.ok) return;
            setFollowing(true);
            setFollowers([...followers, accountId]);
        });
    };

    const unfollowCurrentUser = () => {
        if (!userInfos || !userInfos.id) return;
        unfollowUser(userInfos.id).then((res) => {
            if (!res.ok) return;
            setFollowing(false);
            setFollowers(
                followers.filter((follower) => follower !== accountId)
            );
        });
    };

    const blockCurrentUser = () => {
        if (!userInfos || !userInfos.id) return;
        blockUser(userInfos.id).then((res) => {
            if (!res.ok) return;
            setBlocked(true);
            setFollowing(false);
            setFollowers(
                followers.filter((follower) => follower !== accountId)
            );
        });
    };

    const unblockCurrentUser = () => {
        if (!userInfos || !userInfos.id) return;
        unblockUser(userInfos.id).then((res) => {
            if (!res.ok) return;
            setBlocked(false);
        });
    };

    return (
        <div className="user">
            <div className="topBar">
                <Link to="/" className="backButton">
                    <svg
                        viewBox="0 0 24 24"
                        aria-hidden="true"
                        className="r-4qtqp9 r-yyyyoo r-dnmrzs r-bnwqim r-1plcrui r-lrvibr r-z80fyv r-19wmn03"
                    >
                        <g>
                            <path d="M7.414 13l5.043 5.04-1.414 1.42L3.586 12l7.457-7.46 1.414 1.42L7.414 11H21v2H7.414z"></path>
                        </g>
                    </svg>
                </Link>
                <div className="infos">
                    <p>@{user}</p>
                    <p>
                        {posts.length ?? 0} post{posts.length > 1 ? "s" : ""}
                    </p>
                </div>
            </div>
            <div className="banner">
                <img
                    src={userInfos?.bannerUri ?? "https://picsum.photos/800/300"}
                    alt=""
                />
            </div>
            <div className="profilePicture">
                <img src={userInfos?.imageUri ?? profilePicture} alt="" />
            </div>
            <div className="actions">
                {!isAccountOwner && (
                    <button
                        onClick={() =>
                            blocked ? unblockCurrentUser() : blockCurrentUser()
                        }
                    >
                        {blocked ? "Unblock" : "Block"}
                    </button>
                )}
                {!isAccountOwner && !blocked && (
                    <button
                        onClick={() =>
                            following
                                ? unfollowCurrentUser()
                                : followCurrentUser()
                        }
                    >
                        {following ? "Unfollow" : "Follow"}
                    </button>
                )}
                {isAccountOwner && <button>Edit profile</button>}
            </div>
            <div className="stats">
                <div>
                    <span>{follows.length ?? 0}</span>Following
                </div>
                <div>
                    <span>{followers.length ?? 0}</span>Followers
                </div>
            </div>
            <div className="posts">
                {!blocked &&
                    posts.map((post) => (
                        <Post
                            image={post.media}
                            user={post.author}
                            content={post.text}
                            date={post.created_date}
                            post={post.repost}
                            key={post.id}
                            id={post.id}
                        />
                    ))}
            </div>
        </div>
    );
}
