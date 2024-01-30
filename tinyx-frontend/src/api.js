import apisauce from "apisauce";

const baseURL = "http://localhost:8000/";

const api = apisauce.create({
    baseURL,
    headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
    },
    timeout: 10000,
});

export const setUser = (user) => api.setHeader("X-user-id", `${user}`);
export const getUserInfosByUsername = (username) =>
    api.get(`/api/user/by_username/${username}`);

export const getUserInfosById = (id) => api.get(`/api/user/by_user_id/${id}`);

export const getPostsByAuthor = (author) =>
    api.get(`/repo-post/api/posts/by_author/${author}`);

export const getPostById = (postId) =>
    api.get(`/repo-post/api/post/by_id/${postId}`);

export const getAllPosts = () => api.get(`/repo-post/api/posts`);

export const getAllUsers = () => api.get(`/api/users`);

export const likePost = (postId) => api.post(`/repo-social/api/like/${postId}`);

export const unlikePost = (postId) =>
    api.delete(`/repo-social/api/like/${postId}`);

export const getLikesByPostId = (postId) =>
    api.get(`/repo-social/api/likes/post/${postId}`);

export const followUser = (userId) =>
    api.post(`/repo-social/api/follow/${userId}`);

export const unfollowUser = (userId) =>
    api.post(`/repo-social/api/unfollow/${userId}`);

export const getFollowersByUserId = (userId) =>
    api.get(`/repo-social/api/followers/${userId}`);

export const getFollowsByUserId = (userId) =>
    api.get(`/repo-social/api/follows/${userId}`);

export const isUserBlocked = (userId) =>
    api
        .get(`/repo-social/api/blocked/${userId}`)
        .then((res) =>
            res.ok && res.data ? res.data.toString() === "true" : false
        );

export const blockUser = (userId) =>
    api.post(`/repo-social/api/block/${userId}`);

export const unblockUser = (userId) =>
    api.post(`/repo-social/api/unblock/${userId}`);

export default {
    api,
};
