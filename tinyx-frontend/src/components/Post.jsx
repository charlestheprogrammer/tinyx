import React from "react";

import { Link } from "react-router-dom";

import profilePicture from "../assets/pp.jpeg";
import "./styles/Post.scss";
import {
    deletePost,
    getLikesByPostId,
    getPostById,
    getUserInfosById,
    likePost,
    unlikePost,
} from "../api.js";
import { selectAccountId } from "../store/Account.js";
import { useSelector } from "react-redux";

const dateOptions = {
    year: "numeric",
    month: "short",
    day: "numeric",
};

export default function Post({
    user,
    content,
    image,
    date,
    post,
    id,
    onDelete,
    likedBy,
}) {
    const [userInfos, setUserInfos] = React.useState({});
    const [repostInfos, setRepostInfos] = React.useState({});
    const [loading, setLoading] = React.useState(true);
    const [likes, setLikes] = React.useState(0);
    const [userLiked, setUserLiked] = React.useState(false);
    const [likedByUsername, setLikedByUsername] = React.useState("");

    const accountId = useSelector(selectAccountId);

    React.useEffect(() => {
        if (!user) return;
        getUserInfosById(user).then((res) => {
            setUserInfos(res.data);
        });
        if (likedBy) {
            getUserInfosById(likedBy).then((res) => {
                if (!res.ok) return;
                setLikedByUsername(res.data.username);
            });
        }
    }, [user]);

    React.useEffect(() => {
        if (!post) return;
        getPostById(post).then((res) => {
            setRepostInfos(res.data);
        });
    }, []);

    React.useEffect(() => {
        if (userInfos.username && (repostInfos.author || !post)) {
            setLoading(false);
        }
    }, [userInfos, repostInfos]);

    React.useEffect(() => {
        if (!id) return;
        getLikesByPostId(id).then((res) => {
            setLikes(res.data.length);
            setUserLiked(res.data.some((like) => like === accountId));
        });
    }, [post, accountId]);

    const createLike = () => {
        likePost(id).then((res) => {
            if (!res.ok) return;
            setLikes(likes + 1);
            setUserLiked(true);
        });
    };

    const deleteLike = () => {
        unlikePost(id).then((res) => {
            if (!res.ok) return;
            setLikes(likes - 1);
            setUserLiked(false);
        });
    };

    const deleteOwnPost = () => {
        deletePost(id).then((res) => {
            if (!res.ok) return;
            onDelete && onDelete(id);
        });
    };

    if (loading)
        return (
            <div className="post">
                <a>
                    <img src={profilePicture} alt="" />
                </a>
                <div className="content">
                    <p className="post_info">... • ...</p>
                    <p style={{ whiteSpace: "pre-wrap" }}>...</p>
                    {image && <img src={image} alt="" />}
                </div>
            </div>
        );

    return (
        <>
            {likedBy && likedBy !== "null" && (
                <p className="postTopInfo">
                    @{likedByUsername} liked this post
                </p>
            )}
            <div className="post">
                <Link to={`/users/${userInfos.username}`}>
                    <img src={userInfos.imageUri ?? profilePicture} alt="" />
                </Link>
                <div className="content">
                    <p className="post_info">
                        <Link to={`/users/${userInfos.username}`}>
                            @{userInfos.username}
                        </Link>{" "}
                        •{" "}
                        {new Date(date).toLocaleDateString(
                            "en-US",
                            dateOptions
                        )}
                    </p>
                    <p style={{ whiteSpace: "pre-wrap" }}>{content}</p>
                    {image && <img src={image} alt="" />}
                    {post && (
                        <Post
                            user={repostInfos.author}
                            content={repostInfos.text}
                            image={repostInfos.media}
                            date={repostInfos.created_date}
                            post={repostInfos.repost}
                        />
                    )}
                    <div className="actions">
                        <div>
                            <svg
                                viewBox="0 0 24 24"
                                aria-hidden="true"
                                className="r-4qtqp9 r-yyyyoo r-dnmrzs r-bnwqim r-1plcrui r-lrvibr r-1xvli5t r-1hdv0qi"
                            >
                                <g>
                                    <path d="M1.751 10c0-4.42 3.584-8 8.005-8h4.366c4.49 0 8.129 3.64 8.129 8.13 0 2.96-1.607 5.68-4.196 7.11l-8.054 4.46v-3.69h-.067c-4.49.1-8.183-3.51-8.183-8.01zm8.005-6c-3.317 0-6.005 2.69-6.005 6 0 3.37 2.77 6.08 6.138 6.01l.351-.01h1.761v2.3l5.087-2.81c1.951-1.08 3.163-3.13 3.163-5.36 0-3.39-2.744-6.13-6.129-6.13H9.756z"></path>
                                </g>
                            </svg>
                            34
                        </div>
                        <div>
                            <svg
                                viewBox="0 0 24 24"
                                aria-hidden="true"
                                className="r-4qtqp9 r-yyyyoo r-dnmrzs r-bnwqim r-1plcrui r-lrvibr r-1xvli5t r-1hdv0qi"
                            >
                                <g>
                                    <path d="M4.5 3.88l4.432 4.14-1.364 1.46L5.5 7.55V16c0 1.1.896 2 2 2H13v2H7.5c-2.209 0-4-1.79-4-4V7.55L1.432 9.48.068 8.02 4.5 3.88zM16.5 6H11V4h5.5c2.209 0 4 1.79 4 4v8.45l2.068-1.93 1.364 1.46-4.432 4.14-4.432-4.14 1.364-1.46 2.068 1.93V8c0-1.1-.896-2-2-2z"></path>
                                </g>
                            </svg>
                            1,5K
                        </div>
                        <div
                            onClick={() =>
                                userLiked ? deleteLike() : createLike()
                            }
                            className={userLiked ? "like liked" : "like"}
                        >
                            <svg viewBox="0 0 24 24" aria-hidden="true">
                                <g>
                                    <path d="M16.697 5.5c-1.222-.06-2.679.51-3.89 2.16l-.805 1.09-.806-1.09C9.984 6.01 8.526 5.44 7.304 5.5c-1.243.07-2.349.78-2.91 1.91-.552 1.12-.633 2.78.479 4.82 1.074 1.97 3.257 4.27 7.129 6.61 3.87-2.34 6.052-4.64 7.126-6.61 1.111-2.04 1.03-3.7.477-4.82-.561-1.13-1.666-1.84-2.908-1.91zm4.187 7.69c-1.351 2.48-4.001 5.12-8.379 7.67l-.503.3-.504-.3c-4.379-2.55-7.029-5.19-8.382-7.67-1.36-2.5-1.41-4.86-.514-6.67.887-1.79 2.647-2.91 4.601-3.01 1.651-.09 3.368.56 4.798 2.01 1.429-1.45 3.146-2.1 4.796-2.01 1.954.1 3.714 1.22 4.601 3.01.896 1.81.846 4.17-.514 6.67z"></path>
                                </g>
                            </svg>
                            {likes}
                        </div>
                        <div>
                            <svg
                                version="1.1"
                                id="Capa_1"
                                xmlns="http://www.w3.org/2000/svg"
                                viewBox="0 0 408.483 408.483"
                                onClick={deleteOwnPost}
                            >
                                <g>
                                    <g>
                                        <path
                                            d="M87.748,388.784c0.461,11.01,9.521,19.699,20.539,19.699h191.911c11.018,0,20.078-8.689,20.539-19.699l13.705-289.316
			H74.043L87.748,388.784z M247.655,171.329c0-4.61,3.738-8.349,8.35-8.349h13.355c4.609,0,8.35,3.738,8.35,8.349v165.293
			c0,4.611-3.738,8.349-8.35,8.349h-13.355c-4.61,0-8.35-3.736-8.35-8.349V171.329z M189.216,171.329
			c0-4.61,3.738-8.349,8.349-8.349h13.355c4.609,0,8.349,3.738,8.349,8.349v165.293c0,4.611-3.737,8.349-8.349,8.349h-13.355
			c-4.61,0-8.349-3.736-8.349-8.349V171.329L189.216,171.329z M130.775,171.329c0-4.61,3.738-8.349,8.349-8.349h13.356
			c4.61,0,8.349,3.738,8.349,8.349v165.293c0,4.611-3.738,8.349-8.349,8.349h-13.356c-4.61,0-8.349-3.736-8.349-8.349V171.329z"
                                        />
                                        <path
                                            d="M343.567,21.043h-88.535V4.305c0-2.377-1.927-4.305-4.305-4.305h-92.971c-2.377,0-4.304,1.928-4.304,4.305v16.737H64.916
			c-7.125,0-12.9,5.776-12.9,12.901V74.47h304.451V33.944C356.467,26.819,350.692,21.043,343.567,21.043z"
                                        />
                                    </g>
                                </g>
                            </svg>
                            <svg
                                viewBox="0 0 24 24"
                                aria-hidden="true"
                                onClick={() =>
                                    navigator.clipboard.writeText(id)
                                }
                            >
                                <g>
                                    <path d="M12 2.59l5.7 5.7-1.41 1.42L13 6.41V16h-2V6.41l-3.3 3.3-1.41-1.42L12 2.59zM21 15l-.02 3.51c0 1.38-1.12 2.49-2.5 2.49H5.5C4.11 21 3 19.88 3 18.5V15h2v3.5c0 .28.22.5.5.5h12.98c.28 0 .5-.22.5-.5L19 15h2z"></path>
                                </g>
                            </svg>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}
