import React from "react";

import "./styles/User.scss";

import { Link, useParams } from "react-router-dom";

export default function User() {
    const { user } = useParams();

    return (
        <div className="user">
            <div className="topBar">
                <Link to="/" className="backButton">
                    Back
                </Link>
                <div className="infos">
                    <p>@{user}</p>
                    <p>1,3K posts</p>
                </div>
            </div>
            <div className="banner">
                <img src="https://picsum.photos/800/300" alt="" />
            </div>
            <div className="profilePicture">
                <img src="https://picsum.photos/200" alt="" />
            </div>
            <div className="actions">
              <button>Block</button>
              <button>Follow</button>
            </div>
        </div>
    );
}
