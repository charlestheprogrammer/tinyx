import React from "react";

import "./styles/Search.scss";
import { getPostById, searchInPosts } from "../api.js";
import Post from "./Post.jsx";

export default function Search() {
    const [search, setSearch] = React.useState("");
    const [searchResults, setSearchResults] = React.useState([]);

    const startSearch = (input) => {
        searchInPosts(input).then(async (res) => {
            if (!res.ok) return;
            console.log(res.data);
            const searchedPosts = [];
            for (const postId of res.data) {
                const postRequest = await getPostById(postId);
                if (!postRequest.ok) continue;
                searchedPosts.push(postRequest.data);
            }
            setSearchResults(searchedPosts);
        });
    };

    return (
        <div className="search">
            <div className="search-input">
                <div className="icon">
                    <svg viewBox="0 0 24 24" aria-hidden="true">
                        <g>
                            <path d="M10.25 3.75c-3.59 0-6.5 2.91-6.5 6.5s2.91 6.5 6.5 6.5c1.795 0 3.419-.726 4.596-1.904 1.178-1.177 1.904-2.801 1.904-4.596 0-3.59-2.91-6.5-6.5-6.5zm-8.5 6.5c0-4.694 3.806-8.5 8.5-8.5s8.5 3.806 8.5 8.5c0 1.986-.682 3.815-1.824 5.262l4.781 4.781-1.414 1.414-4.781-4.781c-1.447 1.142-3.276 1.824-5.262 1.824-4.694 0-8.5-3.806-8.5-8.5z"></path>
                        </g>
                    </svg>
                </div>
                <input
                    type="text"
                    placeholder="Search"
                    onChange={(e) => setSearch(e.target.value)}
                    value={search}
                    onKeyDown={(event) => {
                        if (event.key === "Enter") {
                            startSearch(search);
                        }
                    }}
                />
            </div>
            <div className="search-results">
                {searchResults.map((post) => (
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
